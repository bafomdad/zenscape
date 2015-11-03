package com.bafomdad.zenscape.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSawtooth extends Item {
	
	public ItemSawtooth() {
		
		super();
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		
		return true;
	}
}
