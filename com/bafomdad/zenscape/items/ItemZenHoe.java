package com.bafomdad.zenscape.items;

import java.util.Set;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemZenHoe extends Item {

	public ItemZenHoe() {
		
		setMaxDamage(131);
		setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        
    	Block sand = world.getBlock(x, y, z);
    	if (sand != null && sand == Blocks.sand) {
    		
    		ItemStack hoe = player.getCurrentEquippedItem();
    		if (hoe != null && hoe.getItem() == this) {
				if (!world.isRemote)
				{				
					world.setBlock(x, y, z, ZenScape.blockZenSand, 0, 3);
					
					hoe.damageItem(1, player);
					return true;
				}
    		}
    	}
    	return false;
    }
}
