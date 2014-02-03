package me.sothatsit.usefulsnippets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoRespawn implements Listener
{
	
	private final Plugin plugin;
	
	public AutoRespawn( Plugin plugin )
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this , plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent e)
	{
		new BukkitRunnable()
		{
			public void run()
			{
				try
				{
					Object nmsPlayer = e.getEntity().getClass().getMethod("getHandle").invoke(e.getEntity());
					Object con = nmsPlayer.getClass().getDeclaredField("playerConnection").get(nmsPlayer);
					
					Class< ? > EntityPlayer = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EntityPlayer");
					
					Field minecraftServer = con.getClass().getDeclaredField("minecraftServer");
					minecraftServer.setAccessible(true);
					Object mcserver = minecraftServer.get(con);
					
					Object playerlist = mcserver.getClass().getDeclaredMethod("getPlayerList").invoke(mcserver);
					Method moveToWorld = playerlist.getClass().getMethod("moveToWorld" , EntityPlayer , int.class , boolean.class);
					moveToWorld.invoke(playerlist , nmsPlayer , 0 , false);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}.runTaskLater(plugin , 2);
	}
	
}
