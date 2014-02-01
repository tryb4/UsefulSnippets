package me.sothatsit.usefulsnippets.serializable;

import java.io.Serializable;

import org.bukkit.enchantments.Enchantment;

public class EEnchantment implements Serializable
{
	private static final long serialVersionUID = 8973856768102665381L;
	
	private final int id;
	
	@SuppressWarnings("deprecation")
	public EEnchantment( Enchantment enchantment )
	{
		this.id = enchantment.getId();
	}
	
	@SuppressWarnings("deprecation")
	public Enchantment unbox()
	{
		return Enchantment.getById(this.id);
	}
}