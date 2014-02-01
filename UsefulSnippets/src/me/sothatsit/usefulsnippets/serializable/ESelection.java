package me.sothatsit.usefulsnippets.serializable;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;

public class ESelection implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private ELocation min;
	private ELocation max;
	
	public ESelection( Location p1 , Location p2 )
	{
		if ( p1 == null || p2 == null )
			throw new IllegalArgumentException("Locations cannot be null");
		if ( !p1.getWorld().getName().equals(p2.getWorld().getName()) )
			throw new IllegalArgumentException("Locations must be in the same world");
		
		World world = p1.getWorld();
		
		int minx = Math.min(p1.getBlockX() , p2.getBlockX());
		int miny = Math.min(p1.getBlockY() , p2.getBlockY());
		int minz = Math.min(p1.getBlockZ() , p2.getBlockZ());
		Location min = new Location(world, minx, miny, minz);
		this.min = new ELocation(min);
		
		int maxx = Math.max(p1.getBlockX() , p2.getBlockX());
		int maxy = Math.max(p1.getBlockY() , p2.getBlockY());
		int maxz = Math.max(p1.getBlockZ() , p2.getBlockZ());
		Location max = new Location(world, maxx, maxy, maxz);
		this.max = new ELocation(max);
	}
	
	public void expandVert(int skylimit)
	{
		this.min.setY(0);
		this.max.setY(skylimit);
	}
	
	public Location newLoc(double x, double y, double z)
	{
		return new Location(min.getWorld(), x, y, z);
	}
	
	public ELocation getMin()
	{
		return min;
	}
	
	public ELocation getMax()
	{
		return max;
	}
	
	public Location[] getCorners()
	{
		double[] a = new double[]
		{ min.getX() , min.getY() , min.getZ() };
		double[] b = new double[]
		{ max.getX() , max.getY() , max.getZ() };
		return new Location[]
		{ newLoc(a[0] , a[1] , a[2]) , newLoc(b[0] , a[1] , a[2]) , newLoc(a[0] , b[1] , a[2]) , newLoc(a[0] , a[1] , b[2]) ,
				newLoc(b[0] , b[1] , a[2]) , newLoc(b[0] , a[1] , b[2]) , newLoc(b[0] , b[1] , b[2]) , newLoc(a[0] , b[1] , b[2]) };
	}
	
	public boolean inBounds(Location location)
	{
		double[] a = new double[]
		{ location.getBlockX() , location.getBlockY() , location.getBlockZ() };
		double[] b = new double[]
		{ min.getX() , min.getY() , min.getZ() };
		double[] c = new double[]
		{ max.getX() , max.getY() , max.getZ() };
		
		return a[0] >= b[0] && a[0] <= c[0] && a[1] >= b[1] && a[1] <= c[1] && a[2] >= b[2] && a[2] <= c[2];
	}
	
	public boolean inBounds(ESelection selection)
	{
		for ( Location loc : getCorners() )
		{
			if ( !inBounds(loc) )
				return false;
		}
		return true;
	}
	
	public boolean colliding(ESelection selection)
	{
		for ( Location loc : getCorners() )
		{
			if ( selection.inBounds(loc) )
				return true;
		}
		for ( Location loc : selection.getCorners() )
		{
			if ( inBounds(loc) )
				return true;
		}
		return false;
	}
}
