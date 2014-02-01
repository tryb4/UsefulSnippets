package me.sothatsit.usefulsnippets.serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EInventory implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private int inventorySize;
	private EItem helmet = null;
	private EItem chestplate = null;
	private EItem leggings = null;
	private EItem boots = null;
	private Map< Integer , EItem > items = new HashMap< Integer , EItem >();
	
	public EInventory( Inventory inventory )
	{
		inventorySize = inventory.getSize();
		
		for ( int i = 0; i < inventory.getContents().length; i++ )
		{
			ItemStack item = inventory.getContents()[i];
			if ( item != null )
				items.put(i , new EItem(item));
		}
		
		if ( inventory instanceof PlayerInventory )
		{
			PlayerInventory playerInv = (PlayerInventory) inventory;
			helmet = playerInv.getHelmet() == null ? null : new EItem(playerInv.getHelmet());
			chestplate = playerInv.getChestplate() == null ? null : new EItem(playerInv.getChestplate());
			leggings = playerInv.getLeggings() == null ? null : new EItem(playerInv.getLeggings());
			boots = playerInv.getBoots() == null ? null : new EItem(playerInv.getBoots());
		}
	}
	
	public Inventory unbox()
	{
		Inventory inventory = Bukkit.createInventory(null , inventorySize);
		for ( int i = 0; i < items.size(); i++ )
		{
			Integer slot = items.keySet().toArray(new Integer[0])[i];
			EItem item = items.get(slot);
			inventory.setItem(slot , item.unbox());
		}
		return inventory;
	}
	
	public void setPlayerInventory(Player player)
	{
		Inventory inv = unbox();
		player.getInventory().setContents(inv.getContents());
		if ( helmet != null )
			player.getInventory().setHelmet(getHelmet());
		if ( chestplate != null )
			player.getInventory().setChestplate(getChestplate());
		if ( leggings != null )
			player.getInventory().setLeggings(getLeggings());
		if ( boots != null )
			player.getInventory().setBoots(getBoots());
	}
	
	public ItemStack getHelmet()
	{
		return ( helmet == null ? null : helmet.unbox() );
	}
	
	public ItemStack getChestplate()
	{
		return ( chestplate == null ? null : chestplate.unbox() );
	}
	
	public ItemStack getLeggings()
	{
		return ( leggings == null ? null : leggings.unbox() );
	}
	
	public ItemStack getBoots()
	{
		return ( boots == null ? null : boots.unbox() );
	}
	
}
