package com.bafomdad.zenscape.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class ItemSolomonRing extends Item {

	public ItemSolomonRing() {
		
		super();
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.rare;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		
		if (player.isSneaking()) 
		{	
			if (itemstack.stackTagCompound == null) 
			{
				itemstack.setTagCompound(new NBTTagCompound());
			}
			
			NBTTagCompound tag = itemstack.stackTagCompound;
			tag.setBoolean("isActive", !tag.getBoolean("isActive"));
			if (tag.getBoolean("isActive"))
			{
				if (world.isRemote) 
				{
					player.addChatMessage(new ChatComponentTranslation("This ring is active."));
				}
			}
			else
			{
				if (world.isRemote)
				{
					player.addChatMessage(new ChatComponentTranslation("This ring is inactive."));
				}
			}
		}
		return itemstack;
	}
}
