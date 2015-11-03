package com.bafomdad.zenscape;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityZenScape extends TileEntity {
	
//	protected long ticks = 0L;
//	
//	public void updateEntity() {
//		
//		super.updateEntity();
//		if (this.ticks == 0L) {
//			initiate();
//		} else if (this.ticks >= 9223372036854775807L) {
//			this.ticks = 1L;
//		}
//		this.ticks += 1L;
//	}
//
//	protected void initiate() {}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		
		super.writeToNBT(tag);
		
		writeCustomNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		
		super.readFromNBT(tag);
		
		readCustomNBT(tag);
	}
	
	public void writeCustomNBT(NBTTagCompound tag) {}
	
	public void readCustomNBT(NBTTagCompound tag) {}
	
    @Override
    public Packet getDescriptionPacket() {
    	
    	S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity) super.getDescriptionPacket();
    	NBTTagCompound dataTag = packet != null ? packet.func_148857_g() : new NBTTagCompound();
    	writeCustomNBT(dataTag);
    	return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, dataTag);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    	
    	super.onDataPacket(net, pkt);
    	NBTTagCompound tag = pkt != null ? pkt.func_148857_g() : new NBTTagCompound();
    	readCustomNBT(tag);
    }
}
