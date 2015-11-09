package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ZEnchanter implements IRecipe {
	
	List<ItemStack> flowerTypes;
	ItemStack output;
	
	public ZEnchanter() {
		
		flowerTypes = new ArrayList();
		flowerTypes.add(new ItemStack(Blocks.double_plant, 1, 0));
		flowerTypes.add(new ItemStack(Blocks.double_plant, 1, 1));
		flowerTypes.add(new ItemStack(Blocks.double_plant, 1, 4));
		flowerTypes.add(new ItemStack(Blocks.double_plant, 1, 5));
		
		output = new ItemStack(Blocks.enchanting_table);
	}
	
	public boolean matches(InventoryCrafting ic, World world) {
		
		int numFlowers = 0;
		ItemStack etable = null;
		boolean isEmpty = true;
		
		for (int i = 1; i < ic.getSizeInventory(); i += 2) {
			for (int j = 0; j < flowerTypes.size(); j++)
			{
				ItemStack stack = ic.getStackInSlot(i);
				ItemStack table = ic.getStackInSlot(4);
				ItemStack stack1 = flowerTypes.get(j);
				
				boolean checkEmpty = ic.getStackInSlot(i - 1) == null || ic.getStackInSlot(4) != null;
				
				if (!checkEmpty)
					isEmpty = false;
				
				if (table != null && table.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
					etable = table;
				
				if (stack != null)
				{
					if (areItemsEqual(stack, stack1)) {
						numFlowers++;
					}
				}
			}
		}
//		if (!world.isRemote)
//			System.out.println(numFlowers + ": " + etable + ": " + isEmpty);
		return (numFlowers == 4) && (etable != null) && (isEmpty);
	}
	
	private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		
		return stack1.isItemEqual(stack2) && stack1.getItemDamage() == stack2.getItemDamage();
	}
	
	public ItemStack getCraftingResult(InventoryCrafting ic) {
		
		int flowerCount = 0;
		String flowerName = null;
		
		ItemStack table = null;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			for (int j = 0; j < flowerTypes.size(); j++)
			{
				ItemStack stack = ic.getStackInSlot(i);
				ItemStack flower1 = flowerTypes.get(j);
				
				if (stack != null)
				{
					if (areItemsEqual(stack, flower1))
					{
						flowerCount++;
						flowerName = stack.getDisplayName();
					}
					if (stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
						table = stack;
				}
			}
		}
		if (flowerCount > 0 && flowerName != null)
		{
			this.setItemLore(table, EnumChatFormatting.WHITE + flowerName + ": " + flowerCount);
		}
		
		if (table == null)
			return null;
		
		ItemStack tableCopy = table.copy();
		if (!tableCopy.hasTagCompound())
			tableCopy.setStackDisplayName(EnumChatFormatting.YELLOW + tableCopy.getDisplayName());
		NBTTagCompound tably = tableCopy.getTagCompound();
		
		return tableCopy;
	}
	
	private void setItemLore(ItemStack item, String loreText) {
		
		NBTTagCompound display = item.stackTagCompound.getCompoundTag("display");
		
		if (!(display.hasKey("Lore")))
			display.setTag("Lore", new NBTTagList());
		
		NBTTagList lore = display.getTagList("Lore", 8);
		
		NBTTagString toAdd = new NBTTagString(loreText);
		
		lore.appendTag(toAdd);
		display.setTag("Lore", lore);
	}
	
	public int getRecipeSize() {
		
		return 0;
	}
	
	public ItemStack getRecipeOutput() {
		
		return null;
	}
}