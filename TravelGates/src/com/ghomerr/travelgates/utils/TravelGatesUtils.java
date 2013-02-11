package com.ghomerr.travelgates.utils;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;
import com.ghomerr.travelgates.enums.TravelGatesBlockFaces;
import com.ghomerr.travelgates.enums.TravelGatesCommands;
import com.ghomerr.travelgates.objects.TravelGatesPortalSign;

public class TravelGatesUtils
{
	private static final Logger _LOGGER = Logger.getLogger("Minecraft");

	private static boolean _isDebugEnabled = false;

	private static final String _debug = TravelGatesConstants.DEBUG_TAG;

	public static void setDebugState(final boolean state)
	{
		_isDebugEnabled = state;
	}

	public static String floor(final Double value)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start floor(value=" + value + ")");
		}

		final String str = String.valueOf(value);
		final int i = str.indexOf(",");
		final int index = (i > 0) ? i : str.indexOf(".");

		final String ret = str.substring(0, index);

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End floor : " + ret);
		}

		return ret;
	}

	public static String floor(final Float value)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start floor(value=" + value + ")");
		}

		final String str = String.valueOf(value);
		final int i = str.indexOf(",");
		final int index = (i > 0) ? i : str.indexOf(".");

		final String ret = str.substring(0, index);

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End floor : " + ret);
		}

		return ret;
	}

	public static String locationToShortString(final Location location)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start locationToShortString(location=" + location + ")");
		}

		final StringBuilder strBldr = new StringBuilder();

		strBldr.append(location.getWorld().getName()).append(TravelGatesConstants.DELIMITER)
				.append(floor(location.getX())).append(TravelGatesConstants.DELIMITER).append(floor(location.getY()))
				.append(TravelGatesConstants.DELIMITER).append(floor(location.getZ()));

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End locationToShortString : " + strBldr.toString());
		}

		return strBldr.toString();
	}

	public static String locationToFullString(final Location location)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start locationToFullString(location=" + location + ")");
		}

		final StringBuilder strBldr = new StringBuilder();

		strBldr.append(location.getWorld().getName()).append(TravelGatesConstants.DELIMITER)
				.append(floor(location.getX())).append(TravelGatesConstants.DELIMITER).append(floor(location.getY()))
				.append(TravelGatesConstants.DELIMITER).append(floor(location.getZ()))
				.append(TravelGatesConstants.DELIMITER).append(floor(location.getYaw()));

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End locationToFullString : " + strBldr.toString());
		}

		return strBldr.toString();
	}

	public static Location fullStringToLocation(final String fullString, final List<World> worlds)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start fullStringToLocation(fullString=" + fullString + ", worlds=" + worlds + ")");
		}

		final String[] tab = fullString.split(TravelGatesConstants.DELIMITER);
		World worldFound = null;
		Location newLoc = null;

		if (tab.length > 4)
		{
			for (final World world : worlds)
			{
				if (world.getName().equalsIgnoreCase(tab[0]))
				{
					worldFound = world;
					break;
				}
			}

			double posX = Double.parseDouble(tab[1]);
			double posZ = Double.parseDouble(tab[3]);

			posX = (posX >= 0) ? posX + 0.5 : posX - 0.5;
			posZ = (posZ >= 0) ? posZ + 0.5 : posZ - 0.5;

			newLoc = new Location(worldFound, posX, Double.parseDouble(tab[2]), posZ, Float.parseFloat(tab[4]), 0f);

			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " Start fullStringToLocation : " + newLoc);
			}
			return newLoc;
		}
		else
		{
			_LOGGER.severe(TravelGatesConstants.PLUGIN_TAG + " The string '" + fullString + "' is invalid.");
			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " End fullStringToLocation : null");
			}
			return null;
		}
	}

	public static Location shortStringToLocation(final String shortLoc, List<World> worlds)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start shortStringToLocation(shortLoc=" + shortLoc + ", worlds=" + worlds + ")");
		}

		final String[] tab = shortLoc.split(TravelGatesConstants.DELIMITER);
		World worldFound = null;
		Location newLoc = null;

		if (tab.length > 3)
		{
			for (final World world : worlds)
			{
				if (world.getName().equalsIgnoreCase(tab[0]))
				{
					worldFound = world;
					break;
				}
			}

			double posX = Double.parseDouble(tab[1]);
			double posZ = Double.parseDouble(tab[3]);

			posX = (posX >= 0) ? posX + 0.5 : posX - 0.5;
			posZ = (posZ >= 0) ? posZ + 0.5 : posZ - 0.5;

			newLoc = new Location(worldFound, posX, Double.parseDouble(tab[2]), posZ, 0f, 0f);

			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " Start shortStringToLocation : " + newLoc);
			}
			return newLoc;
		}
		else
		{
			_LOGGER.severe(TravelGatesConstants.PLUGIN_TAG + " The string '" + shortLoc + "' is invalid.");
			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " End shortStringToLocation : null");
			}
			return null;
		}
	}

	public static String fullStringToShortString(final String fullString)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start fullStringToShortString(fullString=" + fullString + ")");
		}

		final String[] split = fullString.split(TravelGatesConstants.DELIMITER);

		if (split.length > 3)
		{
			final StringBuilder strBuild = new StringBuilder();

			strBuild.append(split[0]).append(TravelGatesConstants.DELIMITER).append(split[1])
					.append(TravelGatesConstants.DELIMITER).append(split[2]).append(TravelGatesConstants.DELIMITER)
					.append(split[3]);

			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " End fullStringToShortString : " + strBuild.toString());
			}
			return strBuild.toString();
		}
		else
		{
			_LOGGER.severe(TravelGatesConstants.PLUGIN_TAG + " The destinations file is invalid : " + fullString);
			if (_isDebugEnabled)
			{
				_LOGGER.info(_debug + " End fullStringToShortString : null");
			}
			return null;
		}
	}

	public static boolean isSign(final Block block)
	{
		return block.getType() == Material.SIGN || block.getType() == Material.WALL_SIGN
				|| block.getType() == Material.SIGN_POST;
	}

	public static boolean isPortalSign(final TravelGates plugin, final String[] lines)
	{
		boolean hasTag = false;
		boolean hasState = false;

		final String onState = plugin.getPortalSignOnState();
		final String offState = plugin.getPortalSignOffState();

		boolean isPortalSign = false;

		for (final String line : lines)
		{
			if (TravelGatesCommands.containsTravelGatesCommand(line))
			{
				hasTag = true;
			}

			if (line.contains(onState) || line.contains(offState))
			{
				hasState = true;
			}

			isPortalSign = hasTag && hasState;

			if (isPortalSign)
			{
				break;
			}
		}

		return isPortalSign;
	}

	public static boolean stringIsBlank(final String str)
	{
		return (str == null || str.isEmpty() || str.matches(TravelGatesConstants.BLANK_STRING_PATTERN));
	}

	public static boolean stringIsNotBlank(final String str)
	{
		return !stringIsBlank(str);
	}

	public static TravelGatesPortalSign getPortalSignFromSign(final Sign sign)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getDestinationInSign(sign=" + sign + ")");
		}

		final String[] lines = sign.getLines();

		TravelGatesPortalSign portalSign = new TravelGatesPortalSign();

		for (final String line : lines)
		{
			portalSign = getPortalSign(portalSign, line);
		}

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getDestinationInSign : " + portalSign.getDestination());
		}

		return portalSign;
	}

	public static int getAvailableLineOnSign(final TravelGates plugin, final Sign sign)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getAvailableLineOnSign(sign=" + sign + ")");
		}

		int selectedLine = -1;

		TravelGatesPortalSign portalSign = new TravelGatesPortalSign();

		// Search filled or empty line
		for (int i = 0 ; i < sign.getLines().length ; i++)
		{
			final String line = sign.getLine(i);

			portalSign = getPortalSign(portalSign, plugin, line, i);
		}

		selectedLine = getSelectedLineFromPortalSign(portalSign, false);

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getAvailableLineOnSign : " + selectedLine);
		}

		return selectedLine;
	}

	public static TravelGatesPortalSign getPortalSignFromPortal(final TravelGates plugin, final Sign sign)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getPortalSign(sign=" + sign + ")");
		}

		TravelGatesPortalSign portalSign = new TravelGatesPortalSign();

		// Search filled or empty line
		for (int i = 0 ; i < sign.getLines().length ; i++)
		{
			final String line = sign.getLine(i);

			portalSign = getPortalSign(portalSign, plugin, line, i);
		}

		portalSign.setFilledLine(getSelectedLineFromPortalSign(portalSign, true));

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getPortalSign : " + portalSign);
		}

		return portalSign;
	}

	public static int getSelectedLineFromPortalSign(final TravelGatesPortalSign portalSign, final boolean onlyFilledLine)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getSelectedLineFromPortalSign(portalSign=" + portalSign + ", onlyFilledLine="
					+ onlyFilledLine + ")");
		}

		int selectedLine = TravelGatesConstants.REGULAR_SIGN;

		if (portalSign.isValidSign() && portalSign.getDestination() != null)
		{
			if (portalSign.getFilledLine() >= 0)
			{
				selectedLine = portalSign.getFilledLine();
			}
			else if (!onlyFilledLine && portalSign.getEmptyLine() >= 0)
			{
				selectedLine = portalSign.getEmptyLine();
			}
			else
			{
				selectedLine = TravelGatesConstants.NO_AVAILABLE_LINE_ON_SIGN;
			}
		}
		else if (!portalSign.isValidSign())
		{
			selectedLine = TravelGatesConstants.NO_VALID_TRAVELGATE_SIGN;
		}
		else if (portalSign.getDestination() == null)
		{
			selectedLine = TravelGatesConstants.NO_DESTINATION_ON_SIGN;
		}

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getSelectedLineFromPortalSign : " + selectedLine);
		}

		return selectedLine;
	}

	private static TravelGatesPortalSign getPortalSign(final TravelGatesPortalSign portalSign, final String line)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getPortalSign(portalSign=" + portalSign + ", line=" + line + ")");
		}

		// Travel Gates Config and eventually Destination
		if (!portalSign.isValidSign() && TravelGatesCommands.containsTravelGatesCommand(line))
		{
			portalSign.setValidSign(true);

			if (portalSign.getDestination() == null)
			{
				String newLine = null;

				if (line.startsWith("["))
				{
					newLine = line.substring(line.indexOf("]") + 1);
				}
				else
				{
					newLine = line.substring(0, line.indexOf("["));
				}

				newLine = newLine.replaceAll(" ", "");

				if (newLine.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
				{
					portalSign.setDestination(newLine);
				}
			}
		}

		// Destination
		if (portalSign.getDestination() == null && line.matches(TravelGatesConstants.DESTINATION_NAME_PATTERN))
		{
			portalSign.setDestination(line);
		}

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getPortalSign : " + portalSign);
		}

		return portalSign;
	}

	private static TravelGatesPortalSign getPortalSign(final TravelGatesPortalSign portalSign,
			final TravelGates plugin, final String line, final int lineIndex)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getPortalSign(portalSign=" + portalSign + ", plugin=TravelGates, line="
					+ line + ", lineIndex=" + lineIndex + ")");
		}

		final String onState = plugin.getPortalSignOnState();
		final String offState = plugin.getPortalSignOffState();

		TravelGatesPortalSign localPortalSign = portalSign.clone();

		localPortalSign = getPortalSign(localPortalSign, line);

		// Filled Line
		if (localPortalSign.getFilledLine() < 0 && (line.contains(offState) || line.contains(onState)))
		{
			localPortalSign.setFilledLine(lineIndex);
		}

		// Empty Line
		if (localPortalSign.getEmptyLine() < 0 && TravelGatesUtils.stringIsBlank(line))
		{
			localPortalSign.setEmptyLine(lineIndex);
		}

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getPortalSign : " + localPortalSign);
		}

		return localPortalSign;
	}

	public static Location getDestinationLocation(final World world, final String[] positionData,
			final Location playerLoc, final int offset) throws IllegalArgumentException
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getDestinationLocation(world=" + world + ", positionData=" + positionData
					+ ", playerLoc=" + playerLoc + ", offset=" + offset + ")");
		}

		Location destinationLocation = null;

		if (world != null)
		{
			Double posX = Double.parseDouble(positionData[0 + offset]);
			Double posZ = Double.parseDouble(positionData[2 + offset]);
			Double posY = null;

			final String strPosY = positionData[1 + offset];

			if (TravelGatesConstants.UNKNOWN_HEIGHT.equals(strPosY))
			{
				final int maxHeight = getHeighestFreeBlockAt(posX.intValue(), posZ.intValue(), world);
				posY = new Double(maxHeight);
			}
			else
			{
				posY = Double.parseDouble(strPosY) - 1;
			}

			if (posX.doubleValue() > TravelGatesConstants.MIN_COORDINATE
					&& posX.doubleValue() < TravelGatesConstants.MAX_COORDINATE
					&& posZ.doubleValue() > TravelGatesConstants.MIN_COORDINATE
					&& posZ.doubleValue() < TravelGatesConstants.MAX_COORDINATE && posY.doubleValue() > 0
					&& posY.doubleValue() < world.getMaxHeight())
			{

				destinationLocation = new Location(world, posX.doubleValue(), posY.doubleValue(), posZ.doubleValue(),
						playerLoc.getYaw(), 0f);
			}
			else
			{
				throw new IllegalArgumentException("Input values of position are out of bounds.");
			}
		}
		else
		{
			throw new IllegalArgumentException("Input world does not exist.");
		}

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getDestinationLocation : " + destinationLocation);
		}

		return destinationLocation;
	}

	public static int getHeighestFreeBlockAt(final int posX, final int posZ, final World world)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getHeighestFreeBlockAt(x=" + posX + ", z=" + posZ + ", world=" + world + ")");
		}

		final int maxHeight = world.getMaxHeight();

		int searchedHeight = maxHeight - 1;

		Block lastBlock = null;

		while (searchedHeight > 0)
		{
			final Block block = world.getBlockAt(posX, searchedHeight, posZ);

			if (lastBlock != null && lastBlock.getType() == Material.AIR && block.getType() != Material.AIR)
			{
				break;
			}

			lastBlock = block;
			searchedHeight--;
		}

		searchedHeight++;

		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getDestinationLocation : " + searchedHeight);
		}

		return searchedHeight;
	}

	public static Location getDestinationLocationNearPortal(final TravelGates plugin, final Location playerLoc, final Block portalBlock)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getDestinationLocationNearPortal(plugin=" + plugin + ", playerLoc=" + playerLoc
					+ ",portalBlock=" + portalBlock + ")");
		}

		Location destLocation = null;
		String str = plugin.getDestination(playerLoc);

		if (str != null)
		{
			// Player on dest
			destLocation = playerLoc;
		}
		else
		{
			final Location portalBlockLocation = portalBlock.getLocation();
			str = plugin.getDestination(portalBlockLocation);
			if (str != null)
			{
				// Portal block on dest
				destLocation = portalBlock.getLocation(portalBlockLocation);
			}
			else
			{
				// Search nearest blocks
				for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
				{
					final Location relLoc = portalBlock.getRelative(tgFace.face()).getLocation();
					if (plugin.getDestination(relLoc) != null)
					{
						destLocation = relLoc;
						break;
					}
				}
			}
		}
		
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getDestinationLocationNearPortal : " + destLocation);
		}

		return destLocation;
	}

	public static Sign getSignOnPortalFrames(final Block portalBlock, final World world)
	{
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " Start getSignOnPortalFrames(portalBlock=" + portalBlock + ", world=" + world + ")");
		}

		Sign portalSign = null;
		Block portalFrame = null;

		// Search portal frame
		for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
		{
			final Block adjacentBlock = portalBlock.getRelative(tgFace.face());

			if (adjacentBlock.getType() == Material.OBSIDIAN)
			{
				portalFrame = adjacentBlock;
				break;
			}
		}

		// Search sign on this frame
		if (portalFrame != null)
		{
			final Block frameUpperBlock = portalFrame.getRelative(BlockFace.UP);
			BlockFace portalFace = null;

			for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
			{
				if (tgFace.isSimple())
				{
					final BlockFace currFace = tgFace.face();

					final Block adjacentFrameBlock = frameUpperBlock.getRelative(currFace);

					if (portalSign == null && isSign(adjacentFrameBlock))
					{
						portalSign = (Sign) adjacentFrameBlock.getState();
					}

					if (portalFace == null && adjacentFrameBlock.getType() == Material.PORTAL)
					{
						portalFace = currFace;
					}

					if (portalSign != null && portalFace != null)
					{
						break;
					}
				}
			}

			// Search sign on the opposite frame
			if (portalSign == null && portalFace != null)
			{
				final Block oppositeFrameUpperBlock = frameUpperBlock.getRelative(portalFace, 3);

				for (TravelGatesBlockFaces tgFace : TravelGatesBlockFaces.values())
				{
					if (tgFace.isSimple())
					{
						final BlockFace currFace = tgFace.face();

						final Block adjacentFrameBlock = oppositeFrameUpperBlock.getRelative(currFace);

						if (portalSign == null && isSign(adjacentFrameBlock))
						{
							portalSign = (Sign) adjacentFrameBlock.getState();
							break;
						}
					}
				}
			}
		}
		
		if (_isDebugEnabled)
		{
			_LOGGER.info(_debug + " End getSignOnPortalFrames : " + portalSign);
		}

		return portalSign;
	}

	public static int getCoordinateDiff(final int coordA, final int coordB)
	{
		final int max = Math.max(coordA, coordB);
		final int min = Math.min(coordA, coordB);

		return (max - min);
	}
}
