package com.bafomdad.zenscape.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.entity.EntityDokuDrop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockZenLeaves extends BlockLeavesBase implements IShearable {
	
	public IIcon[] icons;
	public String[] textureNames = new String[] { "leaves_lightning", "leaves_explosive", "leaves_poison", "leaves_flywood", "leaves_dizzy", "leaves_joker", "leaves_gears", "leaves_copycat" };

	public BlockZenLeaves(Material material, boolean bool) {
		
		super(material, false);
		this.setTickRandomly(true);
	}
	
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 1) {
			return 10.0F;
		}
		return super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		Block block = world.getBlock(x, y - 1, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (block == null || block.isAir(world, x, y, z) && meta == 2)
		{
			if (rand.nextInt(15) == 0)
			{
				EntityDokuDrop edd = new EntityDokuDrop(world, x, y, z);
				edd.setPosition(x + 0.5, y - 0.3, z + 0.5);
				world.spawnEntityInWorld(edd);
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
       
    	Block block = world.getBlock(x, y - 1, z);
		int meta = world.getBlockMetadata(x, y, z);
    	if (block == null || block.isAir(world, x, y, z) && meta == 2)
    	{
    		if (rand.nextInt(5) == 0)
    		{
	    		double d0 = (double)((float)x + rand.nextFloat());
	    		double d1 = (double)y - 0.05D;
	    		double d2 = (double)((float)z + rand.nextFloat());
	    		world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
    		}
    	}
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {

		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {

		ArrayList<ItemStack> ret = new ArrayList();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		return this.icons[meta % this.icons.length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.icons = new IIcon[textureNames.length];
		
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon("zenscape:" + textureNames[i]);
		}
	}
	
	@Override
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	@Override
    public int quantityDropped(Random p_149745_1_)
    {
        return p_149745_1_.nextInt(20) == 0 ? 1 : 0;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		
		for (int i = 0; i < textureNames.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	public static class ItemZenscapeLeaves extends ItemBlock {
		
		public static final String blockType[] = { "lightning", "explosive", "poison", "flywood", "dizzy", "joker" };
		
		public ItemZenscapeLeaves(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
    	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
}
