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
	
	public TelevisionSeries()
	{
		setId(UUID.randomUUID());
		setType(WatchableThingType.TelevisionSeries);
	}
	
}