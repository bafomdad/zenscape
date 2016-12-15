package com.bafomdad.zenscape.blocks.ancientblocks;

import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAncientGrass extends Block {

	@SideOnly(Side.CLIENT)
	public IIcon top, side, side_snow;
	
	public BlockAncientGrass(Material material) {
		
		super(material);
		setTickRandomly(true);
		setHardness(0.6F);
		setStepSound(soundTypeGrass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		top = register.registerIcon("zenscape:ancient_grass_top");
		side = register.registerIcon("zenscape:ancient_grass_side");
		side_snow = register.registerIcon("zenscape:ancient_grass_snowed");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		
		switch(par1)
		{
			case 0: return Blocks.dirt.getBlockTextureFromSide(par1);
			case 1: return top;
			default: return side;
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        
		if (side == 1)
        {
            return this.top;
        }
        else if (side == 0)
        {
            return Blocks.dirt.getBlockTextureFromSide(side);
        }
        else
        {
            Material material = access.getBlock(x, y + 1, z).getMaterial();
            return material != Material.snow && material != Material.craftedSnow ? this.side : this.side_snow;
        }
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (!world.isRemote)
		{
			if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2)
			{
				world.setBlock(x, y, z, Blocks.dirt, 0, 3);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
					int i1 = x + rand.nextInt(3) - 1;
					int j1 = y + rand.nextInt(5) - 3;
					int k1 = z + rand.nextInt(3) - 1;
					
					if (world.getBlock(i1, j1, k1) == Blocks.dirt && world.getBlockMetadata(i1, j1, k1) == 0 && world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
					{
						world.setBlock(i1, j1, k1, this);
					}
				}
			}
		}
	}
	
	@Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		
		Block plant = world.getBlock(x, y + 1, z);
		if (plant != null && plant instanceof IPlantable)
		{
			return true;
		}
		return false;
	}
}
