package com.bafomdad.zenscape.util;

import java.util.Random;

import org.lwjgl.input.Mouse;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockJoker.TileJoker;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class ZSTickHandler {
	
	Random rand = new Random();
	
	@SubscribeEvent
	public void tickMiningEvent(TickEvent.PlayerTickEvent event) {
		
		MovingObjectPosition mop = event.player.rayTrace(10, 1);
		Block block = event.player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		boolean isButtonDown = Mouse.isButtonDown(0);
		
		if (block != null && block == ZenScape.blockZenLog3 && event.player.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) == 0) {
			if (isButtonDown && event.player.getCurrentEquippedItem() != null && event.player.getCurrentEquippedItem().getItem() instanceof ItemAxe && rand.nextInt(100) == 0) {
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 9; j++) {
						for (int k = 0; k < 5; k++) {
							Block block2 = event.player.worldObj.getBlock(mop.blockX + i, mop.blockY + j, mop.blockZ + k);
							TileJoker tile = (TileJoker)event.player.worldObj.getTileEntity(mop.blockX + i, mop.blockY + j, mop.blockZ + k);
							if (block2 != null && block2 == ZenScape.blockJoker && tile.getStackInSlot(0) == null && event.side == Side.SERVER) 
							{
								System.out.println("yoink");
								this.stealHeldTool(tile, event.player);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void stealHeldTool(TileJoker tile, EntityPlayer player) {
		
		ItemStack tool = player.getCurrentEquippedItem();
		if (tool == null || tile == null)
			return;
		for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
			if (player.inventory.getStackInSlot(i) == tool) {
				player.inventory.setInventorySlotContents(i, null);
				tile.setInventorySlotContents(0, tool);
				ZPacketDispatcher.dispatchTEToNearbyPlayers(tile);
				break;
			}
		}
	}
}
