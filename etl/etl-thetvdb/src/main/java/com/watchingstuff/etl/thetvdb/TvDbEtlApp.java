package com.watchingstuff.etl.thetvdb;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Synchronizes our database with the thetvdb.com based on the last
 * synchronization
 */
public class TvDbEtlApp
{
	public static void main(String[] args)
	{
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TheTvDbEtl etl = (TheTvDbEtl) context.getBean("etl");
		context.registerShutdownHook();
		etl.runEtl();
	}
}
