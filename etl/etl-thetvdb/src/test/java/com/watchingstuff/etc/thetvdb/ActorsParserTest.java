package com.watchingstuff.etc.thetvdb;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.watchingstuff.etl.thetvdb.ActorsParser;
import com.watchingstuff.storage.Actor;
import com.watchingstuff.storage.Role;

public class ActorsParserTest
{
	private ActorsParser actorsParser;

	@Before
	public void setUp() throws Exception
	{
		InputStream enStream = getClass().getClassLoader().getResourceAsStream("series/actors.xml");
		assertNotNull(enStream);
		actorsParser = new ActorsParser();
		actorsParser.parse(enStream);
	}

	/**
	 * @throws ParseException 
	 */
	@Test
	public void testActorsParsed() throws ParseException
	{
		List<Role> cast = actorsParser.getCast();
		Assert.assertEquals(38, cast.size());
		
		Role role = cast.get(0);
		Assert.assertEquals("Jack Shephard", role.getRoleName());
		
		Actor actor = role.getActor();
		Assert.assertEquals("Matthew Fox", actor.getName());
		Assert.assertEquals("27747", actor.getSourceId());
	}
	
}
