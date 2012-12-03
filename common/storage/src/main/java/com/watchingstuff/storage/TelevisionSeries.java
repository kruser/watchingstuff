/**
 * 
 */
package com.watchingstuff.storage;

import java.util.UUID;


/**
 * @author kruser
 *
 */
public class TelevisionSeries extends WatchableThing
{
	private static final long serialVersionUID = 196805809914987338L;
	private static final String PROP_NETWORK = "network";
	
	public TelevisionSeries()
	{
		setId(UUID.randomUUID());
		setType(WatchableThingType.TelevisionSeries);
	}
	
	/**
	 * The television network that this series aired on
	 * @param name
	 */
	public void setNetwork(String name)
	{
		put(PROP_NETWORK, name);
	}

	/**
	 * The television network that this series aired on
	 * @return
	 */
	public String getNetwork()
	{
		return (String) get(PROP_NETWORK);
	}
	
}