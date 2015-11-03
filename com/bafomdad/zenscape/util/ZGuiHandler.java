package com.bafomdad.zenscape.util;

import com.bafomdad.zenscape.blocks.BlockRAC;
import com.bafomdad.zenscape.blocks.BlockRAC.TileRAC;
import com.bafomdad.zenscape.render.GuiRAC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class ZGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);
		
		if (ID == 1)
		{
			if (tile instanceof TileRAC)
				return new BlockRAC.ContainerRAC(player.inventory, (TileRAC)tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);
		
		if (ID == 1)
		{
			if (tile instanceof BlockRAC.TileRAC)
				return new GuiRAC((BlockRAC.TileRAC)tile, player.inventory, world, x, y, z);
		}
		return null;
	}
}
