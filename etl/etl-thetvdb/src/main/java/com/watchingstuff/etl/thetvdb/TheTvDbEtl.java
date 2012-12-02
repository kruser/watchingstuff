/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.watchingstuff.storage.IPersistenceManager;
import com.watchingstuff.storage.TelevisionEpisode;
import com.watchingstuff.storage.TelevisionSeries;
import com.watchingstuff.storage.WatchingStuffCollection;
import com.watchingstuff.utils.HttpUtils;
import com.watchingstuff.utils.ZipUtils;

/**
 * Has operations to start a new database from scratch or update an existing database using thetvdb.com as the data
 * source.
 * 
 * Currently only supports English language
 * 
 * @author kruser
 */
@Component
public class TheTvDbEtl
{
	private static final boolean DEV_MODE = false;
	/** this key is registered to ryan@kruseonline.net **/
	private static final String TVDB_API_KEY = "6C5E6E03B728CC24";
	private static final String API_ROOT = "http://www.thetvdb.com/api/";
	private static final String TVDB_ALL_SERIES = "http://thetvdb.com/?string=&searchseriesid=&tab=listseries&function=Search";
	private static final String PROPERTY_TV_DB_LAST_UPDATE = "TV_DB_LAST_UPDATE";
	private static Logger LOGGER = Logger.getLogger(TheTvDbEtl.class.getName());

	@Autowired
	private IPersistenceManager persistenceManager;

	/**
	 * Run the ETL process.
	 */
	public void runEtl()
	{
		long currentServerTime = getCurrentServerTime();
		LOGGER.info(String.format("Current %s server timestamp is %d", API_ROOT, currentServerTime));

		Long lastUpdate = (Long) persistenceManager.getProperty(PROPERTY_TV_DB_LAST_UPDATE);
		if (lastUpdate == null)
		{
			LOGGER.info("The ETL process from thetvdb.com has never been run. Starting fresh.");

			List<Long> seriesIds = getAllSeriesIds(DEV_MODE);
			for (Long seriesId : seriesIds)
			{
				saveSeries(seriesId);
			}
		}
		else
		{
			Date lastUpdateDate = new Date(lastUpdate);
			LOGGER.info(String.format("Synching TheTVDB since %s", lastUpdateDate.toString()));
		}
		// persistenceManager.saveProperty(PROPERTY_TV_DB_LAST_UPDATE,
		// currentServerTime);
	}

	/**
	 * If the series already exists in the database, simply update the series metadata. If it doesn't exist in the
	 * database, retrieve the series along with all of the episodes, actors, etc.
	 * 
	 * @param seriesId
	 */
	private void saveSeries(Long seriesId)
	{
		TelevisionSeries series = (TelevisionSeries) persistenceManager.getObjectByProperty(TelevisionSeries.PROP_SOURCE_ID, seriesId.toString(), WatchingStuffCollection.SERIES);
		if (series != null)
		{
			LOGGER.info("Series " + seriesId + "(" + series.getName() + ") already exists... updating");
			saveSeriesInfoOnly(series);
		}
		else
		{
			LOGGER.info("Series " + seriesId + " doesn't exist yet. Retrieving full series data.");
			saveCompleteSeriesInfo(seriesId);
		}
	}

	/**
	 * Retrieves an updated version of a series and overwrites the old object while preserving the ID
	 * 
	 * @param series
	 */
	private void saveSeriesInfoOnly(TelevisionSeries series)
	{
		String seriesUrl = String.format("%s%s/series/%s/en.xml", API_ROOT, TVDB_API_KEY, series.getSourceId());
		String enXml = HttpUtils.httpGet(seriesUrl);

		SeriesParser seriesParser = new SeriesParser();
		seriesParser.parse(IOUtils.toInputStream(enXml));

		TelevisionSeries newSeries = seriesParser.getSeries();
		newSeries.setId(series.getId());
		persistenceManager.save(newSeries);
	}

	/**
	 * Gets series and all episode information. Designed to be called to populate a series from scratch
	 * 
	 * @param seriesId
	 */
	private void saveCompleteSeriesInfo(Long seriesId)
	{
		String seriesUrl = String.format("%s%s/series/%d/all/en.zip", API_ROOT, TVDB_API_KEY, seriesId);
		try
		{
			ZipInputStream seriesZip = HttpUtils.downloadZip(seriesUrl);
			InputStream seriesAndEpisodesXml = ZipUtils.getFileFromZip(seriesZip, "en.xml");
			SeriesParser seriesParser = new SeriesParser();
			seriesParser.parse(seriesAndEpisodesXml);

			TelevisionSeries series = seriesParser.getSeries();
			persistenceManager.save(series);

			List<TelevisionEpisode> episodes = seriesParser.getEpisodes();
			persistenceManager.insert(episodes);
		}
		catch (IOException e)
		{
			LOGGER.error(String.format("Unable to read series %s", seriesId), e);
		}
	}

	/**
	 * Get the current server time from thetvdb.com so the next time we ask for updates it will be relative to this
	 * time.
	 * 
	 * @return
	 */
	private long getCurrentServerTime()
	{
		Document document = HttpUtils.getDocument(API_ROOT + "Updates.php?type=none");
		NodeList elementsByTagName = document.getElementsByTagName("Time");
		if (elementsByTagName.getLength() > 0)
		{
			Node item = elementsByTagName.item(0);
			String textContent = item.getTextContent();
			return Long.parseLong(textContent);
		}
		throw new RuntimeException("Unable to get the current server time");
	}

	/**
	 * Get all of the series IDs known to thetvdb database
	 * 
	 * @return
	 */
	private List<Long> getAllSeriesIds(boolean devMode)
	{
		List<Long> seriesList = new ArrayList<Long>();
		if (devMode)
		{
			seriesList.add(70327l);
			seriesList.add(70328l);
			seriesList.add(70329l);
			seriesList.add(70330l);
			seriesList.add(70331l);
		}
		else
		{
			String allSeriesPage = HttpUtils.httpGet(TVDB_ALL_SERIES);
			Pattern p = Pattern.compile("\\bid=(\\d+)");
			Matcher matcher = p.matcher(allSeriesPage);
			while (matcher.find())
			{
				String group = matcher.group(1);
				seriesList.add(Long.parseLong(group));
			}
			LOGGER.info(String.format("Found %d series from thetvdb.com", seriesList.size()));
		}
		return seriesList;
	}

}
