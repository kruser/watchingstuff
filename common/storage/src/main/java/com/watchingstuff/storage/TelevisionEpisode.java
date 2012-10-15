/**
 * 
 */
package com.watchingstuff.storage;

import java.util.UUID;

/**
 * Indicates a single episode that is part of a greater series
 * 
 * @author kruser
 */
public class TelevisionEpisode extends WatchableThing
{
	private static final String PROP_SERIES_ID = "seriesId";
	private static final long serialVersionUID = -4462281405678041797L;

	public TelevisionEpisode()
	{
		setType(WatchableThingType.TelevisionEpisode);
	}
	
	public void setSeriesId(UUID seriesId)
	{
		put(PROP_SERIES_ID, seriesId);
	}

	public UUID getSeriesId()
	{
		return (UUID) get(PROP_SERIES_ID);
	}

}
