package com.watchingstuff.etl.thetvdb;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Synchronizes our database with the thetvdb.com based on the last synchronization
 */
public class UpdateTvDb 
{
    public static void main( String[] args )
    {
    	springContextLoad();
    }

    /**
     * Loads up the spring context
     */
	private static void springContextLoad()
	{
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.registerShutdownHook();
	}
}
