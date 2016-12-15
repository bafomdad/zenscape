package com.bafomdad.zenscape.items;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.entity.EntityItemAncientClock;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAncientClock extends Item {

	public ItemAncientClock() {
		
		setMaxStackSize(1);
		EntityRegistry.registerModEntity(EntityItemAncientClock.class, EntityItemAncientClock.class.getSimpleName(), 4, ZenScape.instance, 16, 4, true);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		
		return true;
	}
	
	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		
		return Integer.MAX_VALUE;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack stack) {
		
		if (location instanceof EntityItem) {
			EntityItemAncientClock ac = new EntityItemAncientClock(world, (EntityItem)location, stack);
			
			return ac;
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.uncommon;
    }
}
