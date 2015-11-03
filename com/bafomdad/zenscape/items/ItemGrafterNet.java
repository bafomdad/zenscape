package com.bafomdad.zenscape.items;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrafterNet extends Item {

	public ItemGrafterNet() {
		
		super();
		this.setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        
    	if (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) == ZenScape.blockFruitBomb) 
    	{	
    		ItemStack[] playerInventory = player.inventory.mainInventory;
    		if (playerInventory != null) 
    		{
    			player.inventory.addItemStackToInventory(new ItemStack(ZenScape.blockFruitBomb));
    			world.setBlockToAir(x, y, z);
    		}
    	}
    	return false;
    }
}
