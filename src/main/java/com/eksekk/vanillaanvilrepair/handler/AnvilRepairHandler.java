package com.eksekk.vanillaanvilrepair.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eksekk.vanillaanvilrepair.config.ConfigFields;
import com.eksekk.vanillaanvilrepair.repairlist.RepairList;

@EventBusSubscriber
public class AnvilRepairHandler
{
	@SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
	{
		ItemStack inputItem = event.getLeft();
		ItemStack material = event.getRight();
		
		if (inputItem == null || inputItem.isEmpty() || material == null || material.isEmpty())
		{
			return;
		}
		
		/*//if (!Minecraft.getMinecraft().world.isRemote)
		{
			Map<Item, ArrayList<Item>> map = RepairList.getRepairList();
			
			for (Map.Entry<Item, ArrayList<Item>> entry: map.entrySet())
			{
				for (int i = 0; i < entry.getValue().size(); ++i)
				System.out.println("Repairing item " + new ItemStack(entry.getValue().get(0)).getDisplayName() + " with " + new ItemStack(entry.getKey()).getDisplayName());
			}
		}*/
		
		boolean exists = false;
		ArrayList<Item> list = RepairList.getRepairList().get(material.getItem());
		if (list != null && list.contains(inputItem.getItem()))
		{
			exists = true;
		}
		
		if (!exists)
		{
			return;
		}
		
		ItemStack repairedItem = inputItem.copy();
		
		if (!event.getName().isEmpty()) //for a weird bug where repairing items sets name to empty by default
		{
			repairedItem.setStackDisplayName(event.getName());
		}
		event.setCost(repairedItem.getRepairCost());
		
		if (ConfigFields.repairsIncreaseCost)
		{
			repairedItem.setRepairCost(repairedItem.getRepairCost() * 2 + 1);
		}
			
		
		if (inputItem.getItemDamage() > 0.75 * inputItem.getMaxDamage() && material.getCount() >= 4)
		{
			event.setMaterialCost(4);
			repairedItem.setItemDamage(0);
			event.setOutput(repairedItem);
			event.setCost(event.getCost() + 4);
		}
		else if (inputItem.getItemDamage() > 0.50 * inputItem.getMaxDamage() && material.getCount() >= 3)
		{
			event.setMaterialCost(3);
			repairedItem.setItemDamage(Math.max(0, (int)(inputItem.getItemDamage() - 0.75 * inputItem.getMaxDamage())));
			event.setOutput(repairedItem);
			event.setCost(event.getCost() + 3);
		}
		else if (inputItem.getItemDamage() > 0.25 * inputItem.getMaxDamage() && material.getCount() >= 2)
		{
			event.setMaterialCost(2);
			repairedItem.setItemDamage(Math.max(0, (int)(inputItem.getItemDamage() - 0.50 * inputItem.getMaxDamage())));
			event.setOutput(repairedItem);
			event.setCost(event.getCost() + 2);
		}
		else if (inputItem.getItemDamage() > 0)
		{
			event.setMaterialCost(1);
			repairedItem.setItemDamage(Math.max(0, (int)(inputItem.getItemDamage() - 0.25 * inputItem.getMaxDamage())));
			event.setOutput(repairedItem);
			event.setCost(event.getCost() + 1);
		}
    }
}