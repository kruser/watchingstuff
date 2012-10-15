/**
 * 
 */
package com.watchingstuff.storage;

/**
 * Indicates a real person, not the roles they play
 * 
 * @author kruser
 */
public class Actor extends BaseDBObject
{
	private static final long serialVersionUID = -606299592270197647L;

	public void setName(String name)
	{
		put("name", name);
	}

	public String getName()
	{
		return (String) get("name");
	}

}
