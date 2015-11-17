package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;

import com.bafomdad.zenscape.blocks.BlockEnchanter.TileEnchanter;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
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
			setItemLore(enchantTable, EnumChatFormatting.WHITE + flower.getDisplayName());
			GameRegistry.addShapedRecipe(enchantTable, new Object[] { " # ", "#T#", " # ", '#', flower, 'T', inputTable });
		}
	}
	
	private static void setItemLore(ItemStack item, String loreText) {
		
		NBTTagCompound display = item.stackTagCompound.getCompoundTag("display");
		
		if (!(display.hasKey("Lore")))
			display.setTag("Lore", new NBTTagList());
		
		NBTTagList lore = display.getTagList("Lore", 8);
		
		NBTTagString toAdd = new NBTTagString(loreText);
		lore.appendTag(toAdd);
		display.setTag("Lore", lore);
	}

	@Override
	public boolean matches(InventoryCrafting ic, World world) {
		
		ItemStack table = null;
		boolean foundFlowers = false;
		
		for (int i = 1; i < ic.getSizeInventory(); i += 2) {
			
			ItemStack stack = ic.getStackInSlot(4);
			
			ItemStack slot1 = ic.getStackInSlot(1);
			ItemStack slot2 = ic.getStackInSlot(3);
			ItemStack slot3 = ic.getStackInSlot(5);
			ItemStack slot4 = ic.getStackInSlot(7);
			
			if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
				if (stack.hasTagCompound()) {
					NBTTagCompound tag = stack.stackTagCompound.getCompoundTag("display");
					if (tag.hasKey("Lore")) {
						NBTTagList lore = tag.getTagList("Lore", 8);
						if (lore.tagCount() < 4)
							table = stack;
					}
				}
			}
			
			if (slot1 != null && slot2 != null && slot3 != null && slot4 != null)
			{
				ItemStack sunflower = new ItemStack(Blocks.double_plant, 1, 0);
				ItemStack lilac = new ItemStack(Blocks.double_plant, 1, 1);
				ItemStack tallgrass = new ItemStack(Blocks.double_plant, 1, 2);
				ItemStack tallfern = new ItemStack(Blocks.double_plant, 1, 3);
				ItemStack rosebush = new ItemStack(Blocks.double_plant, 1, 4);
				ItemStack peony = new ItemStack(Blocks.double_plant, 1, 5);
				
				if (slot1.isItemEqual(sunflower) && slot2.isItemEqual(sunflower) && slot3.isItemEqual(sunflower) && slot4.isItemEqual(sunflower))
					foundFlowers = true;
				else if (slot1.isItemEqual(lilac) && slot2.isItemEqual(lilac) && slot3.isItemEqual(lilac) && slot4.isItemEqual(lilac))
					foundFlowers = true;
				else if (slot1.isItemEqual(tallgrass) && slot2.isItemEqual(tallgrass) && slot3.isItemEqual(tallgrass) && slot4.isItemEqual(tallgrass))
					foundFlowers = true;
				else if (slot1.isItemEqual(tallfern) && slot2.isItemEqual(tallfern) && slot3.isItemEqual(tallfern) && slot4.isItemEqual(tallfern))
					foundFlowers = true;
				else if (slot1.isItemEqual(rosebush) && slot2.isItemEqual(rosebush) && slot3.isItemEqual(rosebush) && slot4.isItemEqual(rosebush))
					foundFlowers = true;
				else if (slot1.isItemEqual(peony) && slot2.isItemEqual(peony) && slot3.isItemEqual(peony) && slot4.isItemEqual(peony))
					foundFlowers = true;
			}
		}
		return table != null && foundFlowers;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {

		ItemStack stack = null;
		ItemStack flowers = null;
		
		for (int j = 1; j < ic.getSizeInventory(); j += 2)
		{
			ItemStack table = ic.getStackInSlot(4);
			ItemStack stack1 = ic.getStackInSlot(j);
			
			if (table != null && table.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
				stack = table;
			if (stack1 != null && stack1.getItem() == Item.getItemFromBlock(Blocks.double_plant))
				flowers = stack1;
		}
		if (stack == null && flowers == null)
			return null;
		
		ItemStack copytable = stack.copy();
		setItemLore(copytable, EnumChatFormatting.WHITE + flowers.getDisplayName());
		
		return copytable;
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
