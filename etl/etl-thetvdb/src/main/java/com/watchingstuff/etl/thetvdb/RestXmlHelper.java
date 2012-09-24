/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * @author kruser
 * 
 */
public class RestXmlHelper
{
	private static Logger LOGGER = Logger.getLogger(RestXmlHelper.class);

	public RestXmlHelper()
	{

	}

	/**
	 * Get an XML {@link Document} representing the http response of the URL
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static Document getDocument(String apiUrl)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			return documentBuilder.parse(conn.getInputStream());
		}
		catch (Exception e)
		{
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

}
