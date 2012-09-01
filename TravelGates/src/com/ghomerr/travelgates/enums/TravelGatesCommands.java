package com.ghomerr.travelgates.enums;

import java.util.HashSet;

import com.ghomerr.travelgates.utils.TravelGatesUtils;

public enum TravelGatesCommands
{
	ADD(new String[]
	{ "a", "add" }),

	DEL(new String[]
	{ "dl", "rm", "del", "rem", "delete", "remove" }),

	DETAILS(new String[]
	{ "dt", "det", "detail", "details" }),

	HELP(new String[]
	{ "h", "help" }),

	LIST(new String[]
	{ "ls", "list", "liste" }),

	LOC(new String[]
	{ "lc", "loc", "location" }),

	NAME(new String[]
	{ "n", "name" }),

	TRAVELGATES(new String[]
	{ "tg", "travelgates" }),

	SAVE(new String[]
	{ "s", "sv", "save" }),

	UPDATE(new String[]
	{ "u", "o", "up", "opt", "update", "option", "options" }),

	DEBUG(new String[] {"db","debug"}),

	WORLDS(new String[] {"w","world","worlds"}),

	RESTRICT(new String[] {"r","rst","restrict","restricts","restriction","restrictions"}),

	CONFIG(new String[] {"c","conf","config","configuration"}),

	PERMS(new String[] {"p","perm", "perms", "useperm", "useperms", "permission", "permissions", "usepermissions"}),
	
	SIGNTP(new String[] {"stp", "signtp", "signteleport", "teleportwithsign"}),
	
	PORTALTP(new String[] {"ptp", "portaltp", "portalteleport", "teleportwithportal"}),
	
	CLEARALLINV(new String[] {"cai", "clr", "clear", "clearall", "clearinv", "clearallinv", "clearallinventory"}),
	
	AUTOSAVE(new String[] {"as", "autosave"}),
	
	TPBLOCK(new String[] {"tpb", "tpblk", "tpblc", "tpblock", "tpbloc", "teleportblock"}),
	
	VERSION(new String[] {"v", "ver", "version"});

	private HashSet<String> _list;

	TravelGatesCommands(final String[] tab)
	{
		_list = new HashSet<String>();
		for (String cmd : tab)
		{
			_list.add(cmd);
		}
	}

	public HashSet<String> list()
	{
		return _list;
	}

	public boolean has(final String cmd)
	{
		return _list.contains(cmd.toLowerCase());
	}
	
	public static boolean containsTravelGatesCommand(final String line)
	{
		boolean ret = false;

		if (TravelGatesUtils.stringIsNotBlank(line))
		{
			final String lowerCaseLine = line.toLowerCase();
			
			for(String cmd : TRAVELGATES.list())
			{
				ret = ret || lowerCaseLine.contains("[" + cmd + "]");
			}
		}
		
		return ret;
	}
}
