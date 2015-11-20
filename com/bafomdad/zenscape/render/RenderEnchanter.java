package com.bafomdad.zenscape.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.blocks.BlockEnchanter.TileEnchanter;
import com.bafomdad.zenscape.model.ModelGearLog;
import com.bafomdad.zenscape.util.ZSTickHandler;

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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderEnchanter extends TileEntitySpecialRenderer {
	
	RenderBlocks renderBlocks = new RenderBlocks();
	ModelGearLog cube = new ModelGearLog();
	ResourceLocation texture = new ResourceLocation("textures/blocks/cauldron_inner.png");

	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		
		TileEnchanter enchanter = (TileEnchanter)tile;
		
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(x, y, z);
		
		int items = 0;
		for (int i = 0; i < enchanter.getSizeInventory(); i++)
		{
			if (enchanter.getStackInSlot(i) == null)
				break;
			else items++;
		}
		float[] angles = new float[enchanter.getSizeInventory()];
		
		float anglePer = 360F / items;
		float totalAngle = 0F;
		for (int i = 0; i < angles.length; i++)
			angles[i] = totalAngle += anglePer;
		
		double time = ZSTickHandler.ticksInGame + partialTicks;
		
		GL11.glPushMatrix();
		float f5 = 0.0175F;
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(0F + (float)time, 1F, 1F, 0F);
		GL11.glRotatef(0F + (float)time * 0.375F, 1F, 0f, 0F);
		bindTexture(texture);
		cube.render(tile, f5);
		GL11.glPopMatrix();
		
		for(int i = 0; i < enchanter.getSizeInventory(); i++) 
		{
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef(1F, 0.5F, 1F);
			GL11.glRotatef(angles[i] + (float) time, 0F, 1F, 0F);
			GL11.glTranslatef(1.25F, 0F, 0.5F); // default 2.25F
			GL11.glRotatef(90F, 0F, 1F, 0F);
//			GL11.glTranslated(0D, 0.15 * Math.sin((time + i * 10) / 5D), 0F);
			
			ItemStack stack = enchanter.getStackInSlot(i);
			Minecraft mc = Minecraft.getMinecraft();
			
			if(stack != null) {
				mc.renderEngine.bindTexture(stack.getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);

				GL11.glScalef(2F, 2F, 2F);
				if(!ForgeHooksClient.renderEntityItem(new EntityItem(enchanter.getWorldObj(), enchanter.xCoord, enchanter.yCoord, enchanter.zCoord, stack), stack, 0F, 0F, enchanter.getWorldObj().rand, mc.renderEngine, renderBlocks, 1)) {
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					if(stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
						GL11.glScalef(0.5F, 0.5F, 0.5F);
						GL11.glTranslatef(1F, 1.1F, 0F);
						renderBlocks.renderBlockAsItem(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 1F);
						GL11.glTranslatef(-1F, -1.1F, 0F);
						GL11.glScalef(2F, 2F, 2F);
					} else {
						int renderPass = 0;
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
		
		GL11.glPopMatrix();
	}
}
