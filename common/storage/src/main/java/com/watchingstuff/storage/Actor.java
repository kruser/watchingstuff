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
	public static final String PROP_SOURCE_ID = "sourceId";
	public static final String PROP_IMAGE = "image";

	public void setName(String name)
	{
		put("name", name);
	}

	public String getName()
	{
		return (String) get("name");
	}

	public void setSourceId(String sourceId)
	{
		put(PROP_SOURCE_ID, sourceId);
	}

	/**
	 * The source ID is the ID used in the external database that was used to populate the WatchingStuff database
	 * 
	 * @return
	 */
	public String getSourceId()
	{
		return (String) get(PROP_SOURCE_ID);
	}

	/**
	 * The path to the image for this actor.
	 * 
	 * @param image
	 */
	public void setImage(String image)
	{
		put(PROP_IMAGE, image);
	}

	/**
	 * The path to the image for this actor
	 * 
	 * @return null if there isn't a picture available
	 */
	public String getImage()
	{
		return (String) get(PROP_IMAGE);
	}
}
