package com.bafomdad.zenscape.worldgen;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldGenDecorators {

	@SubscribeEvent
	public void decorateCaveTrap(DecorateBiomeEvent.Decorate event) {
		
		if ((event.getResult() == Result.ALLOW || event.getResult() == Result.DEFAULT) && event.rand.nextInt(90) == 0) {
			
			int x = event.chunkX + event.rand.nextInt(16) + 8;
			int z = event.chunkZ + event.rand.nextInt(16) + 8;
			int y = event.rand.nextInt(26) + 4;
			
			if (event.world.isAirBlock(x, y, z) && event.world.getBlock(x, y - 1, z) == Blocks.stone) 
			{
				event.world.setBlock(x, y - 1, z, ZenScape.blockCaveTrap);
//				System.out.println("Trap generated at: " + x + ", " + y + ", " + z);
			}
		}
	}
	
	@SubscribeEvent
	public void decorateFireLily(DecorateBiomeEvent.Decorate event) {
		
		if ((event.getResult() == Result.ALLOW || event.getResult() == Result.DEFAULT) && event.world.provider.isHellWorld) {
			if (event.rand.nextInt(30) == 0)
			{
				int x = event.chunkX + event.rand.nextInt(16) + 8;
				int z = event.chunkZ + event.rand.nextInt(16) + 8;
				int y = 32;
				
				if (event.world.isAirBlock(x, y, z) && event.world.getBlock(x, y - 1, z).getMaterial() == Material.lava)
				{
					event.world.setBlock(x, y, z, ZenScape.blockZenLily, 0, 2);
					for (int i = -3; i <= 3; i++) {
						for (int j = -3; j <= 3; j++)
						{
							if (event.world.isAirBlock(x + i, y, z + j) && event.world.getBlock(x + i, y - 1, z + j).getMaterial() == Material.lava)
								if (event.world.rand.nextInt(5) == 0)
									event.world.setBlock(x + i, y, z + j, ZenScape.blockZenLily, 0, 2);
						}
					}
				}
			}
		}
	}
}
