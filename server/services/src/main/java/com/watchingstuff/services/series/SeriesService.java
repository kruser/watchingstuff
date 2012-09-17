/**
 * 
 */
package com.watchingstuff.services.series;

import java.util.UUID;

/**
 * @author kruser
 */
public class SeriesService implements ISeriesService
{
	/* (non-Javadoc)
	 * @see com.watchingstuff.services.series.ISeriesService#getSeriesById(java.util.UUID)
	 */
	@Override
	public String getSeriesById(UUID id)
	{
		return "Seinfeld";
	}

}
