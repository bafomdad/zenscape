package com.bafomdad.zenscape.util;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockCopyCat.TileCopyCat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CopyCatHandler {
	
	private int range = 4;

	@SubscribeEvent
	public void blockInteract(PlayerInteractEvent event) {
		
		int x = (int)event.entityPlayer.posX;
		int y = (int)event.entityPlayer.posY;
		int z = (int)event.entityPlayer.posZ;
		
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.world.getBlock(event.x, event.y, event.z) != ZenScape.blockCopyCat)
		{
			for (int i = -range; i <= range; i++) {
				for (int j = -range; j <= range; j++) {
					for (int k = 0; k < 4; k++)
					{
						if (event.entityPlayer.worldObj.getBlock(x + i, y + k, z + j) != null && event.entityPlayer.worldObj.getBlock(x + i, y + k, z + j) == ZenScape.blockCopyCat)
						{
							TileCopyCat tile = (TileCopyCat)event.entityPlayer.worldObj.getTileEntity(x + i, y + k, z + j);
							if (tile != null && tile.canLearn) {
								if (tile.blockInteract < 5)
								{
									tile.blockInteract++;
									System.out.println(tile.blockInteract);
								}
								else
									tile.canLearn = false;
							}
						}
					}
				}
			}
		}
	}
}
