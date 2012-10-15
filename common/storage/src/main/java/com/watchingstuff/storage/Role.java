/**
 * 
 */
package com.watchingstuff.storage;

import java.util.UUID;

/**
 * Indicates a role that an actor plays for a television series or movie
 * 
 * @author kruser
 */
public class Role extends BaseDBObject
{
	private static final String PROP_ROLE_NAME = "roleName";
	private static final long serialVersionUID = -2168381869734990561L;
	
	public void setRoleName(String roleName)
	{
		put(PROP_ROLE_NAME, roleName);
	}
	
	public String getRoleName()
	{
		return (String)get(PROP_ROLE_NAME);
	}
	
	public void setActorName(String actorName)
	{
		put("actorName", actorName);
	}
	
	public String getActorName(String actorName)
	{
		return(String) get("actorName");
	}
	
	public void setActorId(UUID actorId)
	{
		put("actorId", actorId);
	}
	
	public UUID getActorId()
	{
		return (UUID) get("actorId");
	}
	
	/**
	 * Determines the relavance that this role has to the {@link WatchableThing}. 0=more important=leads.
	 * @param sortOrder
	 */
	public void setSortOrder(int sortOrder)
	{
		put("sortOrder", sortOrder);
	}
	
	/**
	 * Determines the relavance that this role has to the {@link WatchableThing}. 0=more important=leads.
	 * 
	 */
	public int getSortOrder()
	{
		Object sortOrderObj = get("sortOrder");
		if (sortOrderObj != null && sortOrderObj instanceof Integer)
		{
			return (Integer)sortOrderObj;
		}
		return 0;
	}
}
