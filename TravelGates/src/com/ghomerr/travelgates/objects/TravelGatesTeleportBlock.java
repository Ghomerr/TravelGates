package com.ghomerr.travelgates.objects;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.material.Tree;
import org.bukkit.material.Wool;

import com.ghomerr.travelgates.constants.TravelGatesConstants;

public class TravelGatesTeleportBlock
{
	private Material _tpBlockType = null;
	private DyeColor _tpBlockColor = null;
	private TreeSpecies _tpBlockSpecies = null;

	private boolean _enabled = false;
	
	public TravelGatesTeleportBlock()
	{
		super();
	}
	
	public TravelGatesTeleportBlock(final Material type)
	{
		this();
		_tpBlockType = type;
	}
	
	public TravelGatesTeleportBlock(final Material type, final DyeColor color, final TreeSpecies species)
	{
		this(type);
		_tpBlockColor = color;
		_tpBlockSpecies = species;
	}
	
	public Material type()
	{
		return _tpBlockType;
	}
	public void setType(final Material type)
	{
		_tpBlockType = type;
	}
	
	public DyeColor color()
	{
		return _tpBlockColor;
	}
	public void setColor(final DyeColor color)
	{
		_tpBlockColor = color;
	}
	
	public TreeSpecies species()
	{
		return _tpBlockSpecies;
	}
	public void setSpecies(final TreeSpecies species)
	{
		_tpBlockSpecies = species;
	}
	
	public boolean isTPBlock(final Block block)
	{
		final Material blockType = block.getType();
		boolean isTpBlock = blockType == _tpBlockType;
		
		if (isTpBlock)
		{
			final byte data = block.getData();
			
			switch(blockType)
			{
				case WOOL:
					final Wool woolBlock = new Wool(blockType, data);
					isTpBlock = woolBlock.getColor() == _tpBlockColor;
					break;
					
				case LOG:
					final Tree treeBlock = new Tree(blockType, data);
					isTpBlock = treeBlock.getSpecies() == _tpBlockSpecies;
					break;
					
				default:
					break;
			}
		}
		
		return isTpBlock;
	}
	
	@Override
	public String toString()
	{
		String str = "";
		
		if (_tpBlockType == null || !_enabled)
		{
			str += "disabled";
		}
		else
		{
			str += _tpBlockType.name().toLowerCase();
			
			switch(_tpBlockType)
			{
				case WOOL:
					str += TravelGatesConstants.DELIMITER + _tpBlockColor.name().toLowerCase();
					break;
					
				case LOG:
					str += TravelGatesConstants.DELIMITER + _tpBlockSpecies.name().toLowerCase();
					break;
				
				default:
					break;
			}
		}
		
		return str;
	}
	
	public void setEnabled(final boolean state)
	{
		_enabled = state;
	}
	public boolean toggleState()
	{
		_enabled = !_enabled;
		return _enabled;
	}
	public boolean isEnabled()
	{
		return _enabled;
	}
}
