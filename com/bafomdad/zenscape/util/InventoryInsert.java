package com.bafomdad.zenscape.util;

import com.bafomdad.zenscape.blocks.BlockRAC.ContainerRAC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryInsert implements IInventory {
	
	private ItemStack[] stackList;
	private ContainerRAC contRAC;
	private int x, y, z;
	
	public InventoryInsert(ContainerRAC cont) {
		
		this.stackList = new ItemStack[1];
		this.contRAC = cont;
	}

	@Override
	public int getSizeInventory() {

		return stackList.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return slot >= this.getSizeInventory() ? null : this.stackList[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {

		if (this.stackList[par1] != null)
		{
			ItemStack itemstack;
			if (this.stackList[par1].stackSize <= par2)
			{
				itemstack = this.stackList[par1];
				this.stackList[par1] = null;
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.stackList[par1].splitStack(par2);
				if (this.stackList[par1].stackSize == 0)
				{
					this.stackList[par1] = null;
				}
				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {

        if (this.stackList[p_70304_1_] != null)
        {
            ItemStack itemstack = this.stackList[p_70304_1_];
            this.stackList[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack stack) {
		
		IInventory inv = (IInventory) contRAC.rac.getContainerCoords(x, y, z);
		boolean flag = false;
		
		this.stackList[var1] = stack;
		if (this.stackList[var1] != null)
		{
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				if (inv.getStackInSlot(i) == null) {
					flag = true;
					inv.setInventorySlotContents(i, stack);
					inv.markDirty();
					break;
				}
				if (inv.getStackInSlot(i) != null && (inv.getStackInSlot(i) == stack && inv.getStackInSlot(i).getItemDamage() == stack.getItemDamage())) {
					if (inv.getStackInSlot(i).getMaxStackSize() > inv.getStackInSlot(i).stackSize) {
						flag = true;
						System.out.println("can merge");
						break;
					}
					else
						return;
				}
			}
		}
		if (flag)
		{
			this.stackList[var1] = null;
			this.markDirty();
			flag = false;
		}
	}

	@Override
	public String getInventoryName() {

		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {

		return false;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void markDirty() {
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {

		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {

		return true;
	}
}
