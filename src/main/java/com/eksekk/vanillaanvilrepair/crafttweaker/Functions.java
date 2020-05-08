package com.eksekk.vanillaanvilrepair.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

import com.eksekk.vanillaanvilrepair.repairlist.RepairList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

@ZenRegister
@ZenClass("mods.vanillaanvilrepair")
public class Functions
{
	@ZenMethod
	public static void addRepairEntry(IIngredient repairedItems, IIngredient materials)
	{
		for (IItemStack repairedItem: repairedItems.getItems())
		{
			for (IItemStack material: materials.getItems())
			{
				RepairList.addRepairEntryCT(((ItemStack)repairedItem.getInternal()).getItem(), (((ItemStack)material.getInternal()).getItem()));
			}
		}
	}
}
