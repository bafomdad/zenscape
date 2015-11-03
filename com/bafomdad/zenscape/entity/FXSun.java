package com.bafomdad.zenscape.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FXSun extends EntityFX {
	
	public static final ResourceLocation texture = new ResourceLocation("zenscape", "textures/misc/beam.png");
	Random rand = new Random();
	
	float f;
	float f1;
	float f2;
	float f3;
	float f4;
	float f5;
	float alphaFade = 0.1F;
	
	public FXSun(World world, double x, double y, double z) {
		
		super(world, x, y, z);
		particleMaxAge = rand.nextInt(60) + 20;
		noClip = true;
		setSize(0.5F, 0.5F);
		
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
	}
	
	@Override
	public void onUpdate() {
		
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		
		if (particleAge++ >= particleMaxAge)
			alphaFade -= 0.1F;
		else if (particleAge++ <= particleMaxAge)
			alphaFade += 0.05F;
		if (alphaFade <= 0)
			setDead();
	}
	
	@Override
    public void renderParticle(Tessellator tess, float f, float f1, float f2, float f3, float f4, float f5)
    {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		this.f = f;
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.f4 = f4;
		this.f5 = f5;
		
		float f10 = 0.5F * particleScale;
		float f11 = (float)(prevPosX + (posX - prevPosX) * f - interpPosX);
		float f12 = (float)(prevPosY + (posY - prevPosY) * f - interpPosY);
		float f13 = (float)(prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		
		tess.setColorRGBA_F(particleRed, particleGreen, particleBlue, alphaFade);
		tess.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, 0, 1);
		tess.addVertexWithUV(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, 1, 1);
		tess.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, 1, 0);
		tess.addVertexWithUV(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, 0, 0);
    }
}
