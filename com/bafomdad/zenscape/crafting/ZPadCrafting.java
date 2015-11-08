package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bafomdad.zenscape.blocks.BlockPistonCrafter;
import com.bafomdad.zenscape.util.ZSEventHandler;

public class ZPadCrafting {

	Block output;
	int blockmeta;
	List<ItemStack> inputs;
	
	public static List<ZPadCrafting> lilyRecipes = new ArrayList<ZPadCrafting>();
	
	public ZPadCrafting(Block output, int blockmeta, ItemStack... inputs) {
		
		this.output = output;
		this.blockmeta = blockmeta;
		
		List<ItemStack> inputsToSet = new ArrayList();
		for (Object obj : inputs) {
			if (obj instanceof ItemStack)
				inputsToSet.add((ItemStack)obj);
			else throw new IllegalArgumentException("Invalid input, expected: ItemStack");
		}
		this.inputs = inputsToSet;
	}
	
	public boolean matches(ZSEventHandler tile) {
		
		List<Object> inputsMissing = new ArrayList(inputs);
		
		for (int i = 0; i < tile.eventList.size(); i++) {
			ItemStack stack = tile.eventList.get(i);
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
	
	public Object getOutput() {
		
		return output;
	}
	
	public int getOutputMeta() {
		
		return blockmeta;
	}
	
	public static ZPadCrafting addRecipe(Block output, int meta, ItemStack... inputs) {
		
		ZPadCrafting recipe = new ZPadCrafting(output, meta, inputs);
		lilyRecipes.add(recipe);
		return recipe;
	}
}
