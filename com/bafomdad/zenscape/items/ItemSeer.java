package com.bafomdad.zenscape.items;

import com.bafomdad.zenscape.entity.EntitySeer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeer extends Item {

	public ItemSeer() {
		
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		Block block = world.getBlock(x, y, z);
		if (block != null && block.isBlockSolid(world, x, y, z, side))
		{
			stack.stackSize--;
			if (!world.isRemote)
			{
				EntitySeer seer = new EntitySeer(world);
				seer.setPosition(x + 0.5, y, z + 0.5);
				world.spawnEntityInWorld(seer);
			}
			return true;
		}
		return false;
	}
}
