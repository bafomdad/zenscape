package com.bafomdad.zenscape.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum Dyes {

	INK_SAC(0, 1118481),  ROSE_RED(1, 12464176),  CACTUS_GREEN(2, 5732898),  COCOA_BEANS(3, 5057301),  LAPIS_LAZULI(4, 2247599),  PURPLE_DYE(5, 8532146),  CYAN_DYE(6, 3968688),  LIGHT_GRAY_DYE(7, 11513789),  GRAY_DYE(8, 7763574),  PINK_DYE(9, 15574987),  LIME_DYE(10, 8639516),  DANDELION_YELLOW(11, 15197994),  LIGHT_BLUE_DYE(12, 12179199),  MAGENTA_DYE(13, 14383829),  ORANGE_DYE(14, 15113780),  BONE_MEAL(15, 16777215);
	
	public final int damageValue;
	public final int rgb;
	public static final Dyes[] DYES = { INK_SAC, ROSE_RED, CACTUS_GREEN, COCOA_BEANS, LAPIS_LAZULI, PURPLE_DYE, CYAN_DYE, LIGHT_GRAY_DYE, GRAY_DYE, PINK_DYE, LIME_DYE, DANDELION_YELLOW, LIGHT_BLUE_DYE, MAGENTA_DYE, ORANGE_DYE, BONE_MEAL };

	private Dyes(int damageValue, int rgb) {
		
		this.damageValue = damageValue;
		this.rgb = rgb;
	}
	
	public ItemStack createStack() {
		
		return createStack(1);
	}
	
	public ItemStack createStack(int quantity) {
		
		return new ItemStack(Items.dye, quantity, this.damageValue);
	}
	
	public static Dyes fromStack(ItemStack potion) {
		
		if ((potion.getItem() == Items.dye) && (potion.getItemDamage() >= 0) && (potion.getItemDamage() < DYES.length)) {
			return DYES[potion.getItemDamage()];
		}
		return BONE_MEAL;
	}
}
