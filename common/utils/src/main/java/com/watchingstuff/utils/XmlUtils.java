/**
 * 
 */
package com.watchingstuff.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
}
