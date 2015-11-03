package com.bafomdad.zenscape.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.blocks.BlockFruitBomb;
import com.bafomdad.zenscape.entity.EntityFruitBomb;
import com.bafomdad.zenscape.model.ModelFruitBomb;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFruitBomb extends RendererLivingEntity {
	
	private ResourceLocation texture;
	
	public RenderFruitBomb() {
		
		super(new ModelFruitBomb(), 0.75F);
		
		texture = new ResourceLocation(ZenScape.MOD_ID, "textures/model/fruitbomb.png");
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return texture;
	}

    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityFruitBomb)entity, x, y, z, p_76986_8_, p_76986_9_);
    }
    
    protected boolean func_110813_b(EntityLivingBase entityLivingBase) {
    	
    	return false;
    }
}
