package com.watchingstuff.etc.thetvdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.watchingstuff.etl.thetvdb.SeriesParser;
import com.watchingstuff.storage.TelevisionEpisode;
import com.watchingstuff.storage.TelevisionSeries;

public class SeriesParserTest
{
	private SeriesParser seriesParser;
	
	@Before
	public void setUp() throws Exception
	{
		InputStream enStream = getClass().getClassLoader().getResourceAsStream("series/en.xml");
		assertNotNull(enStream);
		seriesParser = new SeriesParser();
		seriesParser.parse(enStream);
	}

	/**
	 * Tests out the series that gets parsed from a full episode's worth of data
	 */
	@Test
	public void testSeriesParser()
	{
		TelevisionSeries series = seriesParser.getSeries();
		assertNotNull(series);
		assertNotNull(series.getId());
		assertEquals("Lost", series.getName());
		assertEquals("After their plane, Oceanic Air flight 815, tore apart whilst thousands of miles off course, the survivors find themselves on a mysterious deserted island where they soon find out they are not alone.", series.getSynopsis());
		assertEquals(60, series.getRuntime().intValue());
		assertEquals("73739", series.getSourceId());
		assertEquals(new DateTime(1954, 9, 22, 0, 0), series.getAirDate());
	}
	
	/**
	 * Test out all of the episodes that are parsed from a full series worth of data
	 */
	@Test
	public void testEpisodeParser()
	{
		List<TelevisionEpisode> episodes = seriesParser.getEpisodes();
		assertEquals(149, episodes.size());
		
		TelevisionEpisode episode = episodes.get(0);
		assertEquals("127151", episode.getSourceId());
		assertEquals("The Journey", episode.getName());
		assertEquals("Flashbacks of the core characters illustrating who they were and what they were doing before the crash, a look at the island itself, and a preview of the big season finale.", episode.getSynopsis());
		assertEquals(1, episode.getEpisodeNumber().intValue());
		assertEquals(0, episode.getSeasonNumber().intValue());
		assertEquals(new DateTime(2005, 4, 27, 0, 0), episode.getAirDate());
		assertEquals(seriesParser.getSeries().getId(), episode.getSeriesId());
	}

}
