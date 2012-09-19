/**
 * 
 */
package com.watchingstuff.storage;

/**
 * @author kruser
 * 
 */
public interface IPersistenceManager
{
	/**
	 * Save or update a property
	 * 
	 * @param propertyName
	 * @param propertyValue
	 */
	public void saveProperty(String propertyName, Object propertyValue);

	/**
	 * Get a property by name
	 * 
	 * @param propertyName
	 * @return
	 */
	public Object getProperty(String propertyName);
}
