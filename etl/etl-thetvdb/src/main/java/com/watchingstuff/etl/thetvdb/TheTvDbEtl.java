/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.watchingstuff.storage.IPersistenceManager;

/**
 * Has operations to start a new database from scratch or update an existing
 * database using thetvdb.com as the data source.
 * 
 * Currently only supports English language
 * 
 * @author kruser
 */
@Component
public class TheTvDbEtl
{
	private static final String PROPERTY_TV_DB_LAST_UPDATE = "TV_DB_LAST_UPDATE";
	/** this key is registered to ryan@kruseonline.net **/
	private static String TVDB_API_KEY = "6C5E6E03B728CC24";
	private static String API_ROOT = "http://www.thetvdb.com/api/";
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

			List<Long> seriesIds = getAllSeriesIds();
			LOGGER.info(seriesIds);
			for (Long seriesId : seriesIds)
			{
				updateSeries(seriesId);
			}
		}
		else
		{
			Date lastUpdateDate = new Date(lastUpdate);
			LOGGER.info(String.format("Synching TheTVDB since %s", lastUpdateDate.toString()));
		}
		//persistenceManager.saveProperty(PROPERTY_TV_DB_LAST_UPDATE, currentServerTime);
	}

	private void updateSeries(Long seriesId)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Get the current server time from thetvdb.com so the next time we ask for
	 * updates it will be relative to this time.
	 * 
	 * @return
	 */
	private long getCurrentServerTime()
	{
		Document document = RestXmlHelper.getDocument(API_ROOT + "Updates.php?type=none");
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
	private List<Long> getAllSeriesIds()
	{
		List<Long> seriesIds = new ArrayList<Long>();
		Document document = RestXmlHelper.getDocument(API_ROOT + "Updates.php?type=series&time=1");
		NodeList seriesElements = document.getElementsByTagName("Series");
		LOGGER.info(String.format("Found %d series to inject", seriesElements.getLength()));
		if (seriesElements.getLength() > 0)
		{
			for (int i = 0; i < seriesElements.getLength(); i++)
			{
				Node item = seriesElements.item(i);
				String textContent = item.getTextContent();
				seriesIds.add(Long.parseLong(textContent));
			}
			return seriesIds;
		}
		throw new RuntimeException("Unable to get the list of series");
	}

}
