package com.ghomerr.travelgates.listeners;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;
import com.ghomerr.travelgates.enums.TravelGatesCommands;
import com.ghomerr.travelgates.enums.TravelGatesOptions;
import com.ghomerr.travelgates.enums.TravelGatesPermissionsNodes;
import com.ghomerr.travelgates.messages.TravelGatesMessages;
import com.ghomerr.travelgates.objects.TravelGatesOptionsContainer;
import com.ghomerr.travelgates.utils.TravelGatesUtils;

public class TravelGatesCommandExecutor implements CommandExecutor
{
	private static final Logger _LOGGER = Logger.getLogger(TravelGatesConstants.MINECRAFT);

	private final TravelGates _plugin;

	private final String _optionPrefixe = "-";

	// private final int _targetRangeMax = 6;

	public TravelGatesCommandExecutor(final TravelGates plugin)
	{
		_plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] split)
	{
		boolean result = false;

		if (_plugin.isPluginEnabled())
		{
			if (!(sender instanceof Player))
			{
				result = false;
			}
			else
			{
				final Player player = (Player) sender;

				// /tg ...
				if (TravelGatesCommands.TRAVELGATES.has(label))
				{
					if (_plugin.isDebugEnabled())
					{
						_LOGGER.info(TravelGatesConstants.DEBUG_TAG + " Player issuing travelgates command : " + split);
					}

					if (split.length > 0)
					{
						// first arg
						final String arg1 = split[0];

						// /tg add ...
						if (TravelGatesCommands.ADD.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.ADD))
							{
								if (split.length > 1)
								{
									// dest name
									final String destination = split[1];

									// /tg add <destination>
									if (destination.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										String inputOptions = null;

										// /tg add <destination> [-options]
										if (split.length > 2)
										{
											inputOptions = split[2];
										}

										final Location playerLoc = player.getLocation();

										final TravelGatesOptionsContainer container = _plugin.createDestinationOptions(destination, inputOptions);
										Location destinationLocation = null;

										if (container.has(TravelGatesOptions.POSITION))
										{
											destinationLocation = _plugin.getLocationFromPosition(player, playerLoc, container.getPosition());
										}
										else
										{
											destinationLocation = playerLoc;
										}

										if (destinationLocation != null)
										{
											if (!_plugin.hasDestination(destination) && !_plugin.hasLocation(destinationLocation))
											{
												_plugin.addDestination(player, destination, destinationLocation, container);

												if (_plugin.hasDestination(destination))
												{
													player.sendMessage(ChatColor.GREEN
															+ _plugin.getMessage(TravelGatesMessages.DESTINATION_ADDED, ChatColor.AQUA + destination
																	+ ChatColor.GREEN));
												}
												else
												{
													player.sendMessage(ChatColor.RED
															+ _plugin.getMessage(TravelGatesMessages.DESTINATION_ADD_FAIL, ChatColor.AQUA
																	+ destination + ChatColor.RED));
												}
											}
											else
											{
												player.sendMessage(_plugin.getMessage(TravelGatesMessages.DESTINATION_ALREADY_EXISTS, ChatColor.AQUA
														+ destination + ChatColor.RED));
											}
										}
									}
									else
									{
										player.sendMessage(_plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
								}
							}
						}
						// /tg del ...
						else if (TravelGatesCommands.DEL.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.DEL))
							{
								if (split.length > 1)
								{
									final String destination = split[1];

									// /tg del <destination>
									if (destination.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										if (_plugin.hasDestination(destination))
										{
											boolean save = false;

											// /tg del <destination> -s
											if (split.length > 2)
											{
												final String options = split[2];

												if (TravelGatesUtils.stringIsNotBlank(options)
														&& options.contains(_optionPrefixe + TravelGatesOptions.SAVE.value()))
												{
													save = true;
												}
											}

											_plugin.deleteDestination(destination, save, player);
											player.sendMessage(ChatColor.GREEN
													+ _plugin.getMessage(TravelGatesMessages.DESTINATION_DEL, ChatColor.AQUA + destination
															+ ChatColor.GREEN));
										}
										else
										{
											player.sendMessage(ChatColor.RED
													+ _plugin.getMessage(TravelGatesMessages.DESTINATION_DOESNT_EXIST, ChatColor.AQUA + destination
															+ ChatColor.RED));
										}
									}
									else
									{
										player.sendMessage(_plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.USER_NOT_ALLOWED));
							}
						}
						// tg restrict <destination>
						else if (TravelGatesCommands.RESTRICT.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.RESTRICT))
							{
								if (split.length > 1)
								{
									// destination's name
									final String destination = split[1];

									// /tg restrict <destination>
									if (destination.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										if (_plugin.hasDestination(destination))
										{
											player.sendMessage(ChatColor.YELLOW + _plugin.getRestrictionsList(destination));
										}
										else
										{
											player.sendMessage(ChatColor.RED
													+ _plugin.getMessage(TravelGatesMessages.DESTINATION_DOESNT_EXIST, ChatColor.AQUA + destination
															+ ChatColor.RED));
										}
									}
									else
									{
										player.sendMessage(_plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
								}
							}
						}
						// tg loc <destination>
						else if (TravelGatesCommands.LOC.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.LOC))
							{
								if (split.length > 1)
								{
									// destination's name
									final String destination = split[1];

									// /tg loc <destination>
									if (destination.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										if (_plugin.hasDestination(destination))
										{
											player.sendMessage(ChatColor.YELLOW
													+ _plugin.getMessage(TravelGatesMessages.DESTINATION_LOCATION, destination) + ChatColor.AQUA
													+ _plugin.getShortLoc(destination));
										}
										else
										{
											player.sendMessage(ChatColor.RED
													+ _plugin.getMessage(TravelGatesMessages.DESTINATION_DOESNT_EXIST, destination));
										}
									}
									else
									{
										player.sendMessage(_plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
								}
							}
						}
						// /tg update ...
						else if (TravelGatesCommands.UPDATE.has(arg1))
						{
							// /tg options|update <destinnations> ...
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.UPDATE))
							{
								if (split.length > 1)
								{
									final String destination = split[1];

									if (destination.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										String options = null;
										if (split.length > 2)
										{
											options = split[2];
										}

										// /tg update <destination> [-options]
										if (options != null && options.startsWith(_optionPrefixe))
										{
											final TravelGatesOptionsContainer container = _plugin.createDestinationOptions(destination, options);

											final boolean updated = _plugin.updateDestination(destination.toLowerCase(), container, player);
											if (updated)
											{
												player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.OPTIONS_UPDATE_DONE));
												player.sendMessage(ChatColor.YELLOW + _plugin.getDestinationDetails(destination));
											}
											else
											{
												player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.OPTIONS_UPDATE_FAILED));
											}
										}
										else
										{
											player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.OPTIONS_ERROR));
										}
									}
									else
									{
										player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
								else
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
								}
							}
						}
						// /tg list
						else if (TravelGatesCommands.LIST.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.LIST))
							{
								player.sendMessage(ChatColor.YELLOW + _plugin.getDestinationsList());
							}
						}
						// /tg config
						else if (TravelGatesCommands.CONFIG.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.CONFIG))
							{
								player.sendMessage(_plugin.getCurrentConfiguration());
							}
						}
						// /tg perms
						else if (TravelGatesCommands.PERMS.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.PERMS))
							{
								boolean permissionsEnabled = _plugin.togglePermissionsState();

								if (permissionsEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.PERMS_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PERMS_DISABLED));
								}
							}
						}
						// /tg signtp
						else if (TravelGatesCommands.SIGNTP.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.SIGNTP))
							{
								boolean signTeleportEnabled = _plugin.toggleSignTeleportState();

								if (signTeleportEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.SIGNTP_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.SIGNTP_DISABLED));
								}
							}
						}
						// /tg portaltp
						else if (TravelGatesCommands.PORTALTP.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.PORTALTP))
							{
								boolean portalTeleportEnabled = _plugin.togglePortalTeleportState();

								if (portalTeleportEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.PORTALTP_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PORTALTP_DISABLED));
								}
							}
						}
						// /tg clearallinv
						else if (TravelGatesCommands.CLEARALLINV.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.CLEARALLINV))
							{
								boolean clearAllInventoryEnabled = _plugin.toggleClearAllInventoryState();

								if (clearAllInventoryEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.CLEARALLINV_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.CLEARALLINV_DISABLED));
								}
							}
						}
						// /tg protectadmininv
						else if (TravelGatesCommands.PROECTADMININV.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.PROTECTADMININV))
							{
								boolean clearAllInventoryEnabled = _plugin.toggleProtectAdminInventoryState();

								if (clearAllInventoryEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.PROTECTADMININV_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.PROTECTADMININV_DISABLED));
								}
							}
						}
						// /tg autosave
						else if (TravelGatesCommands.AUTOSAVE.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.AUTOSAVE))
							{
								boolean clearAllInventoryEnabled = _plugin.toggleAutoSaveState();

								if (clearAllInventoryEnabled)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.AUTOSAVE_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.AUTOSAVE_DISABLED));
								}
							}
						}
						// /tg details ...
						else if (TravelGatesCommands.DETAILS.has(arg1))
						{
							String arg2 = null;

							if (split.length > 1)
							{
								arg2 = split[1];
							}

							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.DETAILS))
							{
								// tg details
								if (arg2 == null)
								{
									player.sendMessage(ChatColor.YELLOW + _plugin.getDestinationsDetailsList());
								}
								// tg details <dest>
								else
								{
									if (arg2.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
									{
										player.sendMessage(ChatColor.YELLOW + _plugin.getDestinationDetails(arg2));
									}
									else
									{
										player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.DESTINATION_PATTERN_ERROR));
									}
								}
							}
						}
						// tg tpblock ...
						else if (TravelGatesCommands.TPBLOCK.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.TPBLOCK))
							{
								String tpblock = null;

								if (split.length > 1)
								{
									tpblock = split[1];
								}

								boolean tpBlockEnabled = false;

								// tg tpblock
								if (TravelGatesUtils.stringIsBlank(tpblock))
								{
									tpBlockEnabled = _plugin.toggleTeleportBlockState();
								}
								// tg tpblock xxx[,xxx]
								else
								{
									tpBlockEnabled = _plugin.configTeleportBlock(tpblock, true);
								}

								if (tpBlockEnabled)
								{
									player.sendMessage(ChatColor.GREEN
											+ _plugin.getMessage(TravelGatesMessages.TPBLOCK_ENABLED, ChatColor.YELLOW
													+ _plugin.getTeleportBlock().toString()));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.TPBLOCK_DISABLED));
								}
							}
						}

						// /tg worlds...
						else if (TravelGatesCommands.WORLDS.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.WORLDS))
							{
								String arg2 = null;

								if (split.length > 1)
								{
									arg2 = split[1];

									// /tg worlds (<world>|*) ...
									if (arg2 != null)
									{
										String arg3 = null;

										if (split.length > 2)
										{
											arg3 = split[2];
										}

										// /tg worlds <world> ...
										if (arg3 != null && arg3.startsWith(TravelGatesConstants.OPTION_PREFIX))
										{
											if (arg2.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
											{
												final TravelGatesOptions option = TravelGatesOptions.get(arg3.substring(1, 2));

												if (option != null)
												{
													switch (option)
													{
														// /tg worlds -l ...
														case LOADWORLD:
															String type = null;

															// /tg worlds <world> -l [type]
															if (split.length > 3)
															{
																type = split[3];
															}

															final World loadedWorld = _plugin.loadWorld(arg2, type);
															player.sendMessage(_plugin.getWorldState(loadedWorld, arg2));
															break;

														// /tg worlds <world> -u
														case UNLOADWORLD:
															final World unloadedWorld = _plugin.unloadWorld(arg2);

															if (unloadedWorld != null)
															{
																final String number = String.valueOf(unloadedWorld.getPlayers().size());
																player.sendMessage(ChatColor.RED
																		+ _plugin.getMessage(TravelGatesMessages.CANNOT_UNLOAD_WORLD_WITH_PLAYERS,
																				ChatColor.AQUA + arg2 + ChatColor.RED, ChatColor.AQUA + number
																						+ ChatColor.RED));
															}

															player.sendMessage(_plugin.getWorldState(unloadedWorld, arg2));
															break;

														default:
															player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.UNKNOWN_OPTION));
													}
												}
												else
												{
													player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.UNKNOWN_OPTION));
												}
											}
											else
											{
												player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.WORLD_PATTERN_ERROR));
											}
										}
										// tg worlds (<world>|*)
										else
										{
											// /tg worlds <world>
											if (arg2.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
											{
												player.sendMessage(_plugin.getWorldState(arg2));
											}
											// /tg worlds *
											else if (arg2.equals(TravelGatesConstants.WILDCARD))
											{
												player.sendMessage(_plugin.getAllWorldsFromServerDirectory());
											}
											else
											{
												player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.WORLD_PATTERN_ERROR));
											}
										}
									}
								}
								// /tg worlds
								else
								{
									player.sendMessage(_plugin.getListOfWorlds());
								}
							}
						}
						// /tg name
						else if (TravelGatesCommands.NAME.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.NAME))
							{
								final String destination = _plugin.getDestination(player.getLocation());

								if (destination != null)
								{
									player.sendMessage(ChatColor.YELLOW
											+ _plugin.getMessage(TravelGatesMessages.YOU_ARE_AT, ChatColor.AQUA + destination));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.NO_STANDING_ON_DESTINATION));
								}
							}
						}
						// /tg save
						else if (TravelGatesCommands.SAVE.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.SAVE))
							{
								final boolean saved = _plugin.saveAll();
								if (saved)
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.SAVE_DONE));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.SAVE_FAILED));
								}
							}
						}
						// /tg help
						else if (TravelGatesCommands.HELP.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.HELP))
							{
								player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
							}
						}
						// /tg version
						else if (TravelGatesCommands.VERSION.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.VERSION))
							{
								player.sendMessage(ChatColor.YELLOW + _plugin.getDescription().getVersion());
							}
						}
						// /tg debug
						else if (TravelGatesCommands.DEBUG.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.DEBUG))
							{
								_plugin.toggleDebugState();

								if (_plugin.isDebugEnabled())
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.DEBUG_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.DEBUG_DISABLED));
								}
							}
						}
						// /tg displayteleportmessage
						else if (TravelGatesCommands.DISPLAYTELEPORTMESSAGE.has(arg1))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.DISPLAYTELEPORTMESSAGE))
							{
								if (_plugin.toggleDisplayTeleportMessage())
								{
									player.sendMessage(ChatColor.GREEN + _plugin.getMessage(TravelGatesMessages.DISPLAY_TELEPORT_MESSAGE_ENABLED));
								}
								else
								{
									player.sendMessage(ChatColor.RED + _plugin.getMessage(TravelGatesMessages.DISPLAY_TELEPORT_MESSAGE_DISABLED));
								}
							}
						}
						// /tg <destination> ...
						else if (arg1.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
						{
							if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.TELEPORT_CMD))
							{
								String ignoreOption = null;
								boolean ignorePlayerLocation = false;

								if (split.length > 1)
								{
									ignoreOption = split[1];
									if ((_optionPrefixe + TravelGatesOptions.FORCETP.value()).equalsIgnoreCase(ignoreOption))
									{
										if (_plugin.hasPermission(player, TravelGatesPermissionsNodes.FORCETP))
										{
											ignorePlayerLocation = true;
										}
									}
								}

								_plugin.teleportPlayerToDest(arg1, player, false, ignorePlayerLocation, null);
							}
						}
						else
						{
							player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
						}
					}
					// /tg
					else
					{
						player.sendMessage(ChatColor.YELLOW + _plugin.getMessage(TravelGatesMessages.SHORT_HELP));
					}
				}
			}
		}

		return result;
	}
}
