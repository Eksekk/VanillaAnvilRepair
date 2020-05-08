package com.eksekk.vanillaanvilrepair.config;

import com.eksekk.vanillaanvilrepair.util.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config (modid = Reference.MOD_ID)
public class ConfigFields
{
	@Comment({"List of repairs, in format",
			  "repaireditem1,repaireditem2[...];material1,material2[...]",
			  "(you can use ore:item for OreDictionary entry (only for materials))"})
	public static String[] repairList = {};
	
	@Comment("If true, repairs added by this mod increase further work level cost (same as vanilla)")
	public static boolean repairsIncreaseCost = false;
}
