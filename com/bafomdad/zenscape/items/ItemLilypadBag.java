package com.bafomdad.zenscape.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemLilypadBag extends Item {

	private static final String TAG_ITEMS = "InvItems";
	private static final String TAG_SLOT = "Slot";
	
	public ItemLilypadBag() {
		
		setMaxStackSize(1);
//		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPickupItem(EntityItemPickupEvent event) {
		
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote && player.isSneaking())
			System.out.println("snek");
		return stack;
	}
}
