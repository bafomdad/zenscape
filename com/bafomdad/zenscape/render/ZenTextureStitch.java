package com.bafomdad.zenscape.render;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ZenTextureStitch extends TextureAtlasSprite {

	public ZenTextureStitch(String str) {
		
		super(str);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateAnimation() {
		
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		if (p != null) 
		{
			if ((p.getCurrentArmor(3) != null && p.getCurrentArmor(3).getItem() == ZenScape.itemGoggles) || p.capabilities.isCreativeMode)
			{
				this.frameCounter = 0;
			}
			else
			{
				this.frameCounter = 1;
			}
		}
		TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
	}
	
	@Override
	public boolean hasAnimationMetadata() {
		
		return true;
	}
}
