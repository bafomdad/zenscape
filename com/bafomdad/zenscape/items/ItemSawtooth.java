package com.bafomdad.zenscape.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSawtooth extends ItemSword {
	
	public ItemSawtooth(ToolMaterial mat) {
		
		super(mat);
		this.setMaxDamage(512);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		
		return true;
	}
	
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
    	
    	return false;
    }
}
