package com.bafomdad.zenscape.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ItemEasyBadge extends Item implements IBauble {
	
	private int range = 5;
	private static final String[] FIELD_G = new String[] { "field_96561_g", "field_96561_g", "zz" };
	private static final String[] FUSETIME = new String[] { "fuseTime", "field_82225_f", "bq" };

	public ItemEasyBadge() {
		
		super();
		setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		list.add("Makes mobs easier.");
	}

	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase entity) {

		return true;
	}

	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase entity) {

		return true;
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase entity) {}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity) {}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase elb) {
		
		EntityPlayer player = (EntityPlayer)elb;
		
		List<EntityLiving> entities = player.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range));
		
		for (EntityLiving entity : entities) {
			List<EntityAITaskEntry> entries = new ArrayList(entity.tasks.taskEntries);
			entries.addAll(new ArrayList(entity.targetTasks.taskEntries));
			
			for (EntityAITaskEntry entry : entries) {
				if (entry.action instanceof EntityAIArrowAttack) {
					makeSkellyShootSlower((EntityAIArrowAttack)entry.action);
				}
			}
			if (entity instanceof EntityCreeper)
				ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper)entity, 60, this.FUSETIME);
			if (entity instanceof EntitySpider)
				((EntitySpider)entity).setAIMoveSpeed(((EntitySpider)entity).getAIMoveSpeed() / 2);
		}
	}
	
	private void makeSkellyShootSlower(EntityAIArrowAttack aiEntry) {
	
		int timeShoot = ReflectionHelper.getPrivateValue(EntityAIArrowAttack.class, aiEntry, this.FIELD_G);
		
		if (timeShoot == 20) 
			ReflectionHelper.setPrivateValue(EntityAIArrowAttack.class, aiEntry, 100, this.FIELD_G);
	}
	
	@SubscribeEvent
	public void blockZombieGanging(ZombieEvent event) {
		
		EntityLivingBase target = event.getSummoner().getAttackTarget();
		EntityPlayer player = (EntityPlayer)target;
		
		if (target != null)
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
	
	@SubscribeEvent
	public void cancelEnderPort(EnderTeleportEvent event) {
		
		if (event.entity.worldObj.isRemote)
			return;
		
		if (event.entity instanceof EntityEnderman) {
			EntityPlayer player = event.entity.worldObj.getClosestPlayerToEntity(event.entity, 5);
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
	
	@SubscribeEvent
	public void removeExpDrops(EntityJoinWorldEvent event) {
		
		if (event.entity instanceof EntityXPOrb)
		{
			EntityPlayer player = (EntityPlayer)event.entity.worldObj.getClosestPlayerToEntity(event.entity, 5);
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
}
