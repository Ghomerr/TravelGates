package com.ghomerr.travelgates.enums;

import java.util.HashMap;

import org.bukkit.World;

public enum TravelGatesWorldType
{
	NORMAL("normal", World.Environment.NORMAL),
	
	NETHER("nether", World.Environment.NETHER),
	
	END("end", World.Environment.THE_END);
	
	private String _type;
	private World.Environment _env;
	
	private static HashMap<String, TravelGatesWorldType> _mapType = new HashMap<String, TravelGatesWorldType>();
	
	TravelGatesWorldType(final String type, final World.Environment env)
	{
		_type = type;
		_env = env;
	}
	
	static
	{
		for (final TravelGatesWorldType type : TravelGatesWorldType.values())
		{
			_mapType.put(type.getName(), type);
		}
	}
	
	public String getName()
	{
		return _type;
	}
	
	public World.Environment getEnv()
	{
		return _env;
	}
	
	public static TravelGatesWorldType getWorldType(final String type)
	{
		if (type != null)
		{
			return _mapType.get(type.toLowerCase());
		}
		else
		{
			return TravelGatesWorldType.NORMAL;
		}
	}
}
