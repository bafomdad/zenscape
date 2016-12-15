package com.bafomdad.zenscape.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bafomdad.zenscape.util.test.ZBlockHandler;
import com.bafomdad.zenscape.util.test.ZBlockHandler.ZWorldInfo;

public class ItemBombStaff extends Item {
	
	private int range = 4;

	public ItemBombStaff() {
		
		setMaxStackSize(1);
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.bow;
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return 72000;
	}
	
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		if (player.worldObj.isRemote)
			return;
	}
	
	public ItemStack onItemRightClick2(ItemStack stack, World world, EntityPlayer player) {
		
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

    	if (!world.isRemote)
    	{
    		int playerX = MathHelper.floor_double(player.posX);
    		int playerY = MathHelper.floor_double(player.posY);
    		int playerZ = MathHelper.floor_double(player.posZ);
    	
    		for (int x1 = -range; x1 <= range; x1++) {
    			for (int y1 = -2; y1 <= 2; y1++) {
    				for (int z1 = -range; z1 <= range; z1++) {
    					
    					int x = playerX + x1;
    					int y = playerY + y1;
    					int z = playerZ + z1;
    					
    					Block block = world.getBlock(x, y, z);
    					Set<ZWorldInfo> savedBlocks = ZBlockHandler.INSTANCE.getSavedBlocks(world, new ChunkCoordinates(x, y, z));
    					
    					if (savedBlocks != null) {
    						for (ZWorldInfo info : savedBlocks)
    						{
    							if (block == Blocks.air || block == Blocks.flowing_water || block == Blocks.flowing_lava) {
    								world.setBlock(info.coords.posX, info.coords.posY, info.coords.posZ, info.block, info.metadata, 2);
    								ZBlockHandler.INSTANCE.removeBlock(world, info);
    							}
    							else
    							{
    								ZBlockHandler.INSTANCE.removeBlock(world, info);
    							}
    						}
    					}
    				}
    			}
    		}	
    	}	
		return stack;
	}
}
