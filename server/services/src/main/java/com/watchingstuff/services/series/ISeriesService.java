/**
 * 
 */
package com.watchingstuff.services.series;

import java.util.UUID;

/**
 * Provides methods for working with television series and shows
 * 
 * @author kruser
 */
public interface ISeriesService
{
	/**
	 * Get a television series by it's ID
	 * @param id
	 * @return
	 */
	public String getSeriesById(UUID id);
}
