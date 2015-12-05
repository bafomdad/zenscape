package com.bafomdad.zenscape.items;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ItemGravRing extends Item implements IBauble {
	
	int RANGE = 5;
	
	public ItemGravRing() {
		
		super();
		setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		list.add("Those pesky falling blocks.");
	}
	
	@SubscribeEvent
	public void onBlockFall(EntityJoinWorldEvent event) {
		
		if (event.entity != null && event.entity instanceof EntityFallingBlock)
		{
			EntityPlayer player = event.entity.worldObj.getClosestPlayerToEntity(event.entity, RANGE);
			if (player != null)
			{
				InventoryBaubles playerInv = PlayerHandler.getPlayerBaubles(player);
				for (int i = 0; i < playerInv.getSizeInventory(); i++) {
					ItemStack ring = playerInv.getStackInSlot(i);
					if (ring != null && ring.getItem() == this)
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {

		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {

		return true;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {

		return BaubleType.RING;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {

	}

	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
		
	}
}
