package me.sothatsit.usefulsnippets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Selection implements Iterable< Block >
{
	private Location max;
	private Location min;
	
	private List< BlockInfo > blockinfo;
	
	public Selection( Location min , Location max )
	{
		this.max = new Location(min.getWorld(), Math.max(min.getX() , max.getX()), Math.max(min.getY() , max.getY()), Math.max(min.getZ() , max
				.getZ()));
		
		this.min = new Location(min.getWorld(), Math.min(min.getX() , max.getX()), Math.min(min.getY() , max.getY()), Math.min(min.getZ() , max
				.getZ()));
		
		this.blockinfo = new ArrayList< BlockInfo >();
		
		for ( Block b : this )
		{
			this.blockinfo.add(new BlockInfo(b));
		}
	}
	
	public Location getMin()
	{
		return min;
	}
	
	public Location getMax()
	{
		return max;
	}
	
	public boolean containsPoint(Location loc)
	{
		return loc.getX() >= min.getX() && loc.getX() <= max.getX() && loc.getY() >= min.getY() && loc.getY() <= max.getY() && loc.getZ() >= min.getZ()
				&& loc.getZ() <= max.getZ();
	}
	
	public Location[] getCorners()
	{
		World w = max.getWorld();
		double maxx = max.getX();
		double maxy = max.getY();
		double maxz = max.getZ();
		
		double minx = min.getX();
		double miny = min.getY();
		double minz = min.getZ();
		
		return new Location[]
		{ max , min , new Location(w, maxx, maxy, minz) , new Location(w, maxx, miny, maxz) , new Location(w, minx, maxy, maxz) ,
				new Location(w, minx, maxy, minz) , new Location(w, minx, miny, maxz) , new Location(w, maxx, miny, minz) };
	}
	
	public boolean containsSelection(Selection selection)
	{
		for ( Location loc : selection.getCorners() )
		{
			if ( !containsPoint(loc) )
				return false;
		}
		return true;
	}
	
	public boolean collidesSelection(Selection selection)
	{
		for ( Location loc : selection.getCorners() )
		{
			if ( containsPoint(loc) )
				return true;
		}
		for ( Location loc : getCorners() )
		{
			if ( selection.containsPoint(loc) )
				return true;
		}
		return false;
	}
	
	public double getWidth()
	{
		return max.getX() + 1 - min.getX();
	}
	
	public double getHeight()
	{
		return max.getY() + 1 - min.getY();
	}
	
	public double getDepth()
	{
		return max.getZ() + 1 - min.getZ();
	}
	
	public double getVolume()
	{
		return getWidth() * getHeight() * getDepth();
	}
	
	@SuppressWarnings("deprecation")
	public int setBlocks(Material type, byte data)
	{
		int affected = 0;
		for ( Block b : this )
		{
			if ( b.getType() != type || b.getData() != data )
			{
				b.setType(type);
				b.setData(data);
			}
		}
		return affected;
	}
	
	public List< BlockInfo > getSavedBlocks()
	{
		return blockinfo;
	}
	
	public void saveBlock(Block b)
	{
		blockinfo.add(new BlockInfo(b));
	}
	
	public void saveBlocks()
	{
		for ( Block b : this )
		{
			saveBlock(b);
		}
	}
	
	public int restoreBlocks()
	{
		int affected = 0;
		for ( BlockInfo b : blockinfo )
		{
			if ( b.restore() )
				affected++;
		}
		return affected;
	}
	
	public List< BlockInfo > getBlockInfo()
	{
		return blockinfo;
	}
	
	public BlockInfo getBlockInfo(Block b)
	{
		for ( BlockInfo binfo : blockinfo )
		{
			Location loc = binfo.getLocation();
			Location l = b.getLocation();
			if ( l.getX() == loc.getX() && l.getY() == loc.getY() && l.getZ() == loc.getZ() )
				return binfo;
		}
		return null;
	}
	
	public List< Block > getBlocks()
	{
		List< Block > blocks = new ArrayList< Block >();
		
		World w = max.getWorld();
		double x = min.getX();
		double y = min.getY();
		double z = min.getZ();
		
		double ex = max.getX() + 1;
		double ey = max.getY() + 1;
		double ez = max.getZ() + 1;
		
		while (x < ex)
		{
			while (y < ey)
			{
				while (z < ez)
				{
					blocks.add(new Location(w, x, y, z).getBlock());
					z++;
				}
				y++;
				z = min.getZ();
			}
			x++;
			y = min.getY();
		}
		return blocks;
	}
	
	@Override
	public java.util.Iterator< Block > iterator()
	{
		return new BlockIterator(getBlocks());
	}
	
	private class BlockIterator implements java.util.Iterator< Block >
	{
		private int i = 0;
		private List< Block > blocks;
		
		public BlockIterator( List< Block > blocks )
		{
			this.blocks = blocks;
		}
		
		public boolean hasNext()
		{
			return i < blocks.size();
		}
		
		public Block next()
		{
			Block b = blocks.get(i);
			i++;
			
			return b;
		}
		
		public void remove()
		{
			blocks.remove(i);
			i--;
		}
	}
	
	public class BlockInfo
	{
		private Location loc;
		private Material type;
		private byte data;
		
		@SuppressWarnings("deprecation")
		public BlockInfo( Block b )
		{
			loc = b.getLocation();
			type = b.getType();
			data = b.getData();
		}
		
		public Location getLocation()
		{
			return loc;
		}
		
		public Material getType()
		{
			return type;
		}
		
		public byte getData()
		{
			return data;
		}
		
		@SuppressWarnings("deprecation")
		public boolean restore()
		{
			Block b = loc.getBlock();
			if ( b.getType() != type || b.getData() != data )
			{
				b.setType(type);
				b.setData(data);
				return true;
			}
			return false;
		}
	}
}
