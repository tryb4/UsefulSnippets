package me.sothatsit.usefulsnippets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class UPlayer implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Map< String , UPlayer > players;
	private String name;
	
	public UPlayer( Player p )
	{
		this.name = p.getName();
	}
	
	public String getName()
	{
		return name;
	}
	
	public static void load(Plugin p)
	{
		players = null;
		if ( new File(p.getDataFolder(), "players.sav").exists() )
			players = load(new File(p.getDataFolder(), "players.sav"));
		
		if ( players == null )
			players = new HashMap< String , UPlayer >();
	}
	
	public static void save(Plugin p)
	{
		save(players , new File(p.getDataFolder(), "players.sav"));
	}
	
	private static void save(Object map, File f)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(f));
			oos.writeObject(map);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static < T extends Object > T load(File f)
	{
		try
		{
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object result = null;
			if ( fis.available() > 0 )
				result = ois.readObject();
			ois.close();
			return (T) result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static UPlayer getUPlayer(Player player)
	{
		UPlayer p = players.get(player.getName());
		
		if ( p == null )
		{
			p = new UPlayer(player);
			players.put(p.getName() , p);
		}
		
		return p;
	}
}
