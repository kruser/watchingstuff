/**
 * 
 */
package com.watchingstuff.storage;

import java.util.Date;
import java.util.List;

/**
 * @author kruser
 */
public abstract class WatchableThing extends BaseDBObject
{
	public enum WatchableThingType { TelevisionEpisode, Movie, };

	private static final String PROP_TYPE = "type";
	private static final String PROP_RUNTIME = "runtime";
	private static final String PROP_SOURCE_ID = "sourceId";
	private static final String PROP_SYNOPSIS = "synopsis";
	private static final String PROP_NAME = "name";
	private static final String PROP_AIR_DATE = "airDate";
	private static final long serialVersionUID = -2118893394042851337L;

	protected void setType(WatchableThingType type)
	{
		put(PROP_TYPE, type);
	}

	public WatchableThingType getType()
	{
		return (WatchableThingType) get(PROP_TYPE);
	}

	public void setRuntime(int minutes)
	{
		put(PROP_RUNTIME, minutes);
	}

	public Integer getRuntime()
	{
		return (Integer) get(PROP_RUNTIME);
	}

	public void setSourceId(String sourceId)
	{
		put(PROP_SOURCE_ID, sourceId);
	}

	public String getSourceId()
	{
		return (String) get(PROP_SOURCE_ID);
	}

	public void setAirDate(Date date)
	{
		put(PROP_AIR_DATE, date);
	}

	public Date getAirDate()
	{
		return (Date) get(PROP_AIR_DATE);
	}

	public void setName(String name)
	{
		put(PROP_NAME, name);
	}

	public String getName()
	{
		return (String) get(PROP_NAME);
	}

	public void setSynopsis(String synopsis)
	{
		put(PROP_SYNOPSIS, synopsis);
	}

	public String getSynopsis()
	{
		return (String) get(PROP_SYNOPSIS);
	}
	
	public void setRoles(List<Role> roles)
	{
		put("roles", roles);
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRoles()
	{
		return (List<Role>) get("roles");
	}

}