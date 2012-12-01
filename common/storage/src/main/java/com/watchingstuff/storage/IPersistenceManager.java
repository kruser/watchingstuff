/**
 * 
 */
package com.watchingstuff.storage;

import java.util.List;
import java.util.UUID;

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

	/**
	 * Insert many objects into the datastore. Note, all objects will be saved
	 * to a collection/table based on their first member type, so don't mix
	 * types in the provided list.
	 * 
	 * @param objects
	 */
	public void insert(List<? extends BaseDBObject> objects);

	/**
	 * Insert or update an object
	 * 
	 * @param series
	 */
	public void save(BaseDBObject series);
	
	/**
	 * Retrieve an object by it's ID
	 * 
	 * @param id
	 * @param collection
	 * @return
	 */
	public BaseDBObject getObjectById(UUID id, WatchingStuffCollection collection);
	
	/**
	 * Find an object by an arbitrary property
	 * 
	 * @param property
	 * @param value
	 * @param collection
	 * @return
	 */
	public BaseDBObject getObjectByProperty(String property, Object value, WatchingStuffCollection collection);

}
