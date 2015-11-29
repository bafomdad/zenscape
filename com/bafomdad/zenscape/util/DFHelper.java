package com.bafomdad.zenscape.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.items.ItemDF;
import com.bafomdad.zenscape.network.PacketFruit;
import com.bafomdad.zenscape.network.ZPacketHandler;

public class DFHelper {

	public static DFHelper INSTANCE = new DFHelper();
	
	public static NBTTagCompound getNBT(EntityPlayer player) {
		
		NBTTagCompound entityData = player.getEntityData();
		if (player.worldObj.isRemote)
			return entityData;
		
		NBTTagCompound tag = entityData.getCompoundTag("PlayerPersisted");
		if (!entityData.hasKey("PlayerPersisted"))
			entityData.setTag("PlayerPersisted", tag);
		
		return tag;
	}
	
	public static void clearFruit(EntityPlayer player) {
		
		if (!player.worldObj.isRemote)
		{
			NBTTagCompound nbt = getNBT(player);
			nbt.removeTag("hasFruit");
		}
	}
	
	public void setFruit(EntityPlayer player, ItemStack stack) {
		
		if (!player.worldObj.isRemote)
		{
			String fruitType = ItemDF.getFruitType(stack).toString();
			NBTTagCompound nbt = getNBT(player);
			nbt.setString("hasFruit", fruitType);
		}
	}
	
	public boolean hasFruit(EntityPlayer player) {
		
		NBTTagCompound nbt = getNBT(player);
		boolean flag = nbt.hasKey("hasFruit");
//		ZPacketHandler.INSTANCE.sendTo(new PacketFruit("foobar"), (EntityPlayerMP)player);
		
		return flag;
	}
}
