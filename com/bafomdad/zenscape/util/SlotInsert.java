package com.bafomdad.zenscape.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInsert extends Slot {

	public SlotInsert(EntityPlayer player, IInventory inv, int slotIndex, int xPos, int yPos) {
		
		super(inv, slotIndex, xPos, yPos);
	}
	
	public boolean isItemValid(ItemStack stack) {
		
		return true;
	}
	
	public int getSlotStackLimit() {
		
		return 64;
	}
}
