/**
 * 
 */
package com.watchingstuff.storage;

/**
 * Identifies the names and serialization classes of different collections in
 * the watchingStuff database
 * 
 * @author kruser
 */
public enum WatchingStuffCollection
{
	EPISODES("episodes", TelevisionEpisode.class), 
	SERIES("series", TelevisionSeries.class), 
	PROPERTIES("properties", String.class),
	ACTORS("actors", Actor.class);

	private final String collectionName;
	@SuppressWarnings("rawtypes")
	private final Class clazz;

	/**
	 * @param collectionName
	 *            the name of the collection/table
	 * @param clazz
	 *            the name of the class to deserialize items to
	 */
	@SuppressWarnings("rawtypes")
	WatchingStuffCollection(String collectionName, Class clazz)
	{
		this.collectionName = collectionName;
		this.clazz = clazz;
	}

	/**
	 * the class that items in this collection should serialize to
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getSerializationClass()
	{
		return clazz;
	}

	/**
	 * the name of this db collection/table 
	 * @return
	 */
	public String getCollectionName()
	{
		return collectionName;
	}
}
