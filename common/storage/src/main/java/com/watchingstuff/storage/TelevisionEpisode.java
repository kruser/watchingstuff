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
	private static final String PROP_EPISODE_NUMBER = "episodeNumber";
	private static final String PROP_SEASON_NUMBER = "seasonNumber";
	private static final long serialVersionUID = -4462281405678041797L;

	public TelevisionEpisode()
	{
		setType(WatchableThingType.TelevisionEpisode);
		setId(UUID.randomUUID());
	}
	
	public void setSeriesId(UUID seriesId)
	{
		put(PROP_SERIES_ID, seriesId);
	}

	public UUID getSeriesId()
	{
		return (UUID) get(PROP_SERIES_ID);
	}
	
	public void setEpisodeNumber(Integer episodeNumber)
	{
		put(PROP_EPISODE_NUMBER, episodeNumber);
	}

	public Integer getEpisodeNumber()
	{
		return (Integer) get(PROP_EPISODE_NUMBER);
	}

	public void setSeasonNumber(Integer seasonNumber)
	{
		put(PROP_SEASON_NUMBER, seasonNumber);
	}
	
	public Integer getSeasonNumber()
	{
		return (Integer) get(PROP_SEASON_NUMBER);
	}

}
