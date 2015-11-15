package com.bafomdad.zenscape.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ItemDemoRock extends Item {
	
	public ItemDemoRock() {
	
		setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.rare;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return true;
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		list.add("Taking damage from");
		list.add("exploding creepers");
		list.add("will give you gunpowder");
	}
	
	@SubscribeEvent
	public void entityExplode(LivingAttackEvent event) {
		
		if (event.source.isExplosion() && event.source.getSourceOfDamage() instanceof EntityCreeper) {
			if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				for (int i = 0; i < player.inventory.mainInventory.length; i++)
				{
					if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == this)
						player.inventory.addItemStackToInventory(new ItemStack(Items.gunpowder, new Random().nextInt(2) + 1, 0));
				}
			}
		}
	}
}
