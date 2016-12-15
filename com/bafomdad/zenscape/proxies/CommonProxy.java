package com.bafomdad.zenscape.proxies;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.bafomdad.zenscape.crafting.ZCrafting;

public class CommonProxy {
	
	public static List<Block> whitelist = new ArrayList();
	
	public void init() {
		
		addBlockWhitelist();
	}
	
	public void registerRenderers() {
		
	}
	
	private void addBlockWhitelist() {
		
		whitelist.add(Blocks.chest);
		whitelist.add(Blocks.trapped_chest);
		whitelist.add(Blocks.fence);
		whitelist.add(Blocks.nether_brick_fence);
	}
}
