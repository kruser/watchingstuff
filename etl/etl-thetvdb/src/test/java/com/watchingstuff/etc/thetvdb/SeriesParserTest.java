package com.watchingstuff.etc.thetvdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.watchingstuff.etl.thetvdb.SeriesParser;
import com.watchingstuff.storage.TelevisionSeries;

public class SeriesParserTest
{
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testSeriesParser()
	{
		InputStream enStream = getClass().getClassLoader().getResourceAsStream("series/en.xml");
		assertNotNull(enStream);
		
		SeriesParser parser = new SeriesParser();
		parser.parse(enStream);
		TelevisionSeries series = parser.getSeries();
		assertNotNull(series);
		
		assertEquals("Lost", series.getName());
		assertEquals("After their plane, Oceanic Air flight 815, tore apart whilst thousands of miles off course, the survivors find themselves on a mysterious deserted island where they soon find out they are not alone.", series.getSynopsis());
		assertEquals(new Integer(60), series.getRuntime());
		assertEquals("73739", series.getSourceId());
		assertEquals(new DateTime(2004, 9, 22, 0, 0), series.getAirDate());
	}

}
