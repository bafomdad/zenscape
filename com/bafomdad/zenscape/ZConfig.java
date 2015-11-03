package com.bafomdad.zenscape;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ZConfig {
	
	public static boolean blockSkybeam;
	public static boolean extraCreativeTab;

	public static void loadConfig(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		// Blocks
		blockSkybeam = config.get("blocks", "Rainbow-y block from NMcCoy's LegendGear mod, circa version 1.5", true).getBoolean(false);
		
		// Debug
		extraCreativeTab = config.get("Debugging", "Creative tab for debugging purposes", false).getBoolean(false);
	
		config.save();
	}
}
