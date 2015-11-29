package com.bafomdad.zenscape.items;

import java.util.List;

import com.bafomdad.zenscape.network.ZPacketDispatcher;

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBatStaff extends Item {

	public ItemBatStaff() {
		
		super();
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.epic;
	}
	
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		
		List<EntityBat> entities = player.worldObj.getEntitiesWithinAABB(EntityBat.class, AxisAlignedBB.getBoundingBox(player.posX - 15, player.posY - 15, player.posZ - 15, player.posX + 15, player.posY + 15, player.posZ + 15));
		for (EntityBat eb : entities)
			if (eb != entities)
			{
				ZPacketDispatcher.clientParticle("witchmagic", eb.posX, eb.posY, eb.posZ, 0.0D, 0.0D, 0.0D);
				if (!world.isRemote)
					eb.setDead();
			}
		return item;
	}
}
