package com.ghomerr.travelgates.enums;

public enum TravelGatesConfigurations
{
	LANGUAGE("language"),
	
	USEPERMISSIONS("usepermissions"),
	
	TELEPORTWITHSIGN("teleportwithsign"),
	
	TELEPORTWITHPORTAL("teleportwithportal"),
	
	DEBUG("debug"),
	
	DISPLAYTELEPORTMESSAGE("displayteleportmessage"),
	
	CLEARALLINVENTORY("clearallinventory"),
	
	PROTECTADMININVENTORY("protectadmininventory"),
	
	AUTOSAVE("autosave"),
	
	TPBLOCK("tpblock"),
	
	WORLDS("worlds");
	
	private String _value;
	
	TravelGatesConfigurations(final String value)
	{
		_value = value;
	}
	
	public String value()
	{
		return _value;
	}
}
