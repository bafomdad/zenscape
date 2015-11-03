package com.bafomdad.zenscape.items;

import com.bafomdad.zenscape.entity.EntityDokuDrop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDokuDrop extends Item {

	public ItemDokuDrop() {
		
		super();
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (true)
		{
			world.spawnEntityInWorld(new EntityDokuDrop(world, player));
			if (!player.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}
		}
		return stack;
	}
}
