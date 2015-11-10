package com.bafomdad.zenscape.items;

import java.util.List;

import scala.actors.threadpool.Arrays;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.util.ItemNBTHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLilypadBag extends Item {

	private static final String TAG_LILYPAD_COUNT = "lilypadCount";
	
	public ItemLilypadBag() {
		
		setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPickupItem(EntityItemPickupEvent event) {
		
		ItemStack stack = event.item.getEntityItem();
		if (stack.getItem() == Item.getItemFromBlock(Blocks.waterlily) && stack.stackSize > 0)
		{
			for (int i = 0; i < event.entityPlayer.inventory.getSizeInventory(); i++) {
				if (i == event.entityPlayer.inventory.currentItem)
					continue;
				ItemStack invStack = event.entityPlayer.inventory.getStackInSlot(i);
				if (invStack != null && invStack.getItem() == this)
				{
					add(invStack, stack.stackSize);
					stack.stackSize -= stack.stackSize;
				}
				if (stack.stackSize == 0)
					return;
			}
		}
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (player.isSneaking())
		{
			if (getBlockCount(stack) > 0)
			{	
				int[] counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.length];
				Arrays.fill(counts, 0);
				for (int i = 0; i < counts.length; i++) {
					
					boolean hasItem = false;
					boolean hasSpace = false;
					
					ItemStack playerStack = player.inventory.getStackInSlot(i);
					
					if (playerStack != null && playerStack.getItem() == Item.getItemFromBlock(Blocks.waterlily))
					{
						hasItem = true;
						if ((playerStack.stackSize < playerStack.getMaxStackSize())) {
							hasSpace = true;
						}
						if ((hasItem) && (hasSpace))
						{
							int space = Math.min(player.inventory.getInventoryStackLimit(), playerStack.getMaxStackSize() - playerStack.stackSize);
							int mergeAmount = Math.min(space, getBlockCount(stack));
							
							ItemStack copy = playerStack.copy();
							copy.stackSize += mergeAmount;
							player.inventory.setInventorySlotContents(i, copy);
							add(stack, -mergeAmount);
							break;
						}
					}
					else if (playerStack == null)
					{
						hasSpace = true;
						if (getBlockCount(stack) < 64)
						{
							player.inventory.setInventorySlotContents(i, new ItemStack(Blocks.waterlily, getBlockCount(stack), 0));
							add(stack, -getBlockCount(stack));
							break;
						}
						else if (getBlockCount(stack) >= 64)
						{
							player.inventory.setInventorySlotContents(i, new ItemStack(Blocks.waterlily, 64, 0));
							add(stack, -64);
							break;
						}
					}
				}
			}
		}
		if (getBlockCount(stack) > 0)
		{
			MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
			if (movingobjectposition == null) 
			{
				return stack;
			}
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				if (!world.canMineBlock(player, i, j, k))
				{
					return stack;
				}
				if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack))
				{
					return stack;
				}
				if ((world.getBlock(i, j, k).getMaterial() == Material.water) && (world.getBlockMetadata(i, j, k) == 0) && (world.isAirBlock(i, j + 1, k)))
				{
					world.setBlock(i, j + 1, k, Blocks.waterlily);
					add(stack, -1);
				}
			}
		}
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		int count = getBlockCount(stack);
		list.add(EnumChatFormatting.GOLD + "" + count + " " + StatCollector.translateToLocal("Lily Pad"));
	}
	
	private void add(ItemStack stack, int count) {
		
		int current = getBlockCount(stack);
		setCount(stack, current + count);
	}
	
	private static void setCount(ItemStack stack, int count) {
		
		ItemNBTHelper.setInt(stack, TAG_LILYPAD_COUNT, count);
	}
	
	private static int getBlockCount(ItemStack stack) {
		
		return ItemNBTHelper.getInt(stack, TAG_LILYPAD_COUNT, 0);
	}
}
