package com.bafomdad.zenscape.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockZenStairs {

	public static class BlockBlackStairs extends BlockStairs {

		public BlockBlackStairs(String unlocalizedname, Block block, int meta) {
			
			super(block, meta);
			setBlockName(unlocalizedname);
			useNeighborBrightness = true;
		}
	}
	
	public static class BlockBlueStairs extends BlockStairs {
		
		public BlockBlueStairs(String unlocalizedname, Block block, int meta) {
			
			super(block, meta);
			setBlockName(unlocalizedname);
			useNeighborBrightness = true;
		}
	}
	
	public static class BlockGreenStairs extends BlockStairs {

		public BlockGreenStairs(String unlocalizedname, Block block, int meta) {
			
			super(block, meta);
			setBlockName(unlocalizedname);
			useNeighborBrightness = true;
		}
	}
	
	public static class BlockRedStairs extends BlockStairs {

		public BlockRedStairs(String unlocalizedname, Block block, int meta) {
			
			super(block, meta);
			setBlockName(unlocalizedname);
			useNeighborBrightness = true;
		}
	}
}
