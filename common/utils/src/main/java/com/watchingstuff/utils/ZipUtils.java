/**
 * 
 */
package com.watchingstuff.utils;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * @author kruser
 * 
 */
public class ZipUtils
{
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
