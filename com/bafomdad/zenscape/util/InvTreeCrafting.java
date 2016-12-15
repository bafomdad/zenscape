package com.bafomdad.zenscape.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvTreeCrafting extends InventoryCrafting {

	public InvTreeCrafting(int par2, int par3) {
		
		super(FakeContainer.instance, par2, par3);
	}
	
	public int hashCode() {
		
		int hash = 0;
		int n = 1;
		for (int i = 0; i < getSizeInventory(); i++)
		{
			n *= 31;
			hash += n * hashItemStack(getStackInSlot(i));
		}
		return hash;
	}
	
	public int hashItemStack(ItemStack item) {
		
		if (item == null)
			return 0;
		
		int k = Item.getIdFromItem(item.getItem());
		k = k * 31 + item.getItemDamage();
		k *= 31;
		if (item.hasTagCompound()) {
			k += item.getTagCompound().hashCode();
		}
		return k;
	}
	
	public static class FakeContainer extends Container {
		
		public static FakeContainer instance = new FakeContainer();
		
		public boolean canInteractWith(EntityPlayer player) {
			
			return false;
		}
	}
}
