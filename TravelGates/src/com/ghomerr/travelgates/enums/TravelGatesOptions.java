package com.ghomerr.travelgates.enums;

import java.util.HashMap;

public enum TravelGatesOptions
{
	INVENTORY("i", true, true),
	
	ADMINTP("a", true, true),
	
	SAVE("s", false, true),

	POSITION("p", false, true),
	
	RESTRICTION("r", true, true),
	
	FORCETP("f", false, false),
	
	LOADWORLD("l", false, false),
	
	UNLOADWORLD("u", false, false);
	
	private String _value;
	private boolean _isData;
	private boolean _isDestinationOption;
	
	private static HashMap<String, TravelGatesOptions> _allOptions = new HashMap<String, TravelGatesOptions>();
	
	TravelGatesOptions(final String value, final boolean isData, final boolean isDestOption)
	{
		_value = value;
		_isData = isData;
		_isDestinationOption = isDestOption;
	}

	public String value()
	{
		return _value;
	}
	
	public boolean isData()
	{
		return _isData;
	}
	
	public boolean isDestinationOption()
	{
		return _isDestinationOption;
	}
	
	static 
	{
		for (TravelGatesOptions option : TravelGatesOptions.values())
		{
			_allOptions.put(option.value(), option);
		}
	}
	
	public static TravelGatesOptions get(final String option)
	{
		return _allOptions.get(option);
	}
}
