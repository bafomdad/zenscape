package com.bafomdad.zenscape.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockDokuPot;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDokuPot extends BlockRenderer implements ISimpleBlockRenderingHandler {
	
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        
    	int side = world.getBlockMetadata(x, y, z) % 6;
    	int type = (world.getBlockMetadata(x, y, z) - side) / 6;
    	int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
    	return renderDokuPot(world, x, y, z, side, type, block, renderer, brightness);
    }

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		drawFaces(renderer, block, ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[1], ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[3], ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[2], ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[2], ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[2], ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[2], true);
	}
	
	public boolean renderDokuPot(IBlockAccess world, int x, int y, int z, int side, int type, Block block, RenderBlocks renderer, int brightness) {
		
    	Tessellator tessellator = Tessellator.instance;

    	renderer.setRenderBoundsFromBlock(block);
    	renderer.renderStandardBlock(block, x, y, z);
    	
   		IIcon innerSide = ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[4];
   		IIcon bottom = ((com.bafomdad.zenscape.blocks.BlockDokuPot)block).icon[5];
   		float f5 = 0.123F;
    	
   		renderer.renderFaceXPos(block, x - 1.0F + f5, y, z, innerSide);
   		renderer.renderFaceXNeg(block, x + 1.0F - f5, y, z, innerSide);
   		renderer.renderFaceZPos(block, x, y, z - 1.0F + f5, innerSide);
   		renderer.renderFaceZNeg(block, x, y, z + 1.0F - f5, innerSide);
   		renderer.renderFaceYPos(block, x, y - 1.0F + 0.25F, z, bottom);
   		renderer.renderFaceYNeg(block, x, y + 1.0F - 0.75F, z, bottom);
   		int i1 = world.getBlockMetadata(x, y, z);

        if (i1 > 0)
        {
        	IIcon iicon = BlockLiquid.getLiquidIcon("water_still");
        	tessellator.setColorRGBA(180, 0, 60, 256);
        	renderer.renderFaceYPos(block, (double)x, (double)((float)y - 1.0F + BlockDokuPot.getRenderLiquidLevel(i1)), (double)z, iicon);
    	}
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return true;
	}

	@Override
	public int getRenderId() {

		return BlockDokuPot.renderId;
	}
}
