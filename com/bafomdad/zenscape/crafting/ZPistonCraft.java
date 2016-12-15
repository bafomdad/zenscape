package com.bafomdad.zenscape.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bafomdad.zenscape.blocks.BlockPistonCrafter;

public class ZPistonCraft {

	Block output;
	int blockmeta;
	List<ItemStack> inputs;
	
	public static List<ZPistonCraft> pRecipes = new ArrayList<ZPistonCraft>();
	
	public ZPistonCraft(Block output, int blockmeta, ItemStack... inputs) {
		
		this.output = output;
		this.blockmeta = blockmeta;
		
		List<ItemStack> inputsToSet = new ArrayList();
		for (Object obj : inputs) {
			if (obj instanceof ItemStack  && inputs.length == 4 || inputs.length == 8)
				inputsToSet.add((ItemStack)obj);
			else throw new IllegalArgumentException("ZPistonCraft: Invalid input, or incorrect amount of inputs");
		}
		this.inputs = inputsToSet;
	}
	
	public boolean matches(BlockPistonCrafter.TilePistonCrafter tile) {
		
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
	
	public Object getOutput() {
		
		return output;
	}
	
	public int getOutputMeta() {
		
		return blockmeta;
	}
	
	public static ZPistonCraft addRecipe(Block output, int meta, ItemStack... inputs) {
		
		ZPistonCraft recipe = new ZPistonCraft(output, meta, inputs);
		pRecipes.add(recipe);
		return recipe;
	}
}
