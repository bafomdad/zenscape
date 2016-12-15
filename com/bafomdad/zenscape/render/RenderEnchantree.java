package com.bafomdad.zenscape.render;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.blocks.BlockEnchantree;
import com.bafomdad.zenscape.util.ZSTickHandler;

public class RenderEnchantree extends TileEntitySpecialRenderer {
	
	public BlockEnchantree.TileEnchantree tile;
	private EntityItem ei = null;
	private RenderBlocks renderBlocks = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float par5) {
		
		tile = (BlockEnchantree.TileEnchantree) tileentity;
		int timestep = (int) ((System.currentTimeMillis()) % 10000);
		double angle = timestep * Math.PI / 5000.0;
		
		ItemStack stack = tile.getStackInSlot(0);
		Minecraft mc = Minecraft.getMinecraft();

    	GL11.glPushMatrix();
        GL11.glTranslatef((float) d0 + 0.5f, (float) d1 + 1.85f, (float) d2 + 0.5f);
        GL11.glScaled(1.25, 1.25, 1.25);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) d0 + 0.5f, (float) d1 + 0.35f, (float) d2 + 0.5f);
		GL11.glRotatef((float) (angle * 57.2957795131), 0.0F, 1.0F, 0.0F);
        
		if (stack != null)
		{
			mc.renderEngine.bindTexture(stack.getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);

			GL11.glScalef(1F, 1F, 1F);
			
			if(!ForgeHooksClient.renderEntityItem(new EntityItem(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, stack), stack, 0F, 0F, tile.getWorldObj().rand, mc.renderEngine, renderBlocks, 1)) {
				
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				if(stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
					GL11.glScalef(0.7F, 0.7F, 0.7F);
					GL11.glTranslatef(0F, 0.5F, 0F);
					GL11.glPushMatrix(); //
					renderBlocks.renderBlockAsItem(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 1F);
					GL11.glPopMatrix(); //
				} else {
					int renderPass = 0;
					GL11.glTranslatef(-0.5f, 0f, 0);
					do {
						IIcon icon = stack.getItem().getIcon(stack, renderPass);
						if(icon != null) {
							Color color = new Color(stack.getItem().getColorFromItemStack(stack, renderPass));
							GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
							float f = icon.getMinU();
							float f1 = icon.getMaxU();
							float f2 = icon.getMinV();
							float f3 = icon.getMaxV();
							
							ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
							GL11.glColor3f(1F, 1F, 1F);
						}
						renderPass++;
					} while(renderPass < stack.getItem().getRenderPasses(stack.getItemDamage()));
				}
			}
		}
		GL11.glPopMatrix();
	}
}
