/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
					DateTime dt = formatter.parseDateTime(chars.toString());
					series.setAirDate(dt);
				}
			}
		}
		catch (NumberFormatException e)
		{
			LOGGER.error("Error parsing a number", e);
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
		}
	}
}
