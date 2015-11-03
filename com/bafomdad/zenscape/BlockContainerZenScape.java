package com.bafomdad.zenscape;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerZenScape extends BlockContainer {

	public BlockContainerZenScape(Material material) {
		
		super(material);
	}
	
	public void registerIcons(IIconRegister register) {
		
		this.blockIcon = register.registerIcon("zenscape" + getSafeUnlocalizedName());
	}
	
	public String getSafeUnlocalizedName() {
		
		return getUnlocalizedName().substring(5);
	}
	
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return null;
	}
}
