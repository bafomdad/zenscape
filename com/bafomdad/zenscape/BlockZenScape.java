package com.bafomdad.zenscape;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bafomdad.zenscape.items.ItemCard;

public class BlockZenScape extends Block {
	
	public BlockZenScape(Material material) {
		
		super(material);
	}

	public String getSafeUnlocalizedName() {
		
		return getSafeUnlocalizedName(this);
	}
	
	public static String getSafeUnlocalizedName(Block block) {
		
		return block.getUnlocalizedName().substring(5);
	}
	
	@Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		
		if (player.getCurrentEquippedItem() != null)
		{
			NBTTagCompound tool = player.getCurrentEquippedItem().getTagCompound();
			if (tool != null && tool.hasKey(ItemCard.TAG_HIGHLIGHT))
			{
				return ForgeHooks.blockStrength(this, player, world, x, y, z);
			}
		}
		return -1.0F;
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	@Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		
		return false;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if (player.inventory.armorItemInSlot(3) == null || (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() != ZenScape.itemGoggles))
				return;
		}
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
	
	@Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec1, Vec3 vec2) {
    	
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if (player != null && !player.capabilities.isCreativeMode) {
			if (player.inventory.armorItemInSlot(3) == null || (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() != ZenScape.itemGoggles))
				return null;
		}
    	return super.collisionRayTrace(world, x, y, z, vec1, vec2);
    }
}
