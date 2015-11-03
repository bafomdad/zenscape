package com.bafomdad.zenscape.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.proxies.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterTorch extends Block {

	public BlockWaterTorch(Material material) {
		
		super(material);
		this.setLightLevel(0.5F);
		this.setTickRandomly(true);
	}
	
	public static int renderId;
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	@Override
	public int getRenderType() {
		
		return renderId;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public int getRenderBlockPass() {
		
		return 1;
	}
	
	@Override
	public boolean canRenderInPass(int pass) {
		
		ClientProxy.renderPass = pass;
		return true;
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Block block = world.getBlock(x, y + 1, z);
		Block block1 = world.getBlock(x, y - 1, z);
		return ((block == Blocks.water) && (block1.isSideSolid(world, x, y, z, ForgeDirection.UP)));
	}
	
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
		if (!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlockToAir(x, y, z);
			this.dropBlockAsItem(world, x, y, z, 0, 0);
		}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        int l = world.getBlockMetadata(x, y, z);
        double d0 = (double)((float)x + 0.5F);
        double d1 = (double)((float)y + 0.7F);
        double d2 = (double)((float)z + 0.5F);
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;

        if (l == 1)
        {
            world.spawnParticle("mobSpell", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 2)
        {
            world.spawnParticle("mobSpell", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 3)
        {
        	world.spawnParticle("mobSpell", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        }
        else if (l == 4)
        {
        	world.spawnParticle("mobSpell", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
        else
        {
        	world.spawnParticle("mobSpell", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
    
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
    	return false;
    }
}
