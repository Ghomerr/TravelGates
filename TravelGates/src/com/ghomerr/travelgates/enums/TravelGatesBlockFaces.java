package com.ghomerr.travelgates.enums;

import org.bukkit.block.BlockFace;

public enum TravelGatesBlockFaces 
{
	NORTH(BlockFace.NORTH, true),
	
	NORTH_EAST(BlockFace.NORTH_EAST, false),
	
	EAST(BlockFace.EAST, true),
	
	SOUTH_EAST(BlockFace.SOUTH_EAST, false),
	
	SOUTH(BlockFace.SOUTH, true),
	
	SOUTH_WEST(BlockFace.SOUTH_WEST, false),
	
	WEST(BlockFace.WEST, true),
	
	NORTH_WEST(BlockFace.NORTH_WEST, false);
	
	private BlockFace _face = null;
	private boolean _simple = false;
	
	TravelGatesBlockFaces(final BlockFace face, final boolean simple)
	{
		_face = face;
		_simple = simple;
	}
	
	public BlockFace face()
	{
		return _face;
	}
	
	public boolean isSimple()
	{
		return _simple;
	}
}
