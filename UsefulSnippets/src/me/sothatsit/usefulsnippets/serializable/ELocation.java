package me.sothatsit.usefulsnippets.serializable;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ELocation implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;
	private double z;
	private String world;
	
	private float pitch;
	private float yaw;
	
	public ELocation(Location loc)
	{
		this.x=loc.getX();
		this.y=loc.getY();
		this.z=loc.getZ();
		this.world=loc.getWorld().getName();
		this.pitch=loc.getPitch();
		this.yaw=loc.getYaw();
	}
	
	public void setX(double x)
	{
		this.x=x;
	}
	
	public void setY(double y)
	{
		this.y=y;
	}
	
	public void setZ(double z)
	{
		this.z=z;
	}
	
	public void setWorld(String world)
	{
		this.world=world;
	}
	
	public void setPitch(float pitch)
	{
		this.pitch=pitch;
	}
	
	public void setYaw(float yaw)
	{
		this.yaw=yaw;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public int getBlockX()
	{
		return (int) Math.floor(x);
	}
	
	public int getBlockY()
	{
		return (int) Math.floor(y);
	}
	
	public int getBlockZ()
	{
		return (int) Math.floor(z);
	}
	
	public float getPitch()
	{
		return pitch;
	}
	
	public float getYaw()
	{
		return yaw;
	}
	
	public String getWorldName()
	{
		return world;
	}
	
	public World getWorld()
	{
		return Bukkit.getWorld(world);
	}
	
	public Location getLocation()
	{
		Location loc = new Location(getWorld(), x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		return loc;
	}
}
