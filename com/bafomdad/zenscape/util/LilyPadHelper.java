package com.bafomdad.zenscape.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class LilyPadHelper {
	
	private static final LilyPadHelper INSTANCE = new LilyPadHelper();
	
	private static final String[] IN_LOVE = new String[] { "inLove", "field_70881_d", "bp" };
	
	public static LilyPadHelper instance() {
		
		return INSTANCE;
	}

	public void func_LavaLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if ((!world.isRemote) && ((entity instanceof EntityPlayer)))
		{
			EntityPlayer ep = (EntityPlayer)entity;
			if (!ep.isImmuneToFire() && !ep.capabilities.isCreativeMode)
			{
				ItemStack boots = ep.getCurrentArmor(0);
				if (boots == null)
				{
					ep.setFire(3);
				}
			}
		}
		else if ((entity instanceof EntityLiving))
		{
			EntityLiving el = (EntityLiving)entity;
			if (!el.isImmuneToFire())
			{
				ItemStack boots = el.getEquipmentInSlot(1);
				if (boots == null)
				{
					el.setFire(3);
				}
			}
		}
	}
	
	public void func_IceLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if (world.isRemote)
			return;
		
		if (entity instanceof EntityLivingBase) 
		{
			EntityLivingBase elb = (EntityLivingBase)entity;
			if (!elb.isPotionActive(Potion.moveSpeed))
				elb.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 0));
		}
		if (entity instanceof EntityItem)
		{
			EntityItem ei = (EntityItem)entity;
			if (!ei.isDead && ei.age >= 105)
			{
				ei.age = 105;
			}
		}
	}
	
	public void func_LoveLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if (!world.isRemote && entity instanceof EntityAnimal) 
		{
			EntityAnimal animal = (EntityAnimal)entity;
			int love = ReflectionHelper.getPrivateValue(EntityAnimal.class, animal, this.IN_LOVE);
			if (animal.getGrowingAge() == 0 && love <= 0) {
				ReflectionHelper.setPrivateValue(EntityAnimal.class, animal, 600, this.IN_LOVE);
				animal.setTarget(null);
				world.setEntityState(animal, (byte)18);
			}
		}
	}
	
	public void func_JungleLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if (!world.isRemote && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if (!player.isAirBorne && player.fallDistance > 0.0F)
				player.fallDistance = 0.0F;
		}
	}
	
	public void func_EnderLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if (!world.isRemote)
		{
			if (entity instanceof EntityPlayer) {
			
				EntityPlayer player = (EntityPlayer)entity;
				if (player.inventory.hasItem(Items.ender_pearl))
					this.findThePad(world, posX, posY, posZ, entity);
			}
			if (entity instanceof EntityItem) {
				
				EntityItem item = (EntityItem)entity;
				this.findThePad(world, posX, posY, posZ, entity);
			}
		}
	}
	
	private boolean findThePad(World world, int x, int y, int z, Entity entity) {
		
		int rand1 = world.rand.nextInt(9) + 1;
		int rand2 = world.rand.nextInt(9) + 1;
		for (int i = -rand1; i < rand1; i++) {
			for (int j = -rand2; j < rand2; j++) {
				
				int x1 = x + i;
				int z1 = z + j;
				
				Block lily = world.getBlock(x1, y, z1);
				int meta = world.getBlockMetadata(x1, y, z1);
				
				if (lily != null && lily == ZenScape.blockZenLily && meta == 9) {
					this.teleportStuff(world, x1, y, z1, entity);
					return true;
				}
			}
		}
		return false;
	}
	
	private void teleportStuff(World world, int x, int y, int z, Entity entity) {
		
		entity.setPosition(x + 0.5, y + 1, z + 0.5);
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			player.setPositionAndUpdate(x + 0.5, y + 0.2, z + 0.5);
			if (!player.capabilities.isCreativeMode) {
				for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
					if (player.inventory.mainInventory[slot] == null) {
						continue;
					}
					if (player.inventory.mainInventory[slot].getItem() == Items.ender_pearl) {
						player.inventory.decrStackSize(slot, 1);
					}
				}
			}
		}
	}
	
	public void func_StealthLily(World world, int posX, int posY, int posZ, Entity entity) {
		
		if (!world.isRemote && entity instanceof EntityLivingBase) {
			
			EntityLivingBase elb = (EntityLivingBase)entity;
			if (!elb.isPotionActive(Potion.invisibility))
				elb.addPotionEffect(new PotionEffect(Potion.invisibility.id, 10, 0));
		}
	}
}