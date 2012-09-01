package com.ghomerr.travelgates.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;
import com.ghomerr.travelgates.enums.TravelGatesOptions;
import com.ghomerr.travelgates.utils.TravelGatesUtils;

public class TravelGatesOptionsContainer
{
	public TravelGates _plugin = null;
	
	private static final Logger _LOGGER = Logger.getLogger(TravelGatesConstants.MINECRAFT);
	
	private HashMap<TravelGatesOptions, Boolean> _mapOptionByEnum = new HashMap<TravelGatesOptions, Boolean>();
	private HashSet<String> _restrictionsList = new HashSet<String>();
	private String _shortLocation = null;
	
	public static final String debug = TravelGatesConstants.DEBUG_TAG;
	
	public TravelGatesOptionsContainer(final TravelGates plugin)
	{
		super();
		
		_plugin = plugin;
		
		for (TravelGatesOptions option : TravelGatesOptions.values())
		{
			_mapOptionByEnum.put(option, false);
		}
	}
	
	public TravelGatesOptionsContainer(final TravelGates plugin, final String str)
	{
		super();
		
		String options = null;
		_plugin = plugin;
		
		if (_plugin.isDebugEnabled())
		{
			_LOGGER.info(debug + " TravelGatesOptionsContainer(str=" + str + ")");
		}
			
		int index = str.lastIndexOf(TravelGatesConstants.DELIMITER);
		
		if (index > 0)
		{
			options = str.substring(index + 1);
		}
		else
		{
			options = str;
		}
		
		for (TravelGatesOptions option : TravelGatesOptions.values())
		{
			if (option.isData())
			{
				_mapOptionByEnum.put(option, options.contains(option.value()));		
			}
			else
			{
				_mapOptionByEnum.put(option, false);
			}
		}
	}	

	public boolean has(final TravelGatesOptions option)
	{
		boolean ret = _mapOptionByEnum.get(option).booleanValue();
		
		if (option == TravelGatesOptions.SAVE && ret)
		{
			set(TravelGatesOptions.SAVE, false);
		}
		
		return ret;		
	}
	
	public void set(final TravelGatesOptions option, boolean value)
	{
		_mapOptionByEnum.put(option, value);
	}
	
	public HashSet<String> getRestrictionsList()
	{
		return (_mapOptionByEnum.get(TravelGatesOptions.RESTRICTION))? _restrictionsList : null;
	}
	
	public String getRestrictionsListString()
	{
		String list = "";
		
		for (final String dest : _restrictionsList)
		{
			if (list.length() > 0)
			{
				list += TravelGatesConstants.DELIMITER;
			}
			
			list += dest;
		}
		
		return list;
	}

	public void setRestrictionsList(final String restrictionsList)
	{
		if (_plugin.isDebugEnabled())
		{
			_LOGGER.info(debug + " setRestrictionsList(restrictionsList=" + restrictionsList + ")");
		}
		
		if (TravelGatesUtils.stringIsBlank(restrictionsList))
		{
			clearRestrictionsList();
		}
		else
		{
			String[] split = restrictionsList.split(TravelGatesConstants.DELIMITER);
			
			if (restrictionsList != null && restrictionsList.matches(TravelGatesConstants.UPDATE_RESTRICTIONS_PATTERN))
			{
				for (final String dest : split)
				{
					if (dest.matches(TravelGatesConstants.ADD_RESTRICTION_PATTERN))
					{
						addAllowedDestination(dest.substring(1));
					}
					else if (dest.matches(TravelGatesConstants.REMOVE_RESTTRICTION_PATTERN))
					{
						removeAllowedDestination(dest.substring(1));
					}
				}
			}
			else
			{
				final HashSet<String> list = new HashSet<String>();
				
				for (final String dest : split)
				{
					if (_plugin.hasDestination(dest))
					{
						list.add(dest);
					}
				}
	
				setRestrictionsList(list);
			}
		}
	}
	
	public void setRestrictionsList(final HashSet<String> restrictionsList)
	{
		if (!has(TravelGatesOptions.RESTRICTION) && _restrictionsList.isEmpty() && restrictionsList != null && !restrictionsList.isEmpty())
		{
			_mapOptionByEnum.put(TravelGatesOptions.RESTRICTION, true);
			this._restrictionsList = restrictionsList;
		}
		else if (has(TravelGatesOptions.RESTRICTION) && (restrictionsList == null || restrictionsList.isEmpty()))
		{
			clearRestrictionsList();
		}
		else
		{
			this._restrictionsList = restrictionsList;
		}
	}
	
	public boolean isDestinationAllowed(final String destination)
	{
		return (has(TravelGatesOptions.RESTRICTION))? _restrictionsList.contains(destination.toLowerCase()) : true;
	}
	
	public void addAllowedDestination(final String destination)
	{
		if(!has(TravelGatesOptions.RESTRICTION) && _restrictionsList.isEmpty())
		{
			_mapOptionByEnum.put(TravelGatesOptions.RESTRICTION, true);
		}
		_restrictionsList.add(destination.toLowerCase());
	}
	
	public void removeAllowedDestination(final String destination)
	{
		_restrictionsList.remove(destination.toLowerCase());
		
		if(has(TravelGatesOptions.RESTRICTION) && _restrictionsList.isEmpty())
		{
			_mapOptionByEnum.put(TravelGatesOptions.RESTRICTION, false);
		}
	}
	
	public void clearRestrictionsList()
	{
		_restrictionsList.clear();
		_mapOptionByEnum.put(TravelGatesOptions.RESTRICTION, false);
	}
	
	public void setPosition(final String shortLoc)
	{
		_shortLocation = shortLoc;
		_mapOptionByEnum.put(TravelGatesOptions.POSITION, true);
	}
	
	public String getPosition()
	{
		String retPos = _shortLocation;
		
		_shortLocation = null;
		_mapOptionByEnum.put(TravelGatesOptions.POSITION, false);
		
		return retPos;
	}
	
	public void clear()
	{
		_restrictionsList = null;
		_shortLocation = null;
		_mapOptionByEnum = null;
	}
	
	public String getOptionsForData()
	{
		String optionsData = "";
		
		for (TravelGatesOptions option : _mapOptionByEnum.keySet())
		{
			if (has(option) && option.isData())
			{
				optionsData += option.value();
			}
		}
		
		return optionsData;
	}
}
