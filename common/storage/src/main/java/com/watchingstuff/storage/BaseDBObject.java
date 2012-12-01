/**
 * 
 */
package com.watchingstuff.storage;

import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author kruser
 *
 */
public class BaseDBObject extends BasicDBObject implements DBObject 
{
	public static final String PROP_ID = "_id";

	public BaseDBObject()
	{
	}
	
	public BaseDBObject(String key, Object value)
	{
		super(key, value);
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

}
