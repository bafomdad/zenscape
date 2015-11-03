package com.bafomdad.zenscape.util;

import java.io.FileWriter;
import java.io.IOException;

import cpw.mods.fml.common.Mod;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class CoordOutput {

	public static void outputFile(World world, int x, int y, int z, int metadata) throws IOException {
		
		FileWriter f0 = new FileWriter("output.txt");	
		String newLine = System.getProperty("line.separator");
		
		for (int i = -metadata; i <= metadata; i++) {
			for (int j = -metadata; j <= metadata; j++) {
				for (int k = -metadata; k <= metadata; k++) {
					if (i * i + j * j + k * k < (metadata + 0.5F) * (metadata + 0.5F)) {
						
						Block block = world.getBlock(x + i, y + j, z + k);
						int meta = world.getBlockMetadata(x + i, y + j, z + k);
						
						if (!world.isAirBlock(x + i, y + j, z + k))
						{
							if (meta > 0)
							{
//								f0.write("world.setBlock(x + " + i + ", y + " + j + ", z + " + k + ", " + block.getUnlocalizedName().substring(5) + ", " + meta + ", 3);");
								f0.write("world.setBlock(x + " + i + ", y + " + j + ", z + " + k + ", " + Block.blockRegistry.getNameForObject(block) + ", " + meta + ", 3);");
								f0.write(newLine);
							}
							else
							{
//								f0.write("world.setBlock(x + " + i + ", y + " + j + ", z + " + k + ", " + block.getUnlocalizedName().substring(5) + ");");
								f0.write("world.setBlock(x + " + i + ", y + " + j + ", z + " + k + ", " + Block.blockRegistry.getNameForObject(block) + ");");
								f0.write(newLine);
							}
						}
					}
				}
			}
		}
		f0.close();
		System.out.println("Coordinates written to output file.");
	}
}
