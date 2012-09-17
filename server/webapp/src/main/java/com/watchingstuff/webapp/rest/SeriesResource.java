/**
 * 
 */
package com.watchingstuff.webapp.rest;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watchingstuff.services.series.ISeriesService;

/**
 * Offers HTTP verbs on Television Series
 * 
 * @author kruser
 */
@Component
@Path("/series")
public class SeriesResource
{
	@Autowired
	ISeriesService seriesService;
	
	@GET
	@Produces("text/plain")
	public String getSeries()
	{
		return seriesService.getSeriesById(UUID.randomUUID());
	}
}
