/**
 * 
 */
package com.watchingstuff.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

/**
 * @author kruser
 * 
 */
public class ZipUtils
{
	/**
	 * Get an {@link InputStream} on a file from a zip. Note a {@link ZipInputStream} can't be reset, so this reading of
	 * files starts from the next entry spot on the zip, i.e. you can't go backwards in the stream
	 * 
	 * @param zip
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static InputStream getFileFromZip(ZipInputStream zip, String fileName) throws IOException
	{
		ZipEntry zipEntry = null;
		while ((zipEntry = zip.getNextEntry()) != null)
		{
			if (zipEntry.getName().equals(fileName))
			{
				try
				{
					return zip;
				}
				catch (Exception e)
				{
					throw new IOException(e.getMessage());
				}
			}
		}
		throw new IOException(String.format("Unable to find %s in zip file", fileName));
	}

	/**
	 * Returns a Map of all the files in a zip file, where the key of the map is the file name and the value is the
	 * contents of the file. Use this with caution and only where the zip contains relatively small text files
	 * 
	 * @param zip
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, String> getTextFilesFromZip(ZipInputStream zip) throws IOException
	{
		Map<String, String> results = new HashMap<String, String>();
		ZipEntry zipEntry = null;
		while ((zipEntry = zip.getNextEntry()) != null)
		{
			results.put(zipEntry.getName(), IOUtils.toString(zip));
		}
		return results;
	}

	/**
	 * Scans a zip file (not directories though), for specified XML document
	 * 
	 * @param zip
	 * @param fileName
	 * @return
	 * @throws IOException
	 *             if the zip is bad or the filename isn't found in the zip
	 */
	public static Document getDocumentFromZip(ZipInputStream zip, String fileName) throws IOException
	{
		ZipEntry zipEntry = null;
		while ((zipEntry = zip.getNextEntry()) != null)
		{
			if (zipEntry.getName().equals(fileName))
			{
				try
				{
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
					return documentBuilder.parse(zip);
				}
				catch (Exception e)
				{
					throw new IOException(e.getMessage());
				}
			}
		}
		throw new IOException(String.format("Unable to find %s in zip file", fileName));
	}
}
