package com.ghomerr.travelgates.listeners;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.material.Lever;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;
import com.ghomerr.travelgates.enums.TravelGatesBlockFaces;
import com.ghomerr.travelgates.enums.TravelGatesPermissionsNodes;
import com.ghomerr.travelgates.messages.TravelGatesMessages;
import com.ghomerr.travelgates.objects.TravelGatesPortalSign;
import com.ghomerr.travelgates.utils.TravelGatesUtils;

public class TravelGatesPortalListener implements Listener
{
	private static final Logger	_LOGGER	= Logger.getLogger(TravelGatesConstants.MINECRAFT);

	private final TravelGates	_plugin;

	public TravelGatesPortalListener(final TravelGates plugin)
	{
		_plugin = plugin;
		_plugin.getPM().registerEvents(this, _plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPortal(final PlayerPortalEvent event)
	{
		if (!event.isCancelled() && _plugin.isPluginEnabled() && _plugin.isPortalTeleportEnabled())
		{
			if (_plugin.isDebugEnabled())
			{
				_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " Using portal.");
			}
			
			final Player player = event.getPlayer();

			final Location playerLocation = player.getLocation();

			final World world = playerLocation.getWorld();

			final Block playerBlock = world.getBlockAt(playerLocation);
			
			Block portalBlock = null;
			
			// Player in portal ?
			if (playerBlock.getType() == Material.PORTAL)
			{
				if (_plugin.isDebugEnabled())
				{
					_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " Player was in portal.");
				}
				portalBlock = playerBlock;
			}

			if (portalBlock == null)
			{
				// Search portal block
				for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
				{
					if (tgFace.isSimple())
					{
						final Block block = playerBlock.getRelative(tgFace.face());
	
						if (block.getType() == Material.PORTAL)
						{
							portalBlock = block;
							break;
						}
					}
				}
			}

			if (portalBlock != null)
			{
				if (_plugin.isDebugEnabled())
				{
					_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " Portal block found here: " 
						+ TravelGatesUtils.locationToShortString(portalBlock.getLocation()) 
						+ " = " + portalBlock.getLocation());
				}
				
				// Check portal size
				boolean otherPortalBlockFound = false;
				TravelGatesBlockFaces otherPortalBlockFace = null;
				for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
				{
					if (tgFace.isSimple())
					{
						final Block block = portalBlock.getRelative(tgFace.face());
						
						if (block.getType() == Material.PORTAL)
						{
							otherPortalBlockFound = true;
							otherPortalBlockFace = tgFace;
							break;
						}
					}
				}
				if (!otherPortalBlockFound) 
				{
					player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIZE_INVALID));
					player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
					return;
				}
				
				final Location currentDestinationLocation = TravelGatesUtils.getDestinationLocationNearPortal(_plugin, playerLocation, portalBlock);

