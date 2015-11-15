package com.bafomdad.zenscape.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

import com.bafomdad.zenscape.blocks.BlockCopyCat.TileCopyCat;
import com.bafomdad.zenscape.entity.CopyCatPlayer;

public class CopyCatHelper {
	
	public static final CopyCatHelper INSTANCE = new CopyCatHelper();
	float rotationX, rotationY;
	int range = 4;
	
	public static CopyCatHelper instance() {
		
		return INSTANCE;
	}
	
	public void func_blockInteract(World world, int x, int y, int z, TileCopyCat tile) {
		
		CopyCatPlayer player = new CopyCatPlayer(tile);
		player.addChatMessage(new ChatComponentTranslation("meow."));
	}

	private ForgeDirection[] SIDES = new ForgeDirection[]{
        ForgeDirection.NORTH,
        ForgeDirection.SOUTH,
        ForgeDirection.WEST,
        ForgeDirection.EAST
    };
}
