package com.bafomdad.zenscape.util.test;

import static java.util.Collections.newSetFromMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class ZBlockHandler {
	
	public static ZBlockHandler INSTANCE;
	
	public ConcurrentHashMap<Integer, ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>>> blockInfo = new ConcurrentHashMap<Integer, ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>>>();
	
	private ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>> getMultimap(int dimension) {
		
		if (blockInfo.get(dimension) == null)
		{
			ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>> mm = new ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>>();
			blockInfo.put(dimension, mm);
		}
		return blockInfo.get(dimension);
	}
	
	public void addBlock(int world, ChunkCoordinates loc, ZWorldInfo info) {
		
		if (!getMultimap(world).containsKey(loc))
			getMultimap(world).put(loc, newSetFromMap(new ConcurrentHashMap<ZWorldInfo, Boolean>()));
		getMultimap(world).get(loc).add(info);
		ZSaveData.setDirty(world);
	}
	
	public void addBlock(World world, ChunkCoordinates loc, Block blockType, int metadata) {
		
		if (!getMultimap(world.provider.dimensionId).containsKey(loc))
			getMultimap(world.provider.dimensionId).put(loc, newSetFromMap(new ConcurrentHashMap<ZWorldInfo, Boolean>()));
		getMultimap(world.provider.dimensionId).get(loc).add(new ZWorldInfo(loc, blockType, metadata, world.provider.dimensionId));
		
		ZSaveData.setDirty(world.provider.dimensionId);
	}
	
	public void removeBlock(World world, ZWorldInfo info) {
		
		if (info == null || world == null)
			return;
		
		for (Set<ZWorldInfo> infol : getMultimap(world.provider.dimensionId).values())
		{
			Iterator<ZWorldInfo> it = infol.iterator();
			while (it.hasNext())
			{
				ZWorldInfo itInfo = it.next();
				if (info.containsBlocks(itInfo))
				{
					it.remove();
					
					if (world.blockExists(itInfo.coords.posX, itInfo.coords.posY, itInfo.coords.posY))
						world.addBlockEvent(itInfo.coords.posX, itInfo.coords.posY, itInfo.coords.posZ, world.getBlock(itInfo.coords.posX, itInfo.coords.posY, itInfo.coords.posZ), -1, 0);
				}
			}
		}
		ZSaveData.setDirty(world.provider.dimensionId);
	}
	
	public synchronized Set<ZWorldInfo> getSavedBlocks(World world, ChunkCoordinates coords) {
		
		ConcurrentHashMap<ChunkCoordinates, Set<ZWorldInfo>> map = getMultimap(world.provider.dimensionId);
		if (map.containsKey(coords))
			return map.get(coords);
		return null;
	}
	
	public Collection<ZWorldInfo> getAllBlocks(World world) {
		
		Set<ZWorldInfo> ret = newSetFromMap(new ConcurrentHashMap<ZWorldInfo, Boolean>());
		for (Set<ZWorldInfo> blockList : getMultimap(world.provider.dimensionId).values())
			ret.addAll(blockList);
		return ret;
	}
	
	public void clearAllBlocks(int world) {
		
		getMultimap(world).clear();
	}
	
	public Set<Integer> getRelevantDimensions() {
		
		return blockInfo.keySet();
	}
	
	public static class ZWorldInfo implements Comparable<ZWorldInfo> {
		
		public ChunkCoordinates coords;
		public Block block;
		public int metadata;
		public int dimID;
		
		public ZWorldInfo(ChunkCoordinates coords, Block block, int metadata, int dimID) {
			
			this.coords = coords;
			this.block = block;
			this.metadata = metadata;
			this.dimID = dimID;
		}
		
		public boolean containsBlocks(ZWorldInfo info) {
			
			if (!(info instanceof ZWorldInfo))
				return false;
			boolean location = coords.equals(info.coords);
			boolean blockType = block.equals(info.block);
			boolean blockMeta = (metadata == info.metadata);
			boolean world = (dimID == info.dimID);
			return location && blockType && blockMeta && world;
		}

		public NBTTagCompound writeNBT() {
			
			NBTTagCompound tag = new NBTTagCompound();
			if (coords != null)
				tag.setIntArray("coords", new int[]{ coords.posX, coords.posY, coords.posZ });
			tag.setInteger("block", block.getIdFromBlock(block));
			tag.setInteger("metadata", metadata);
			tag.setInteger("world", dimID);
			
			return tag;
		}
		
		public static ZWorldInfo readNBT(NBTTagCompound tag) {
			
			if (tag == null)
				return null;
			int[] getcoords = tag.getIntArray("coords");
			ChunkCoordinates coords = new ChunkCoordinates( getcoords[0], getcoords[1], getcoords[2] );
			
			Block blocktype = Block.getBlockById(tag.getInteger("block"));
			int meta = tag.getInteger("metadata");
			int dimID = tag.getInteger("world");
			
			if (coords != null && blocktype != null && meta != -1 && dimID != -1)
				return new ZWorldInfo(coords, blocktype, meta, dimID);
			return null;
		}
		
		@Override
		public int compareTo(ZWorldInfo info) {

			if (equals(info))
				return 0;
			return 0;
		}
	}
}
