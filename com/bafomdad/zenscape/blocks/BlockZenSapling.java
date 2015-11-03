package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.worldgen.WorldGenDeadTree;
import com.bafomdad.zenscape.worldgen.trees.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockZenSapling extends BlockBush {
	
	public IIcon[] saplingIcon;
	public String[] textureNames = new String[] { "sapling_lightning", "sapling_crafting", "sapling_explosive", "sapling_nature", "sapling_flywood", "sapling_poison", "sapling_dizzy", "sapling_gear", "sapling_uncharged", "sapling_joker" };

	public BlockZenSapling(Material material) {
		
		super(material);
		float f = 0.4f;
		setTickRandomly(true);
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if (world.getBlockMetadata(x, y, z) == 8 && world.isThundering() && !world.isRemote) {
			if (world.getBlock(x + 1, y - 1, z) == ZenScape.blockLightBlock && world.getBlock(x, y - 1, z + 1) == ZenScape.blockLightBlock && world.getBlock(x - 1, y - 1, z) == ZenScape.blockLightBlock && world.getBlock(x, y - 1, z - 1) == ZenScape.blockLightBlock) 
			{
				world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
				world.setBlock(x, y, z, ZenScape.blockZenSapling, 0, 2);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		super.randomDisplayTick(world, x, y, z, rand);
		if ((rand.nextInt(1) == 0) && (world.getBlockMetadata(x, y, z) == 2))
		{
			world.spawnParticle("smoke", x + 0.5F, y + 0.9F, z + 0.5F, 0.0D, 0.05D, 0.0D);
			if (world.rand.nextInt(70) == 0)
			{
				world.playAuxSFX(1004, x, y, z, 15);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.saplingIcon = new IIcon[textureNames.length];
		for (int i = 0; i < this.saplingIcon.length; ++i)
		{
			this.saplingIcon[i] = register.registerIcon("zenscape:" + textureNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		return this.saplingIcon[meta % this.saplingIcon.length];
	}
	
	public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
	{
		return par1World.getBlock(par2, par3, par4) == this && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
	}
	
	@Override
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	public void growTree(World world, int x, int y, int z, Random rand) {
		
		if (!TerrainGen.saplingGrowTree(world, rand, x, y, z)) {
			return;
		}
		int l = world.getBlockMetadata(x, y, z);
		Object object = null;
		int i1 = 0;
		int j1 = 0;
		boolean flag = false;
		
		if (l == 1)
		{
			object = new WorldGenCraftTree();
		}
		else if (l == 2)
		{
			object = new WorldGenBakuTree(true);
		}
		else if (l == 3)
		{
			object = new WorldGenNatureTree();
		}
		else if (l == 4)
		{
			object = new WorldGenFlyWood(false);
		}
		else if (l == 5)
		{
			object = new WorldGenDokuTree();
		}
		else if (l == 6)
		{
			object = new WorldGenDizzyTree();
		}
		else if (l == 7)
		{
			object = new WorldGenDeadTree();
		}
		else if (l == 8)
		{
			object = new WorldGenLightningTree(false);
		}
		else if (l == 9)
		{
			object = new WorldGenJokerTree();
		}
		else
		{
			object = new WorldGenLightningTree(true);
		}

        Block block = Blocks.air;

        if (flag)
        {
            world.setBlock(x + i1, y, z + j1, block, 0, 4);
            world.setBlock(x + i1 + 1, y, z + j1, block, 0, 4);
            world.setBlock(x + i1, y, z + j1 + 1, block, 0, 4);
            world.setBlock(x + i1 + 1, y, z + j1 + 1, block, 0, 4);
        }
        else
        {
            world.setBlock(x, y, z, block, 0, 4);
        }

        if (!((WorldGenerator)object).generate(world, rand, x + i1, y, z + j1))
        {
            if (flag)
            {
                world.setBlock(x + i1, y, z + j1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1, this, l, 4);
                world.setBlock(x + i1, y, z + j1 + 1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1 + 1, this, l, 4);
            }
            else
            {
                world.setBlock(x, y, z, this, l, 4);
            }
        }
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < textureNames.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public static class ItemZenscapeSapling extends ItemBlock {
		
//		public static final String blockType[] = { "lightning", "crafting", "explosive", "nature", "flywood", "poison", "dizzy", "gear", "uncharged" };
		
		public ItemZenscapeSapling(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
    	
    	@SideOnly(Side.CLIENT)
    	public IIcon getIconFromDamage(int par1) {
    		
    		return ZenScape.blockZenSapling.getIcon(0, par1);
    	}
    	
		public int getMetadata(int par1) {
			
			return par1;
		}
	}
}
