package com.bafomdad.zenscape.render;

import com.bafomdad.zenscape.blocks.BlockDokuPot;
import com.bafomdad.zenscape.blocks.BlockPowerLog;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPowerLog extends BlockRenderer implements ISimpleBlockRenderingHandler {
	
	private boolean renderPowerLog(IBlockAccess world, int x, int y, int z, int side, int type, Block block, RenderBlocks renderer, int brightness) {
		
		IIcon top = ((BlockPowerLog)block).top;
		IIcon[] sides = ((BlockPowerLog)block).icons;
		float f5 = 0;
		
		World realworld = MinecraftServer.getServer().getEntityWorld();
		BlockPowerLog.TilePowerLog tile = (BlockPowerLog.TilePowerLog)realworld.getTileEntity(x, y, z);
		int power = tile.getPowerAmount();
		
   		renderer.renderFaceXPos(block, x, y, z, sides[power]);
   		renderer.renderFaceXNeg(block, x, y, z, sides[power]);
   		renderer.renderFaceZPos(block, x, y, z, sides[power]);
   		renderer.renderFaceZNeg(block, x, y, z, sides[power]);
   		renderer.renderFaceYPos(block, x, y, z, top);
   		renderer.renderFaceYNeg(block, x, y, z, top);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		drawFaces(renderer, block, ((BlockPowerLog)block).top, ((BlockPowerLog)block).top, ((BlockPowerLog)block).icons[0], ((BlockPowerLog)block).icons[0], ((BlockPowerLog)block).icons[0], ((BlockPowerLog)block).icons[0], true);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

    	int side = world.getBlockMetadata(x, y, z) % 6;
    	int type = (world.getBlockMetadata(x, y, z) - side) / 6;
		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		return renderPowerLog(world, x, y, z, side, type, block, renderer, brightness);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return true;
	}

	@Override
	public int getRenderId() {

		return BlockPowerLog.renderId;
	}

}
