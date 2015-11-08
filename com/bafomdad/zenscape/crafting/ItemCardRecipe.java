package com.bafomdad.zenscape.crafting;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.items.ItemCard;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCardRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting ic, World world) {

		ItemStack tool = null;
		boolean foundCard = false;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (stack != null) {
				Item item = stack.getItem();
				NBTTagCompound tooly = stack.getTagCompound();
				if (item.isRepairable() && (item instanceof ItemTool || item instanceof ItemShears) && tooly != null && !tooly.hasKey(ItemCard.TAG_HIGHLIGHT))
					tool = stack;
				else if (item == ZenScape.itemCard && stack.getItemDamage() == 0)
					foundCard = true;
			}
		}
		
		return tool != null && foundCard;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {

		ItemStack tool = null;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (stack != null && stack.getItem().isDamageable())
				tool = stack;
		}
		
		if (tool == null)
			return null;
		
		ItemStack toolCopy = tool.copy();
		NBTTagCompound tooly = toolCopy.getTagCompound();
		tooly.setBoolean(ItemCard.TAG_HIGHLIGHT, true);
		toolCopy.setStackDisplayName(toolCopy.getDisplayName() + " tagged");
		return toolCopy;
	}

	@Override
	public int getRecipeSize() {

		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {

		return null;
	}
}
