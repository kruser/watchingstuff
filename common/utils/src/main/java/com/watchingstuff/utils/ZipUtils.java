/**
 * 
 */
package com.watchingstuff.utils;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * @author kruser
 * 
 */
public class ZipUtils
{
	private static Logger LOGGER = Logger.getLogger(ZipUtils.class);

	/**
	 * Scans a zip file (not directories though), for specified XML document
	 * 
	 * @param zip
	 * @param fileName
	 * @return
	 * @throws IOException
	 *             if the zip is bad or the filename isn't found in the zip
	 */
	public Document getDocumentFromZip(ZipInputStream zip, String fileName) throws IOException
	{
		ZipEntry zipEntry = null;
		while ((zipEntry = zip.getNextEntry()) != null)
		{
			if (zipEntry.getName().equals(fileName))
			{
				StringBuilder rawText = new StringBuilder();
				while (zip.available() > 0)
				{
					zip.read();
				}
			}
		}
		throw new IOException(String.format("Unable to find %s in zip file", fileName));
	}
}
