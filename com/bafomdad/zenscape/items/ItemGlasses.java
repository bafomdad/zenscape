package com.bafomdad.zenscape.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGlasses extends ItemArmor {

	public String textureName = "zenscape:textures/model/goggles.png";
	
	public ItemGlasses(ArmorMaterial material, int type) {
		
		super(material, 0, type);
		setMaxDamage(700);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		
		return this.textureName;
	}
}
