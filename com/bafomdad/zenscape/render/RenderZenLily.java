package com.bafomdad.zenscape.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.bafomdad.zenscape.blocks.BlockZenLily;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderZenLily implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		int meta = world.getBlockMetadata(x, y, z);

		return renderLilyPads(renderer, block, meta, x, y, z);
	}
	
	public boolean renderLilyPads(RenderBlocks renderer, Block block, int meta, int x, int y, int z) {
		
		Tessellator tessellator = Tessellator.instance;
		IBlockAccess world = renderer.blockAccess;
		IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, meta);
		
		if (renderer.hasOverrideBlockTexture())
		{
			icon = renderer.overrideBlockTexture;
		}
		
		float f = 0.015625F;
		double d0 = (double)icon.getMinU();
		double d1 = (double)icon.getMinV();
		double d2 = (double)icon.getMaxU();
		double d3 = (double)icon.getMaxV();
		long l = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
		l = l * l * 42317861L + l * 11L;
		int i1 = (int)(l >> 16 & 3L);
		
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		float f1 = (float)x + 0.5F;
		float f2 = (float)z + 0.5F;
		float f3 = (float)(i1 & 1) * 0.5F * (float)(1 - i1 / 2 % 2 * 2);
		float f4 = (float)(i1 + 1 & 1) * 0.5F * (float)(1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.setColorOpaque_I(block.getBlockColor());
        tessellator.addVertexWithUV((double)(f1 + f3 - f4), (double)((float)y + f), (double)(f2 + f3 + f4), d0, d1);
        tessellator.addVertexWithUV((double)(f1 + f3 + f4), (double)((float)y + f), (double)(f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((double)(f1 - f3 + f4), (double)((float)y + f), (double)(f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((double)(f1 - f3 - f4), (double)((float)y + f), (double)(f2 + f3 - f4), d0, d3);
        tessellator.setColorOpaque_I(block.getBlockColor());
        tessellator.addVertexWithUV((double)(f1 - f3 - f4), (double)((float)y + f), (double)(f2 + f3 - f4), d0, d3);
        tessellator.addVertexWithUV((double)(f1 - f3 + f4), (double)((float)y + f), (double)(f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((double)(f1 + f3 + f4), (double)((float)y + f), (double)(f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((double)(f1 + f3 - f4), (double)((float)y + f), (double)(f2 + f3 + f4), d0, d1);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return false;
	}

	@Override
	public int getRenderId() {

		return BlockZenLily.renderId;
	}

}
