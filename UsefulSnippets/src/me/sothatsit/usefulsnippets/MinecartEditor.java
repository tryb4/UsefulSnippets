package me.sothatsit.usefulsnippets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Minecart;

public class MinecartEditor
{
	
	@SuppressWarnings("deprecation")
	public static Material getBlockType(Minecart cart)
	{
		return Material.getMaterial(getBlockId(cart));
	}
	
	public static int getBlockY(Minecart cart)
	{
		Object handle = ReflectionUtils.getHandle(cart);
		Method getDataWatcher = ReflectionUtils.getMethod(handle.getClass() ,
				"getDataWatcher");
		try
		{
			Object dataWatcher = getDataWatcher.invoke(handle);
			Method getInt = ReflectionUtils.getMethod(dataWatcher.getClass() ,
					"getInt");
			return (Integer) getInt.invoke(dataWatcher , 21) & '\uffff';
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static int getBlockId(Minecart cart)
	{
		Object handle = ReflectionUtils.getHandle(cart);
		Method getDataWatcher = ReflectionUtils.getMethod(handle.getClass() ,
				"getDataWatcher");
		try
		{
			Object dataWatcher = getDataWatcher.invoke(handle);
			Map c = getDataMap(dataWatcher);
			if ( c.containsKey(Integer.valueOf(20)) )
			{
				Method getInt = ReflectionUtils.getMethod(
						dataWatcher.getClass() , "getInt");
				return (Integer) getInt.invoke(dataWatcher , 20) & '\uffff';
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Integer getBlockData(Minecart cart)
	{
		Object handle = ReflectionUtils.getHandle(cart);
		Method o = ReflectionUtils.getMethod(handle.getClass() , "o");
		try
		{
			return (Integer) o.invoke(handle);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setBlockY(Minecart cart, Integer y)
	{
		Object handle = ReflectionUtils.getHandle(cart);
		Method getDataWatcher = ReflectionUtils.getMethod(handle.getClass() ,
				"getDataWatcher");
		try
		{
			Object dataWatcher = getDataWatcher.invoke(handle);
			Map c = getDataMap(dataWatcher);
			if ( c.containsKey(Integer.valueOf(21)) )
				c.remove(Integer.valueOf(21));
			Method a = ReflectionUtils.getMethod(dataWatcher.getClass() , "a");
			a.invoke(dataWatcher , 21 , y);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setBlock(Minecart cart, Material blockType)
	{
		setBlock(cart , blockType , 0);
	}
	
	public static void setBlock(Minecart cart, int blockId)
	{
		setBlock(cart , blockId , 0);
	}
	
	@SuppressWarnings("deprecation")
	public static void setBlock(Minecart cart, Material blockType, int blockData)
	{
		setBlock(cart , blockType == null ? 0 : blockType.getId() , blockData);
	}
	
	@SuppressWarnings("rawtypes")
	public static void setBlock(Minecart cart, Integer blockId,
			Integer blockData)
	{
		Object handle = ReflectionUtils.getHandle(cart);
		Method getDataWatcher = ReflectionUtils.getMethod(handle.getClass() ,
				"getDataWatcher");
		try
		{
			Object dataWatcher = getDataWatcher.invoke(handle);
			Method a = ReflectionUtils.getMethod(dataWatcher.getClass() , "a");
			Map c = getDataMap(dataWatcher);
			if ( c.containsKey(Integer.valueOf(20)) )
				c.remove(Integer.valueOf(20));
			int value = blockId & 0xFFFF | blockData << 16;
			a.invoke(dataWatcher , 20 , value);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getDataMap(Object DataWatcher)
	{
		Map map = null;
		
		try
		{
			Field f = DataWatcher.getClass().getDeclaredField("c");
			f.setAccessible(true);
			map = (Map) f.get(DataWatcher);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return map;
	}
	
}
