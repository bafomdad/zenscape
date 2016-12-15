package com.bafomdad.zenscape.blocks;

import java.util.List;

import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRainThistle extends BlockBush implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	public IIcon[] iconArray;
	
	public BlockRainThistle(Material material) {
		
		super(material);
		float f = 0.4f;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	
	public int damageDropped(int par1) {
		
		return par1;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		iconArray = new IIcon[2];
		for (int i = 0; i < iconArray.length; i++) 
		{
			iconArray[i] = register.registerIcon("zenscape:" + this.textureName + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {

		return this.iconArray[meta];
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		list.add(new ItemStack(item, 1, 0));
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileRainThistle) {
			if (entity != null && entity instanceof EntityPlayer) {
				if (((EntityPlayer)entity).capabilities.isCreativeMode)
					((TileRainThistle)tile).isCreative = true;
			}
		}
	}
	
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if (meta == 0)
			return new TileRainThistle();
		
		return null;
	}
	
	public static class ItemRainThistle extends ItemBlock {

		public ItemRainThistle(Block block) {
			
			super(block);
			setHasSubtypes(true);
			setMaxDamage(0);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
    	
    	@SideOnly(Side.CLIENT)
    	public IIcon getIconFromDamage(int meta) {
    		
    		return ZenScape.blockRainThistle.getIcon(0, meta);
    	}
	}
	
	public static class TileRainThistle extends TileEntityZenScape {
		
		public boolean isCreative = false;
		
		public void updateEntity() {
			
			if (this.getWorldObj().isRaining() && this.getWorldObj().getWorldChunkManager().getBiomeGenAt(xCoord, zCoord).getIntRainfall() > 0 || this.getWorldObj().isThundering())
			{
				if (worldObj.rand.nextInt(100) == 0)
				{
					getWorldObj().getWorldInfo().setRaining(false);
					if (!isCreative)
						getWorldObj().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
				}
			}
		}
		
		public void writeCustomNBT(NBTTagCompound tag) {
			
			tag.setBoolean("RAINTHISTLE_CREATIVE", this.isCreative);
		}
		
		public void readCustomNBT(NBTTagCompound tag) {
			
			this.isCreative = tag.getBoolean("RAINTHISTLE_CREATIVE");
		}
	}
}
