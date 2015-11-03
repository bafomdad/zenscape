package com.bafomdad.zenscape.render;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockWaterTorch;
import com.bafomdad.zenscape.proxies.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderWaterTorch implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		if (ClientProxy.renderPass == 0)
		{
			renderer.renderBlockTorch(ZenScape.blockWaterTorch, x, y, z);
		}
		else
		{
			renderer.renderStandardBlock(Blocks.water, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return false;
	}

	@Override
	public int getRenderId() {

		return BlockWaterTorch.renderId;
	}
}
