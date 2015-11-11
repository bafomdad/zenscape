package com.bafomdad.zenscape.libs;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BlockLibs {

	public static int getStackFromString(String name) {
		
		if (name.equals("Sunflower"))
			return 0;
		if (name.equals("Lilac"))
			return 1;
		if (name.equals("Double Tallgrass"))
			return 2;
		if (name.equals("Large Fern"))
			return 3;
		if (name.equals("Rose Bush"))
			return 4;
		if (name.equals("Peony"))
			return 5;
		return -1;
	}
}
