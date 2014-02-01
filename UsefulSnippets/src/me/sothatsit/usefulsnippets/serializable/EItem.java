package me.sothatsit.usefulsnippets.serializable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EItem implements Serializable
{
	private static final long serialVersionUID = 729890133797629668L;
	
	private final int type , amount;
	private final short damage;
	private final byte data;
	private final String[] lore;
	private final String name;
	
	private final HashMap< EEnchantment , Integer > enchants;
	
	@SuppressWarnings("deprecation")
	public EItem( ItemStack item )
	{
		this.type = item.getTypeId();
		this.amount = item.getAmount();
		this.damage = item.getDurability();
		this.data = item.getData().getData();
		this.name = item.getItemMeta().getDisplayName();
		this.lore = new String[item.getItemMeta().hasLore() ? item.getItemMeta().getLore().size() : 0];
		if ( item.getItemMeta().hasLore() )
		{
			for ( int i = 0; i < item.getItemMeta().getLore().size(); i++ )
			{
				this.lore[i] = item.getItemMeta().getLore().get(i);
			}
		}
		
		HashMap< EEnchantment , Integer > map = new HashMap< EEnchantment , Integer >();
		
		Map< Enchantment , Integer > enchantments = item.getEnchantments();
		
		for ( Enchantment enchantment : enchantments.keySet() )
		{
			map.put(new EEnchantment(enchantment) , enchantments.get(enchantment));
		}
		
		this.enchants = map;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack unbox()
	{
		ItemStack item = new ItemStack(type, amount, damage);
		item.getData().setData(data);
		
		HashMap< Enchantment , Integer > map = new HashMap< Enchantment , Integer >();
		
		for ( EEnchantment cEnchantment : enchants.keySet() )
		{
			map.put(cEnchantment.unbox() , enchants.get(cEnchantment));
		}
		
		item.addUnsafeEnchantments(map);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(name);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
