/**
 * 
 */
package com.watchingstuff.etl.thetvdb;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watchingstuff.storage.IPersistenceManager;

/**
 * Has operations to start a new database from scratch or update an existing
 * database using thetvdb.com as the data source.
 * 
 * @author kruser
 */
@Component
public class TheTvDbEtl
{
	/** this key is registered to ryan@kruseonline.net **/
	public static String TVDB_API_KEY = "6C5E6E03B728CC24";
	private static Logger LOGGER = Logger.getLogger(TheTvDbEtl.class.getName());
	
	@Autowired
	private IPersistenceManager persistenceManager;

	/**
	 * Run the ETL process. 
	 */
	public void runEtl()
	{
		long lastUpdate = getLastUpdate();
	}

	/**
	 * Gets the timestamp of the last update. Returns 1 if the property doesn't exist
	 * @return
	 */
	private long getLastUpdate()
	{
		Object property = persistenceManager.getProperty("TV_DB_LAST_UPDATE");
		if (property != null)
		{
		}
		LOGGER.info("The ETL process from thetvdb.com has never been run. Starting fresh.");
		return 1;
	}
	
}
