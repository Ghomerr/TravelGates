package com.ghomerr.travelgates.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.enums.TravelGatesPermissionsNodes;
import com.ghomerr.travelgates.messages.TravelGatesMessages;
import com.ghomerr.travelgates.objects.TravelGatesPortalSign;
import com.ghomerr.travelgates.utils.TravelGatesUtils;

public class TravelGatesPlayerListener implements Listener
{
	private final TravelGates _plugin;

	public TravelGatesPlayerListener(final TravelGates plugin)
	{
		_plugin = plugin;
		_plugin.getPM().registerEvents(this, _plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (_plugin.isPluginEnabled() && _plugin.isSignTeleportEnabled())
		{
			final Action playerAction = event.getAction();

			if (playerAction == Action.RIGHT_CLICK_BLOCK)
			{
				final Block clickedBlock = event.getClickedBlock();

				if (TravelGatesUtils.isSign(clickedBlock))
				{
					final Sign sign = (Sign) clickedBlock.getState();

					String dest = null;

					final TravelGatesPortalSign portalSign = TravelGatesUtils.getPortalSignFromSign(sign);

					if (portalSign.isValidSign())
					{
						dest = portalSign.getDestination();

						if (dest != null)
						{
							final boolean hasDestination = _plugin.hasDestination(dest);

							if (hasDestination)
							{
								if (_plugin.hasPermission(event.getPlayer(), TravelGatesPermissionsNodes.TELEPORT_SIGN))
								{
									event.setCancelled(true);
									_plugin.teleportPlayerToDest(dest.toLowerCase(), event.getPlayer(), hasDestination, false, null);
								}
							}
							else
							{
								event.getPlayer().sendMessage(ChatColor.RED + 
										_plugin.getMessage(TravelGatesMessages.DESTINATION_DOESNT_EXIST, dest));
							}
						}
					}
				}
			}
		}
	}
}
