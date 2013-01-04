/**
 * 
 */
package com.watchingstuff.storage;

import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

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
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#getProperty(java.lang.String )
	 */
	@Override
	public Object getProperty(String propertyName)
	{
		BasicDBObject query = new BasicDBObject();
		query.append(PROPERTY_NAME_KEY, propertyName);

		DBCollection collection = databaseConnection.getCollection(WatchingStuffCollection.PROPERTIES.getCollectionName());
		DBObject propResult = collection.findOne(query);
		if (propResult != null)
		{
			return propResult.get(PROPERTY_NAME_VALUE);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#saveProperty(java.lang. String, java.lang.Object)
	 */
	@Override
	public void saveProperty(String propertyName, Object propertyValue)
	{
		DBCollection collection = databaseConnection.getCollection(WatchingStuffCollection.PROPERTIES.getCollectionName());

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
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#insert(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(List<? extends BaseDBObject> objects)
	{
		if (objects != null && objects.size() > 0)
		{
			WatchingStuffCollection collectionName = getCollectionForObject(objects.get(0));
			DBCollection collection = databaseConnection.getCollection(collectionName.getCollectionName());
			collection.insert((List<DBObject>) objects);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#save(com.watchingstuff. storage.BaseDBObject)
	 */
	@Override
	public void save(BaseDBObject object)
	{
		if (object.getId() == null)
		{
			object.setId(UUID.randomUUID());
		}
		WatchingStuffCollection collectionName = getCollectionForObject(object);
		DBCollection collection = databaseConnection.getCollection(collectionName.getCollectionName());
		collection.save(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#getObjectById(java.util .UUID,
	 * com.watchingstuff.storage.IPersistenceManager.DBCollectionName)
	 */
	@Override
	public BaseDBObject getObjectById(UUID id, WatchingStuffCollection collection)
	{
		return getObjectByProperty(BaseDBObject.PROP_ID, id, collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.watchingstuff.storage.IPersistenceManager#getObjectByProperty(java .lang.String, java.lang.Object,
	 * com.watchingstuff.storage.WatchingStuffCollection)
	 */
	@Override
	public BaseDBObject getObjectByProperty(String property, Object value, WatchingStuffCollection collection)
	{
		DBCollection col = databaseConnection.getCollection(collection.getCollectionName());
		col.setObjectClass(collection.getSerializationClass());
		DBObject queryObj = new BaseDBObject(property, value);
		DBObject found = col.findOne(queryObj);
		return (BaseDBObject) found;
	}

	/**
	 * Looks up the collection based on the provided type. Throws an exception if it can't save.
	 * 
	 * @param baseDBObject
	 * @return
	 */
	private WatchingStuffCollection getCollectionForObject(BaseDBObject dbObj)
	{
		for (WatchingStuffCollection col : WatchingStuffCollection.values())
		{
			if (col.getSerializationClass().equals(dbObj.getClass()))
			{
				return col;
			}
		}
		throw new RuntimeException("No collection found for '" + dbObj.getClass().getName() + "'");
	}
}
