package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.zenscape.blocks.BlockEnchantree;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ZEnchanter {
	
	ItemStack output;
	List<ItemStack> inputs;
	
	public static List<ZEnchanter> eRecipes = new ArrayList<ZEnchanter>();
	
	public ZEnchanter(ItemStack output, ItemStack... inputs) {
		
		this.output = output;
		
		List<ItemStack> inputsToSet = new ArrayList();
		for (Object obj : inputs) {
			if (obj instanceof ItemStack)
				inputsToSet.add((ItemStack)obj);
			else throw new IllegalArgumentException("ZEnchanter: Invalid input");
		}
		this.inputs = inputsToSet;
	}
	
	public boolean matches(BlockEnchantree.TileEnchantree tile) {
		
		List<Object> inputsMissing = new ArrayList(inputs);
		
		for (int i = 0; i < tile.itemList.size(); i++) {
			ItemStack stack = tile.itemList.get(i);
			if (stack == null)
				break;
			
			int stackIndex = -1;
			
			for (int j = 0; j < inputsMissing.size(); j++) {
				Object input = inputsMissing.get(j);
				
				if (input instanceof ItemStack && stack.areItemStacksEqual((ItemStack)input, stack)) {
					stackIndex = j;
					break;
				}
			}
			if (stackIndex != -1)
				inputsMissing.remove(stackIndex);
			else return false;
		}
		return inputsMissing.isEmpty();
	}
	
	public List<ItemStack> getInputs() {
		
		return new ArrayList(inputs);
	}
	
	public ItemStack getOutput() {
		
		return output;
	}
	
	public static ZEnchanter addRecipe(ItemStack output, ItemStack... inputs) {
		
		ZEnchanter recipe = new ZEnchanter(output, inputs);
		eRecipes.add(recipe);
		return recipe;
	}
}
