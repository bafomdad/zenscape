package com.bafomdad.zenscape.crafting;

import net.minecraft.block.BlockDoublePlant;
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
import cpw.mods.fml.common.registry.GameRegistry;

public class ZEnchanter implements IRecipe {
	
	public static ItemStack enchantTable;
	
	public static void init() {
		
		ItemStack inputTable = new ItemStack(Blocks.enchanting_table).copy();
		
		for (int i = 0; i < BlockDoublePlant.field_149892_a.length; i++)
		{
			ItemStack flower = new ItemStack(Blocks.double_plant, 1, i);
			enchantTable = new ItemStack(Blocks.enchanting_table, 1, i);
			if (!enchantTable.hasTagCompound())
				enchantTable.setStackDisplayName(EnumChatFormatting.YELLOW + enchantTable.getDisplayName());
			setItemLore(enchantTable, EnumChatFormatting.WHITE + flower.getDisplayName() + ": 4");
			GameRegistry.addShapedRecipe(enchantTable, new Object[] { " # ", "#T#", " # ", '#', flower, 'T', inputTable });
		}
	}
	
	private static void setItemLore(ItemStack item, String loreText) {
		
		NBTTagCompound display = item.stackTagCompound.getCompoundTag("display");
		
		if (!(display.hasKey("Lore")))
			display.setTag("Lore", new NBTTagList());
		
		NBTTagList lore = display.getTagList("Lore", 8);
		
		if (lore.tagCount() > 0) {
			for (int j = 0; j < lore.tagCount(); ++j)
			{
				NBTTagString toAdd = new NBTTagString(loreText);
				lore.appendTag(toAdd);
				display.setTag("Lore", lore);
			}
		}
		NBTTagString toAdd = new NBTTagString(loreText);
		lore.appendTag(toAdd);
		display.setTag("Lore", lore);
	}

	@Override
	public boolean matches(InventoryCrafting ic, World world) {
		
		ItemStack table = null;
		
		for (int i = 1; i < ic.getSizeInventory(); i += 2) {
			
			ItemStack stack = ic.getStackInSlot(4);
			
			if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
				if (stack.hasTagCompound())
					table = stack;
			}
		}
		return table != null;
	}
	
	private boolean matchFlowers(ItemStack stack) {
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {

		return null;
	}

	@Override
	public int getRecipeSize() {
		
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}
}
