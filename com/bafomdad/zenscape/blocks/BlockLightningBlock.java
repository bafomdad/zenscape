package com.bafomdad.zenscape.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLightningBlock extends Block {
	
	public BlockLightningBlock(Material material) {
		
		super(material);
	}

	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if (player.inventory.armorItemInSlot(0) == null)
				return;
		}
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
}
