/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import org.w3c.dom.Document;

import com.watchingstuff.storage.TelevisionSeries;
import com.watchingstuff.utils.XmlUtils;

/**
 * Contains utilities for converting XML into watchingstuff objects ready for
 * storage
 * 
 * @author kruser
 */
public class Conversions
{

	/**
	 * Convert a series XML document as received from TheTvDB.com into a
	 * watchingstuff {@link TelevisionSeries}
	 * 
	 * @param seriesDocument
	 * @return
	 */
	public static TelevisionSeries fromDocument(Document seriesDocument)
	{
		TelevisionSeries series = new TelevisionSeries();
		series.setName(XmlUtils.getFirstElementTextByName(seriesDocument, "SeriesName"));
		return series;
	}
}
