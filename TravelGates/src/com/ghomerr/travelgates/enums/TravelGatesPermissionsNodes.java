package com.ghomerr.travelgates.enums;

import com.ghomerr.travelgates.constants.TravelGatesConstants;

public enum TravelGatesPermissionsNodes
{
	// ADMINS :
	ADD(TravelGatesConstants.PERMISSIONS_ROOT + "add", true),
	
	DEL(TravelGatesConstants.PERMISSIONS_ROOT + "del", true),
	
	UPDATE(TravelGatesConstants.PERMISSIONS_ROOT + "update", true),
	
	ADMINTP(TravelGatesConstants.PERMISSIONS_ROOT + "admintp", true),
	
	SAVE(TravelGatesConstants.PERMISSIONS_ROOT + "save", true),
	
	DEBUG(TravelGatesConstants.PERMISSIONS_ROOT + "debug", true),
	
	DISPLAYTELEPORTMESSAGE(TravelGatesConstants.PERMISSIONS_ROOT + "displayteleportmessage", true),
	
	WORLDS(TravelGatesConstants.PERMISSIONS_ROOT + "worlds", true),
	
	CONFIG(TravelGatesConstants.PERMISSIONS_ROOT + "config", true),
	
	PERMS(TravelGatesConstants.PERMISSIONS_ROOT + "perms", true),
	
	SIGNTP(TravelGatesConstants.PERMISSIONS_ROOT + "signtp", true),
	
	PORTALTP(TravelGatesConstants.PERMISSIONS_ROOT + "portaltp", true),
	
	CLEARALLINV(TravelGatesConstants.PERMISSIONS_ROOT + "clearallinv", true),
	
	PROTECTADMININV(TravelGatesConstants.PERMISSIONS_ROOT + "protectadmininv", true),
	
	AUTOSAVE(TravelGatesConstants.PERMISSIONS_ROOT + "autosave", true),
	
	TPBLOCK(TravelGatesConstants.PERMISSIONS_ROOT + "tpblock", true),
	
	FORCETP(TravelGatesConstants.PERMISSIONS_ROOT + "forcetp", true),
	
	
	// USERS:
	LOC(TravelGatesConstants.PERMISSIONS_ROOT + "loc", false),
	
	LIST(TravelGatesConstants.PERMISSIONS_ROOT + "list", false),
	
	RESTRICT(TravelGatesConstants.PERMISSIONS_ROOT + "restrict", false),
	
	DETAILS(TravelGatesConstants.PERMISSIONS_ROOT + "details", false),
	
	NAME(TravelGatesConstants.PERMISSIONS_ROOT + "name", false),
	
	TELEPORT_CMD(TravelGatesConstants.PERMISSIONS_ROOT + "teleportcmd", false),
	
	TELEPORT_SIGN(TravelGatesConstants.PERMISSIONS_ROOT + "teleportsign", false),
	
	TELEPORT_PORTAL(TravelGatesConstants.PERMISSIONS_ROOT + "teleportportal", false),
	
	LEVER(TravelGatesConstants.PERMISSIONS_ROOT + "lever", false),
	
	HELP(TravelGatesConstants.PERMISSIONS_ROOT + "help", false),
	
	VERSION(TravelGatesConstants.PERMISSIONS_ROOT + "version", false);
	
	private String _permissionNode;
	
	private boolean _isAdminOnly;
	
	TravelGatesPermissionsNodes(final String permissionNode, final boolean isAdminOnly)
	{
		_permissionNode = permissionNode;
		_isAdminOnly = isAdminOnly;
	}
	
	public String getNode()
	{
		return _permissionNode;
	}
	
	public boolean isAdminOnly()
	{
		return _isAdminOnly;
	}
	
	@Override
	public String toString()
	{
		return "{" + _permissionNode + ", " + ((_isAdminOnly)? "admin" : "everyone") + "}";
	}
}
