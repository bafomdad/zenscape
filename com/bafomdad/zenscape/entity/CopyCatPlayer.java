package com.bafomdad.zenscape.entity;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class CopyCatPlayer extends FakePlayer {
	
	private static GameProfile NAME = new GameProfile(UUID.fromString("2ff9e90e-b172-4472-881a-3174a733097e"), "[MeoW]");
	
	public String myName = "[MeoW]";

	public CopyCatPlayer(WorldServer world, GameProfile name) {
		
		super(world, NAME);
		this.addedToChunk = false;
	}
	
	@Override
	public double getDistanceSq(double x, double y, double z) {
		
		return 0F;
	}
	
	@Override
	public double getDistance(double x, double y, double z) {
		
		return 0F;
	}
	
	@Override
	public String getDisplayName() {
		
		return getCommandSenderName();
	}
	
	@Override
	public float getEyeHeight() {
		
		return 1.1F;
	}
	
	@Override
	public void addChatMessage(IChatComponent chat) {}
	
	@Override
	public void addChatComponentMessage(IChatComponent chat) {}
}
