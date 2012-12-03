/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.watchingstuff.storage.Actor;
import com.watchingstuff.storage.Role;

/**
 * Parsers a series of actors from a TVDB actors.xml
 * 
 * @author kruser
 */
public class ActorsParser extends DefaultHandler
{
	private static Logger LOGGER = Logger.getLogger(SeriesParser.class);

	private List<Role> cast = new ArrayList<Role>();
	private Role currentRole;

	private SAXParserFactory factory;
	private SAXParser saxParser;
	private StringBuilder chars = new StringBuilder();

	public ActorsParser()
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
		if (qName.equals("Actor"))
		{
			if (currentRole != null)
			{
				cast.add(currentRole);
				currentRole = null;
			}
		}

		if (qName.equals("id"))
		{
			currentRole.getActor().setSourceId(chars.toString());
		}
		else if (qName.equals("Name"))
		{
			currentRole.getActor().setName(chars.toString());
		}
		else if (qName.equals("Role"))
		{
			currentRole.setRoleName(chars.toString());
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
		if (qName.equals("Actor"))
		{
			currentRole = new Role();
			currentRole.setActor(new Actor());
		}
	}

	/**
	 * Get all the roles that were parsed from a list of Actors
	 * 
	 * @return
	 */
	public List<Role> getCast()
	{
		return cast;
	}
}
