/**
 * 
 */
package com.watchingstuff.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author kruser
 */
public class XmlUtils
{
	/**
	 * Gets the text of the first element that matches the provided name
	 * 
	 * @param document
	 * @param elementName
	 * @return null if the element doesn't exist
	 */
	public static String getFirstElementTextByName(Document document, String elementName)
	{
		NodeList elements = document.getElementsByTagName(elementName);
		if (elements.getLength() > 0)
		{
			Node item = elements.item(0);
			return item.getTextContent();
		}
		return null;
	}

	/**
	 * Create a {@link Document} given an {@link InputStream} of an XML
	 * structure
	 * 
	 * @param in
	 * @return
	 */
	public static Document newDocumentFromInputStream(InputStream in)
	{
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try
		{
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}

		try
		{
			ret = builder.parse(new InputSource(in));
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return ret;
	}
}