				if (currentDestinationLocation != null)
				{
					final Sign portalSign = TravelGatesUtils.getSignOnPortalFrames(portalBlock, otherPortalBlockFace, world);

					// Sign info
					if (portalSign != null)
					{
						final TravelGatesPortalSign tgPortalSign = TravelGatesUtils.getPortalSignFromPortal(_plugin, portalSign);

						final boolean isValidSign = tgPortalSign.isValidSign();
						final String destination = tgPortalSign.getDestination();
						final int stateLine = tgPortalSign.getFilledLine();

						// Valid sign
						if (isValidSign && destination != null && stateLine > TravelGatesConstants.REGULAR_SIGN)
						{
							// State line is ON
							if (portalSign.getLine(stateLine).contains(_plugin.getMessage(TravelGatesMessages.ON)))
							{
								if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.TELEPORT_PORTAL))
								{
									final String currentDestShortLoc = TravelGatesUtils.locationToShortString(currentDestinationLocation);
									// Use existing method to do the TP job...
									// TODO: la currentDestLocation n'est pas une dest forcément, donc ça pète après
									if(_plugin.teleportPlayerToDest(destination, player, false, false, currentDestShortLoc))
									{
										event.setCancelled(true);
									}
									
//									if (_plugin.hasDestination(destination))
//									{
//										final String targetDestinationfullLoc = _plugin.getFullLoc(destination);
//										final String targetDestinationShortLoc = _plugin.getShortLoc(destination);
//
//										final boolean hasAdminTP = _plugin.getOptionOfDestination(destination, TravelGatesOptions.ADMINTP);
//
//										// Admin TP only
//										if (hasAdminTP)
//
//										{
//											final boolean hasPerm = _plugin.hasPermission(player, TravelGatesPermissionsNodes.ADMINTP);
//											
//											if (!hasPerm)
//											{
//												player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.ONLY_ADMIN_TP));
//												player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
//												return;
//											}
//										}
//										
//										final String currentDestShortLoc = TravelGatesUtils.locationToShortString(currentDestinationLocation);
//
//										final boolean sameLocations = targetDestinationShortLoc.equalsIgnoreCase(currentDestShortLoc);
//
//										// Portal is on existing Location
//										// and destination is not the
//										// current where is standing the
//										// player
//										String currentDestName = null;
//
//										if (!sameLocations)
//										{
//											currentDestName = _plugin.getDestination(currentDestShortLoc);
//											
//											if (currentDestName != null && _plugin.getOptionOfDestination(currentDestName, TravelGatesOptions.RESTRICTION))
//											{
//												final TravelGatesOptionsContainer container = _plugin.getOptionsOfDestination(currentDestName);
//
//												if (container != null && !container.isDestinationAllowed(destination))
//												{
//													player.sendMessage(ChatColor.RED
//															+ _plugin.getMessage(TravelGatesMessages.DESTINATION_IS_RESTRICTED, currentDestName,
//																	destination));
//
//													player.sendMessage(ChatColor.YELLOW
//															+ _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
//
//													return;
//												}
//											}
//											else
//											{
//												if (currentDestName == null)
//												{
//													_LOGGER.severe(TravelGatesConstants.PLUGIN_TAG + " Current destination not found for Portal Teleportation");
//												}
//											}
//
//											// TODO : revoir fullStringToLocation en plus simple ? <- tp system refactoring
//											final Location targetLocation = TravelGatesUtils.fullStringToLocation(targetDestinationfullLoc,
//													player.getServer().getWorlds());
//
//											if (targetLocation.getWorld() != null)
//											{
//												event.setCancelled(true);
//												player.teleport(targetLocation);
//											}
//											else
//											{
//												player.sendMessage(ChatColor.RED
//														+ _plugin.getMessage(TravelGatesMessages.TELEPORT_CANCELLED_WORLD_UNLOADED,
//																ChatColor.AQUA + destination + ChatColor.RED));
//												player.sendMessage(ChatColor.YELLOW
//														+ _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
//												return;
//											}
//
//											_LOGGER.info(TravelGatesConstants.PLUGIN_TAG + " " + player.getName()
//													+ " has used a Nether Portal to travel from " + currentDestName + " to " + destination);
//
//											final boolean inventoryCleared = _plugin.getOptionOfDestination(destination,
//													TravelGatesOptions.INVENTORY);
//											if (!inventoryCleared || _plugin.isProtectedInventory(player))
//											{
//												player.sendMessage(ChatColor.YELLOW
//														+ _plugin.getMessage(TravelGatesMessages.YOU_ARE_ARRIVED_AT, destination)
//														+ ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.INVENTORY_KEPT));
//											}
//											else
//											{
//												player.getInventory().clear();
//												player.sendMessage(ChatColor.YELLOW
//														+ _plugin.getMessage(TravelGatesMessages.YOU_ARE_ARRIVED_AT, destination) + ChatColor.RED
//														+ _plugin.getMessage(TravelGatesMessages.INVENTORY_LOST));
//											}
//
//											final Chunk arrivalChunk = player.getWorld().getChunkAt(targetLocation);
//											if (!arrivalChunk.isLoaded())
//											{
//												if (_plugin.isDebugEnabled())
//												{
//													_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " The " + destination
//															+ "'s chunk was not loaded at " + targetLocation);
//												}
//
//												arrivalChunk.load();
//											}
//										}
//										else
//										{
//											player.sendMessage(ChatColor.RED
//													+ _plugin.getMessage(TravelGatesMessages.YOURE_ALREADY_AT, destination));
//											player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.PORTAL_TP_CANCELLED));
//											event.setCancelled(true);
//										}
//									}
//									else
//									{
//										player.sendMessage(ChatColor.RED
//												+ _plugin.getMessage(TravelGatesMessages.DESTINATION_DOESNT_EXIST, destination));
//										player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
//									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_IS_OFF));
								player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
							}
						}
						else if (stateLine == TravelGatesConstants.NO_VALID_TRAVELGATE_SIGN)
						{
							player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_INVALID));
							player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
						}
						else if (stateLine == TravelGatesConstants.NO_DESTINATION_ON_SIGN)
						{
							player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_NO_DEST));
							player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
						}
						else
						{
							player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.TELEPORT_TO_NETHER));
						}
					}
				}
				else
				{
					if (_plugin.isDebugEnabled())
					{
						_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " No destination found.");
					}
				}
			}
			else
			{
				if (_plugin.isDebugEnabled())
				{
					_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " No portal block found.");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (_plugin.isPluginEnabled() && _plugin.isPortalTeleportEnabled())
		{
			final Action playerAction = event.getAction();
			final Player player = event.getPlayer();

			// Right click
			if (playerAction == Action.RIGHT_CLICK_BLOCK)
			{
				final Block clickedBlock = event.getClickedBlock();

				// On Lever
				if (clickedBlock.getType() == Material.LEVER)
				{
					final Lever lever = new Lever(Material.LEVER, clickedBlock.getData());

					final BlockFace leverAttachedFace = lever.getAttachedFace();

					final Block attachedBlock = clickedBlock.getRelative(leverAttachedFace);

//					final Block adjacentToAttachedBlock = clickedBlock.getRelative(leverAttachedFace, 2);

					// Attached on Portal FRAME
					if (attachedBlock.getType() != Material.AIR /*&& adjacentToAttachedBlock.getType() == Material.PORTAL*/)
					{
						final BlockFace leverOppositeFace = leverAttachedFace.getOppositeFace();

						// Check frame Faces
						for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
						{
							final BlockFace face = tgFace.face();

							// Not on LEVER Face or OPPOSITE Face
							if (face != leverOppositeFace && face != leverAttachedFace)
							{
								final Block sideBlock = attachedBlock.getRelative(face);

								// Faced to a Sign
								if (TravelGatesUtils.isSign(sideBlock))
								{
									if (_plugin.isDebugEnabled())
									{
										_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " onPlayerInteract()");
									}

									final Sign sideSign = (Sign) sideBlock.getState();

									int selectedLine = TravelGatesUtils.getAvailableLineOnSign(_plugin, sideSign);

									if (selectedLine >= 0)
									{
										if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.LEVER))
										{
											// Add Lever state to the sign
											if (lever.isPowered())
											{
												sideSign.setLine(selectedLine, ChatColor.RED + _plugin.getMessage(TravelGatesMessages.ON));
											}
											else
											{
												sideSign.setLine(selectedLine, ChatColor.GRAY + _plugin.getMessage(TravelGatesMessages.OFF));
											}

											// Update Sign
											final boolean updateSucceeded = sideSign.update();
											if (!updateSucceeded)
											{
												player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_UPDATE_FAILED));
											}
										}
									}
									else if (selectedLine == TravelGatesConstants.NO_VALID_TRAVELGATE_SIGN)
									{
										player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_INVALID));
									}
									else if (selectedLine == TravelGatesConstants.NO_DESTINATION_ON_SIGN)
									{
										player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_NO_DEST));
									}
									else if (selectedLine == TravelGatesConstants.NO_AVAILABLE_LINE_ON_SIGN)
									{
										player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTAL_SIGN_UNAVAILABLE_LINE));
									}
									// Else : regular sign

									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
