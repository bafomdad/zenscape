package com.bafomdad.zenscape.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class ItemClayShovel extends ItemSpade {

	public ItemClayShovel(ToolMaterial toolmat) {
		
		super(toolmat);
		this.setMaxDamage(161);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void clayDrops(HarvestDropsEvent event) {
		if (event.harvester != null && event.block != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() == this && event.block.getMaterial() == Material.clay && event.block.canSilkHarvest(event.world, event.harvester, event.x, event.y, event.z, event.blockMetadata)) {
			event.drops.clear();
			event.drops.add(new ItemStack(event.block, 1, event.blockMetadata));
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		
		return stack2.getItem() == Items.brick ? true : super.getIsRepairable(stack1, stack2);
	}
}
