/**
 * 
 */
package com.watchingstuff.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bson.BSONObject;

import com.mongodb.DBObject;

/**
 * @author kruser
 *
 */
public abstract class BaseDBObject extends HashMap<String, Object> implements DBObject 
{
	private static final String PROP_ID = "_id";

	private boolean isPartialObject = false;
	
	public BaseDBObject()
	{
		this(UUID.randomUUID());
	}
	
	public BaseDBObject(UUID id)
	{
		setId(id);
	}
	
	public void setId(UUID id)
	{
		put(PROP_ID, id);
	}
	
	public UUID getId()
	{
		return (UUID)get(PROP_ID);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720077202394518405L;

	/*
	 * (non-Javadoc)
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object v)
	{
		return super.put(key, v);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#putAll(org.bson.BSONObject)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void putAll(BSONObject o)
	{
		super.putAll(o.toMap());
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void putAll(Map m)
	{
		super.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#get(java.lang.String)
	 */
	@Override
	public Object get(String key)
	{
		return super.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#toMap()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map toMap()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#removeField(java.lang.String)
	 */
	@Override
	public Object removeField(String key)
	{
		return super.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#containsKey(java.lang.String)
	 */
	@Override
	@Deprecated
	public boolean containsKey(String s)
	{
		return super.containsKey(s);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bson.BSONObject#containsField(java.lang.String)
	 */
	@Override
	public boolean containsField(String s)
	{
		return super.containsKey(s);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.HashMap#keySet()
	 */
	@Override
	public Set<String> keySet()
	{
		return super.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.mongodb.DBObject#markAsPartialObject()
	 */
	@Override
	public void markAsPartialObject()
	{
		isPartialObject = true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mongodb.DBObject#isPartialObject()
	 */
	@Override
	public boolean isPartialObject()
	{
		return isPartialObject;
	}

}
