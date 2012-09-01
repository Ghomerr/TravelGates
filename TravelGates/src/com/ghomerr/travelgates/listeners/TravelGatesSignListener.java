package com.ghomerr.travelgates.listeners;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;
import com.ghomerr.travelgates.enums.TravelGatesPermissionsNodes;
import com.ghomerr.travelgates.utils.TravelGatesUtils;

public class TravelGatesSignListener implements Listener
{
	private final Logger _LOGGER = Logger.getLogger(TravelGatesConstants.MINECRAFT);
	
	private final TravelGates _plugin;

	public TravelGatesSignListener(final TravelGates plugin)
	{
		_plugin = plugin;
		_plugin.getPM().registerEvents(this, _plugin);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event)
	{
		if (_plugin.isPluginEnabled() && _plugin.isPortalTeleportEnabled())
		{
			final Player player = event.getPlayer();
			
			boolean isPortalSign = TravelGatesUtils.isPortalSign(_plugin, event.getLines());
	
			if (isPortalSign)
			{
				if (_plugin.isDebugEnabled())
				{
					_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " onSignChange()");
				}
				
				if (!_plugin.hasPermission(player, TravelGatesPermissionsNodes.LEVER))
				{				
					event.setCancelled(true);
				}
			}
		}
	}
}
