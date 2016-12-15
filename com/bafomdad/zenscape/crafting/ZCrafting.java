package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.zenscape.blocks.BlockCraftBox;

import net.minecraft.item.ItemStack;

public class ZCrafting {
	
	ItemStack output;
	List<ItemStack> inputs;
	
	public static List<ZCrafting> recipes = new ArrayList<ZCrafting>();
	
	public ZCrafting(ItemStack output, ItemStack... inputs) {
		
		this.output = output;
		
		List<ItemStack> inputsToSet = new ArrayList();
		for (Object obj : inputs) {
			if (obj instanceof ItemStack)
				inputsToSet.add((ItemStack)obj);
			else throw new IllegalArgumentException("ZCrafting: Invalid input");
		}
		this.inputs = inputsToSet;
	}
	
	public boolean matches(BlockCraftBox.TileCraftBox tile) {
		
		List<Object> inputsMissing = new ArrayList(inputs);
		
		for (int i = 0; i < tile.tilesToGetFrom.size(); i++) {
			ItemStack stack = tile.tilesToGetFrom.get(i).getStackInSlot(0);
			if (stack == null)
				break;
			
			int stackIndex = -1;
			
			for (int j = 0; j < inputsMissing.size(); j++) {
				Object input = inputsMissing.get(j);
				
				if (input instanceof ItemStack && simpleAreStacksEqual((ItemStack) input, stack)) {
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
	
	boolean simpleAreStacksEqual(ItemStack stack1, ItemStack stack2) {
		
		return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
	}
	
	public List<ItemStack> getInputs() {
		
		return new ArrayList(inputs);
	}
	
	public ItemStack getOutput() {
		
		return output;
	}
	
	public static ZCrafting addRecipe(ItemStack output, ItemStack... inputs) {
		
		ZCrafting recipe = new ZCrafting(output, inputs);
		recipes.add(recipe);
		return recipe;
	}
}
