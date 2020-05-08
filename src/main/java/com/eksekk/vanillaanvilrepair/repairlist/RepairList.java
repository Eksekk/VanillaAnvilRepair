package com.eksekk.vanillaanvilrepair.repairlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eksekk.vanillaanvilrepair.config.ConfigFields;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreIngredient;

public class RepairList
{
	private static Map<Item, ArrayList<Item>> repairListCT = new HashMap<Item, ArrayList<Item>>();
	private static Map<Item, ArrayList<Item>> repairList = new HashMap<Item, ArrayList<Item>>();
	
	public static Map<Item, ArrayList<Item>> getRepairList()
	{
		return repairList;
	}
	
	public static void addRepairEntryCT(Item repairedItem, Item material)
	{
		repairListCT.putIfAbsent(material, new ArrayList<Item>());
		repairListCT.get(material).add(repairedItem);
	}
	
	private static void addRepairEntry(Item repairedItem, Item material)
	{
		repairList.putIfAbsent(material, new ArrayList<Item>());
		repairList.get(material).add(repairedItem);
	}
	
	public static void processConfigRepairEntries()
	{
		repairList.clear();
		for (Map.Entry<Item, ArrayList<Item>> entry: repairListCT.entrySet())
		{
			//System.out.println("Adding entry <" + new ItemStack(entry.getKey(), 1).getDisplayName() + ";" + new ItemStack(entry.getValue().get(0), 1).getDisplayName() + ">");
			repairList.putIfAbsent(entry.getKey(), new ArrayList<Item>());
			repairList.get(entry.getKey()).addAll(entry.getValue());
		}
		String[] repairEntries = ConfigFields.repairList;
		
		for (String repair: repairEntries)
		{
			String[] splittedItemsMaterials = repair.split(";");
			if (splittedItemsMaterials.length != 2)
			{
				System.err.println("Config repair entry \"" + repair + "\" is invalid, skipping");
				continue;
			}
			
			String[] splittedItems = splittedItemsMaterials[0].split(",");
			String[] splittedMaterials = splittedItemsMaterials[1].split(",");
			
			ArrayList<Item> repairedItems = new ArrayList(splittedItems.length);
			ArrayList<Item> materials = new ArrayList(splittedMaterials.length);
			for (String itemString: splittedItems)
			{
				Item repairedItem = Item.getByNameOrId(itemString);
				if (repairedItem == null)
				{
					System.err.println("Config repair item entry \"" + itemString + "\" is invalid - repaired item is null, skipping this item");
					continue;
				}
				repairedItems.add(repairedItem);
			}
			
			for (String materialString: splittedMaterials)
			{
				if (materialString.startsWith("ore"))
				{
					OreIngredient oreEntry = new OreIngredient(materialString.substring(4));
					ItemStack[] oreMaterials = oreEntry.getMatchingStacks();
					if (oreMaterials.length == 0)
					{
						System.err.println("Empty OreDictionary entry " + materialString.substring(4));
					}
					for (ItemStack material: oreMaterials)
					{
						materials.add(material.getItem());
					}
				}
				else
				{
					Item material = Item.getByNameOrId(materialString);
					if (material == null)
					{
						System.err.println("Config repair material entry \"" + materialString + "\" is invalid - material is null, skipping this material");
						continue;
					}
					//System.out.println(new ItemStack(repairedItem, 1).getDisplayName() + ";" + new ItemStack(material, 1).getDisplayName());
					
					materials.add(material);
				}
			}
			
			for (Item material: materials)
			{
				repairList.putIfAbsent(material, new ArrayList<Item>());
				repairList.get(material).addAll(repairedItems);
			}
		}
	}
}
