package com.bafomdad.zenscape.items;

import java.util.List;

import net.minecraft.block.BlockCake;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCakePickaxe extends ItemPickaxe {

	public ItemCakePickaxe(ToolMaterial toolmat) {
		
		super(toolmat);
		this.setMaxDamage(240);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		list.add(EnumChatFormatting.GOLD + "This pick is pretty sweet.");
	}
	
	@SubscribeEvent
	public void onBlockDrops(BlockEvent.BreakEvent event) {
		
		if (event.getPlayer() != null && event.block != null && event.getPlayer().getCurrentEquippedItem() != null && event.getPlayer().getCurrentEquippedItem().getItem() == this && event.world.getBlock(event.x, event.y, event.z) instanceof BlockCake && event.world.getBlockMetadata(event.x, event.y, event.z) == 0) {
			EntityItem cake = new EntityItem(event.world, event.x + 0.5, event.y, event.z + 0.5, new ItemStack(Items.cake));
			event.world.spawnEntityInWorld(cake);
		}
	}
}
