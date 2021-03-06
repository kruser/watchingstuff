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
	private static final String PROP_CAST = "cast";

	public enum WatchableThingType
	{
		TelevisionEpisode, Movie, TelevisionSeries,
	};

	public static final String PROP_TYPE = "type";
	public static final String PROP_RUNTIME = "runtime";
	public static final String PROP_SOURCE_ID = "sourceId";
	public static final String PROP_SYNOPSIS = "synopsis";
	public static final String PROP_NAME = "name";
	public static final String PROP_AIR_DATE = "airDate";
	public static final String PROP_GENRES = "genres";

	private static final long serialVersionUID = -2118893394042851337L;

	protected void setType(WatchableThingType type)
	{
		put(PROP_TYPE, type.name());
	}

	public WatchableThingType getType()
	{
		return WatchableThingType.valueOf((String) get(PROP_TYPE));
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

	/**
	 * 
	 * @param roles
	 */
	public void setCast(List<Role> roles)
	{
		put(PROP_CAST, roles);
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getCast()
	{
		return (List<Role>) get(PROP_CAST);
	}

	public void setGenres(List<String> genres)
	{
		put(PROP_GENRES, genres);
	}

	/**
	 * String genres that this thing falls under
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getGenres()
	{
		return (List<String>) get(PROP_GENRES);
	}

}