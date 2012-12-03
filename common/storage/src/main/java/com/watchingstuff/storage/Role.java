/**
 * 
 */
package com.watchingstuff.storage;

/**
 * Indicates a role that an actor plays for a television series or movie
 * 
 * @author kruser
 */
public class Role extends BaseDBObject
{
	private static final String PROP_ROLE_NAME = "roleName";
	private static final String PROP_ACTOR = "actor";
	private static final long serialVersionUID = -2168381869734990561L;

	/**
	 * @param roleName
	 *            The name of the role, i.e. the part they are playing
	 */
	public void setRoleName(String roleName)
	{
		put(PROP_ROLE_NAME, roleName);
	}

	/**
	 * The name of the role, i.e. the part they are playing
	 * 
	 * @return
	 */
	public String getRoleName()
	{
		return (String) get(PROP_ROLE_NAME);
	}

	/**
	 * The actor portraying a role
	 * 
	 * @param actor
	 */
	public void setActor(Actor actor)
	{
		put(PROP_ACTOR, actor);
	}

	/**
	 * The actor portraying a role
	 * 
	 * @return
	 */
	public Actor getActor()
	{
		return (Actor) get(PROP_ACTOR);
	}

}
