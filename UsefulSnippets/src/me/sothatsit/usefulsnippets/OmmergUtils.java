package me.sothatsit.usefulsnippets;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class OmmergUtils
{
	public static String rainbowChatColor(String string)
	{
		int lastColor = 0;
		int currColor = 0;
		String newMessage = "";
		String colors = "123456789abcde";
		for ( int i = 0; i < string.length(); i++ )
		{
			do
			{
				currColor = new Random().nextInt(colors.length() - 1) + 1;
			}
			while (currColor == lastColor);
			
			newMessage += ChatColor.RESET.toString() + ChatColor.getByChar(colors.charAt(currColor)) + "" + string.charAt(i);
			
		}
		return newMessage;
	}
	
	public static String getMidMessage(String s)
	{
		int le = ( 62 - s.length() ) / 2;
		String newS = "";
		for ( int i = 0; i < le; i++ )
		{
			newS += ChatColor.GOLD + "*";
		}
		newS += s;
		for ( int i = 0; i < le; i++ )
		{
			newS += ChatColor.GOLD + "*";
		}
		return newS;
	}
	
	public static ItemStack coloredArmor(Material m, Color color, String name)
	{
		ItemStack leatherArmor = new ItemStack(m);
		LeatherArmorMeta meta = (LeatherArmorMeta) leatherArmor.getItemMeta();
		meta.setColor(color);
		if ( name != null )
		{
			meta.setDisplayName(name);
		}
		leatherArmor.setItemMeta(meta);
		return leatherArmor;
	}
	
	public static ItemStack skullFromString(String s, String name)
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(s);
		if ( name != null )
		{
			meta.setDisplayName(name);
		}
		skull.setItemMeta(meta);
		return skull;
	}
}
