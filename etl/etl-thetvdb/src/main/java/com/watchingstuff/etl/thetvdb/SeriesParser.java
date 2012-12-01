/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.watchingstuff.storage.TelevisionEpisode;
import com.watchingstuff.storage.TelevisionSeries;

/**
 * parses full series information from an XML document.
 * 
 * @author kruser
 * 
 */
public class SeriesParser extends DefaultHandler
{
	private static Logger LOGGER = Logger.getLogger(SeriesParser.class);

	private TelevisionSeries series;
	private List<TelevisionEpisode> episodes = new ArrayList<TelevisionEpisode>();
	private TelevisionEpisode currentEpisode;
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private SAXParserFactory factory;
	private SAXParser saxParser;
	private StringBuilder chars = new StringBuilder();
	
	private boolean inSeries = false;

	public SeriesParser()
	{
		factory = SAXParserFactory.newInstance();
		try
		{
			saxParser = factory.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			LOGGER.error(e);
		}
		catch (SAXException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Resulting objects will be null if this fails
	 * 
	 * @param xml
	 */
	public void parse(InputStream xml)
	{
		try
		{
			saxParser.parse(xml, this);
		}
		catch (SAXException e)
		{
			LOGGER.error("Error parsing XML", e);
		}
		catch (IOException e)
		{
			LOGGER.error("Error parsing XML", e);
		}
	}

	/**
	 * Get the series
	 * 
	 * @return
	 */
	public TelevisionSeries getSeries()
	{
		return series;
	}
	
	/**
	 * Get the episodes as parsed by thetvdb XML
	 * 
	 * @return
	 */
	public List<TelevisionEpisode> getEpisodes()
	{
		return episodes;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		super.characters(ch, start, length);
		chars.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		super.endElement(uri, localName, qName);
		if (qName.equals("Series"))
		{
			inSeries = false;
		}
		else if (qName.equals("Episode"))
		{
			if (currentEpisode != null)
			{
				episodes.add(currentEpisode);
				currentEpisode = null;
			}
		}

		try
		{
			if (inSeries)
			{
				if (qName.equals("SeriesName"))
				{
					series.setName(chars.toString());
				}
				else if (qName.equals("Overview"))
				{
					series.setSynopsis(chars.toString());
				}
				else if (qName.equals("Runtime"))
				{
					String runtimeString = chars.toString();
					series.setRuntime(Integer.parseInt(runtimeString));
				}
				else if (qName.equals("id"))
				{
					series.setSourceId(chars.toString());
				}
				else if (qName.equals("FirstAired"))
				{
					Date date = format.parse(chars.toString());
					series.setAirDate(date);
				}
			}
			else if (currentEpisode != null)
			{
				if (qName.equals("id"))
				{
					currentEpisode.setSourceId(chars.toString());
				}
				else if (qName.equals("Overview"))
				{
					currentEpisode.setSynopsis(chars.toString());
				}
				else if (qName.equals("EpisodeName"))
				{
					currentEpisode.setName(chars.toString());
				}
				else if (qName.equals("EpisodeNumber"))
				{
					currentEpisode.setEpisodeNumber(Integer.parseInt(chars.toString()));
				}
				else if (qName.equals("SeasonNumber"))
				{
					currentEpisode.setSeasonNumber(Integer.parseInt(chars.toString()));
				}
				else if (qName.equals("FirstAired"))
				{
					//DateTime dt = formatter.parseDateTime(chars.toString());
					Date date = format.parse(chars.toString());
					currentEpisode.setAirDate(date);
				}
			}
		}
		catch (NumberFormatException e)
		{
			LOGGER.error("Error parsing a number", e);
		}
		catch (IllegalArgumentException e)
		{
			LOGGER.error("Error parsing a date", e);
		}
		catch (ParseException e)
		{
			LOGGER.error("Error parsing a date", e);
		}
	}

	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		chars = new StringBuilder();
		if (qName.equals("Series"))
		{
			inSeries = true;
			series = new TelevisionSeries();
			series.setId(UUID.randomUUID());
		}
		else if (qName.equals("Episode"))
		{
			currentEpisode = new TelevisionEpisode();
			if (series != null)
			{
				currentEpisode.setSeriesId(series.getId());
			}
		}
	}
}
