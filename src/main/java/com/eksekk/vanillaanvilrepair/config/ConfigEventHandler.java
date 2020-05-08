package com.eksekk.vanillaanvilrepair.config;

import com.eksekk.vanillaanvilrepair.util.Reference;
import com.eksekk.vanillaanvilrepair.repairlist.RepairList;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.PostConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ConfigEventHandler
{
	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(Reference.MOD_ID))
		{
			ConfigManager.load(Reference.MOD_ID, Config.Type.INSTANCE);
			RepairList.processConfigRepairEntries();
		}
	}
}
AttributeModifier