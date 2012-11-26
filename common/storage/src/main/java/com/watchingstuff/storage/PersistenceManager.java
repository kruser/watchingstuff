/**
 * 
 */
package com.watchingstuff.storage;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * @author kruser
 * 
 */
public class PersistenceManager implements IPersistenceManager
{
	private static final String COLLECTION_PROPERTIES = "properties";
	private static final String COLLECTION_SERIES = "series";
	private static final String COLLECTION_EPISODE = "episode";
	private static final String PROPERTY_NAME_VALUE = "value";
	private static final String PROPERTY_NAME_KEY = "key";
	private static Logger LOGGER = Logger.getLogger(PersistenceManager.class.getName());
	private final DB databaseConnection;

	/**
	 * 
	 * @param databaseHost
	 * @param databasePort
	 * @param databaseName
	 * @throws RuntimeException
	 *             if the connection fails
	 */
	public PersistenceManager(String databaseHost, int databasePort, String databaseName)
	{
		try
		{
			Mongo mongo = new Mongo(databaseHost, databasePort);
			LOGGER.info(String.format("Setting up database connection to %s@%s:%d", databaseName, databaseHost, databasePort));
			databaseConnection = mongo.getDB(databaseName);
		}
		catch (UnknownHostException e)
		{
			LOGGER.error("database connection failed." + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.watchingstuff.storage.IPersistenceManager#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String propertyName)
	{
		BasicDBObject query = new BasicDBObject();
		query.append(PROPERTY_NAME_KEY, propertyName);

		DBCollection collection = databaseConnection.getCollection(COLLECTION_PROPERTIES);
		DBObject propResult = collection.findOne(query);
		if (propResult != null)
		{
			return propResult.get(PROPERTY_NAME_VALUE);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.watchingstuff.storage.IPersistenceManager#saveProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void saveProperty(String propertyName, Object propertyValue)
	{
		DBCollection collection = databaseConnection.getCollection(COLLECTION_PROPERTIES);
		
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.put(PROPERTY_NAME_KEY, propertyName);
		newDoc.put(PROPERTY_NAME_VALUE, propertyValue);
		
		if (getProperty(propertyName) != null)
		{
			collection.update(new BasicDBObject().append(PROPERTY_NAME_KEY, propertyName), newDoc);
		}
		else
		{
			collection.save(newDoc);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.watchingstuff.storage.IPersistenceManager#insert(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(List<? extends BaseDBObject> objects)
	{
		if (objects != null && objects.size() > 0)
		{
			String collectionName = getCollectionForObject(objects.get(0));
			DBCollection collection = databaseConnection.getCollection(collectionName);
			collection.insert((List<DBObject>)objects);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.watchingstuff.storage.IPersistenceManager#save(com.watchingstuff.storage.BaseDBObject)
	 */
	@Override
	public void save(BaseDBObject object)
	{
		String collectionName = getCollectionForObject(object);
		DBCollection collection = databaseConnection.getCollection(collectionName);
		collection.save(object);	
	}

	/**
	 * Looks up the collection based on the provided type. Throws an exception if it can't save.
	 * @param baseDBObject
	 * @return
	 */
	private String getCollectionForObject(BaseDBObject dbObj)
	{
		if (dbObj instanceof TelevisionSeries)
		{
			return COLLECTION_SERIES;
		}
		else if (dbObj instanceof TelevisionEpisode)
		{
			return COLLECTION_EPISODE;
		}
		throw new RuntimeException("No collection found for '" + dbObj.getClass().getName() + "'");
	}
}
