package com.bafomdad.zenscape.blocks;

import java.util.Random;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.proxies.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockNatureTree extends Block implements IGrowable {

	public BlockNatureTree(Material material) {
		super(material);
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		world.scheduleBlockUpdate(x, y, z, this, 100);
		
		int metadata = world.getBlockMetadata(x, y, z);
		for (int i = 0; i < 4; i++)
		{
			int x1 = x + rand.nextInt(3) - 2;
			int y1 = y + rand.nextInt(3) - 2;
			int z1 = z + rand.nextInt(3) - 2;
			
			if (metadata == 0)
				this.grow(world, x1, y1, z1);
		}
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ZenScape.blockZenLog);
	}
	
	@Override
	public int damageDropped(int metadata) {
		
		return 3;
	}
	
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean flag) {
    	
    	return true;
    }
	
    @Override
	public boolean func_149852_a(World world, Random random, int x, int y, int z)
    {
        return true;
    }
    
    @Override
	public void func_149853_b(World world, Random random, int x, int y, int z)
    {
        int l = 0;

        while (l < 128)
        {
            int i1 = x;
            int j1 = y + 1;
            int k1 = z;
            int l1 = 0;

            while (true)
            {
                if (l1 < l / 16)
                {
                    i1 += random.nextInt(3) - 1;
                    j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                    k1 += random.nextInt(3) - 1;

                    if (world.getBlock(i1, j1, k1) == ZenScape.blockNatureTree && !world.getBlock(i1, j1, k1).isNormalCube())
                    {
                        ++l1;
                        continue;
                    }
                }
                else if (world.getBlock(i1, j1, k1).getMaterial() == Material.air)
                {
                    if (random.nextInt(8) != 0)
                    {
                        if (Blocks.tallgrass.canBlockStay(world, i1, j1, k1))
                        {
                            world.setBlock(i1, j1, k1, Blocks.tallgrass, 1, 3);
                        }
                    }
                    else
                    {
                        world.getBiomeGenForCoords(i1, k1).plantFlower(world, random, i1, j1, k1);
                    }
                }

                ++l;
                break;
            }
        }
    }
	
	public static boolean grow(World world, int x, int y, int z) {
		
		return grow(world, x, y, z, false);
	}
	
	public static boolean grow(World world, int x, int y, int z, boolean flag) {
		
		Block block = world.getBlock(x, y, z);
		
		if (block == Blocks.dirt)
		{
			if (world.isAirBlock(x, y + 1, z))
			{
				world.setBlock(x, y, z, Blocks.grass);
				flag = true;
			}
		}
		
		else if (block == Blocks.sand)
		{
			if (world.isAirBlock(x, y + 1, z))
			{
				world.setBlock(x, y + 1, z, Blocks.deadbush);
				flag = true;
			}
		}
		
		else if (block == Blocks.cobblestone)
		{
			world.setBlock(x, y, z, Blocks.mossy_cobblestone);
			flag = true;
		}
		
		else if (block instanceof IGrowable && !(block instanceof BlockDoublePlant))
		{
			IGrowable igrowable = (IGrowable) block;
			if (igrowable.func_149852_a(world, world.rand, x, y, z))
			{
				igrowable.func_149853_b(world, world.rand, x, y, z);
				flag = true;
			}
		}
		
		if (flag)
		{
			world.playAuxSFX(2005, x, y, z, 0);
			world.playAuxSFX(2001, x, y, z, 18);
		}
		
		return flag;
	}
}
