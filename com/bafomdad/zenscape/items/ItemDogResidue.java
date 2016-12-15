package com.bafomdad.zenscape.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class ItemDogResidue extends Item {

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	
    	boolean canFill = false;
    	for (int i = 0; i < player.inventory.getSizeInventory() - player.inventory.armorInventory.length; i++) {
    		
    		ItemStack loopitem = player.inventory.getStackInSlot(i);
    		
    		if (loopitem == null)
    		{
    			player.inventory.setInventorySlotContents(i, new ItemStack(this));
    			canFill = true;
    		}
    	}
    	if (!world.isRemote)
    	{
    		if (canFill)
    			player.addChatMessage(new ChatComponentTranslation("The rest of your inventory filled up with dog residue."));
    		else
    			player.addChatMessage(new ChatComponentTranslation("You finished using it. An uneasy atmosphere fills the room."));
    	}
    	return stack;
    }
}
