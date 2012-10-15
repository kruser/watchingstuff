/**
 * 
 */
package com.watchingstuff.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kruser
 * 
 */
public class TelevisionSeriesTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testNewSeries()
	{
		TelevisionSeries televisionSeries = new TelevisionSeries();
		assertNotNull(televisionSeries.getId());
	}

}
