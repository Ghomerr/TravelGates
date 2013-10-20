package com.ghomerr.travelgates.enums;

import org.bukkit.block.BlockFace;

public enum TravelGatesBlockFaces 
{
	NORTH(BlockFace.NORTH, BlockFace.SOUTH, true),
	
	NORTH_EAST(BlockFace.NORTH_EAST, BlockFace.SOUTH_WEST, false),
	
	EAST(BlockFace.EAST, BlockFace.WEST, true),
	
	SOUTH_EAST(BlockFace.SOUTH_EAST, BlockFace.NORTH_WEST, false),
	
	SOUTH(BlockFace.SOUTH, BlockFace.NORTH, true),
	
	SOUTH_WEST(BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, false),
	
	WEST(BlockFace.WEST, BlockFace.EAST, true),
	
	NORTH_WEST(BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, false);
	
	private BlockFace _face = null;
	private BlockFace _opposite;
	private boolean _simple = false;
	
	TravelGatesBlockFaces(final BlockFace face, final BlockFace opposite, final boolean simple)
	{
		_face = face;
		_opposite = opposite;
		_simple = simple;
	}
	
	public BlockFace face()
	{
		return _face;
	}
	
	public BlockFace opposite()
	{
		return _opposite;
	}
	
	public boolean isSimple()
	{
		return _simple;
	}
}
