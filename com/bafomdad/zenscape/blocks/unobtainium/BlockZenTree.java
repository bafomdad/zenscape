package com.bafomdad.zenscape.blocks.unobtainium;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenTree extends BlockZenScape {
	
	public IIcon treeIcon;
	public IIcon topIcon;

	public BlockZenTree(Material material) {
		
		super(material);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.treeIcon = ZenScape.texLogSide;
		this.topIcon = ZenScape.texLogTop;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		switch(side) 
		{
			case 0: return topIcon;
			case 1: return topIcon;
			default: return treeIcon;
		}
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		
		this.dropBlockAsItem(world, x, y, z, new ItemStack(ZenScape.blockZenLog2, 1, 0));
	}
}
