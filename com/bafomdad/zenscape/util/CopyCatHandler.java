package com.bafomdad.zenscape.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockCopyCat.TileCopyCat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CopyCatHandler {
	
	private int range = 4;

	@SubscribeEvent
	public void blockInteract(PlayerInteractEvent event) {
		
		if (event.entityPlayer.worldObj.isRemote)
			return;
		
		int x = (int)event.entityPlayer.posX;
		int y = (int)event.entityPlayer.posY;
		int z = (int)event.entityPlayer.posZ;
		
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.world.getBlock(event.x, event.y, event.z) != ZenScape.blockCopyCat && getComparableBlock(event.world, event.x, event.y, event.z))
		{
			TileCopyCat tile = searchForNearbyTile(event.entityPlayer, x, y, z);
			if (tile == null)
				return;
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
	
	private boolean getComparableBlock(World world, int x, int y, int z) {
		
		Block block = null;
		
		if (ZenScape.proxy.whitelist.size() > 0) {
			for (int i = 0; i < ZenScape.proxy.whitelist.size(); i++)
			{
				Block block1 = world.getBlock(x, y, z);
				Block block2 = ZenScape.proxy.whitelist.get(i);
				
				if (block1 == block2)
				{
					block = block1;
					break;
				}
			}
		}
		return block != null;
	}
	
	private TileCopyCat searchForNearbyTile(EntityPlayer player, int x1, int y1, int z1) {
		
		TileCopyCat tile = null;
		
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				for (int k = -1; k < 4; k++)
				{
					int x = x1 + i;
					int y = y1 + k;
					int z = z1 + j;
					
					TileEntity loopTile = player.worldObj.getTileEntity(x, y, z);
					
					if (loopTile != null && loopTile instanceof TileCopyCat)
					{
						tile = (TileCopyCat)loopTile;
						break;
					}
				}
			}
		}
		return tile;
	}
}
