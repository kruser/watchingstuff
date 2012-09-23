/**
 * 
 */
package com.watchingstuff.storage;

import java.net.UnknownHostException;
import java.util.logging.Logger;

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
			LOGGER.severe("database connection failed." + e.getMessage());
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
		
		collection.update(new BasicDBObject().append(PROPERTY_NAME_KEY, propertyName), newDoc);
	}

}
