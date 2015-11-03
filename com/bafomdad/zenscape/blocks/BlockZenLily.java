package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.ZenScape;
import com.bafomdad.zenscape.util.LilyPadHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenLily extends BlockBush implements ITileEntityProvider {
	
	public IIcon[] lilyIcon;
	public String[] textureNames = new String[] { "lavalily", "icelily", "lovelily", "junglelily", "enderlily", "inconvenientlily", "stealthlily", "redstonelily", "unusablelily", "receiverlily" };
	
	public static int renderId;

	public BlockZenLily(Material material) {
		
		super(material);
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.useNeighborBrightness = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.lilyIcon = new IIcon[textureNames.length];
		for (int i = 0; i < this.lilyIcon.length; ++i)
		{
			this.lilyIcon[i] = register.registerIcon("zenscape:" + textureNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if (meta >= lilyIcon.length)
			meta = 0;
		return lilyIcon[meta];
	}

	@Override
    public int getRenderType() {
        
		return renderId;
    }

	@Override
	public int damageDropped(int meta) {
		
		if (meta == 8)
			return 5;
		return meta;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int side) {
		
		int meta = access.getBlockMetadata(x, y, z);
		
		if (meta == 7)
			return 7;
		return 0;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for (int i = 0; i < textureNames.length; i++)
		{
			if (i != 8)
				list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == 8)
			return null;
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }

	@Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		Material material = world.getBlock(x, y, z).getMaterial();
		return (super.canPlaceBlockAt(world, x, y, z)) && (material != null) && (!material.isLiquid());
    }

	@Override
    public boolean canBlockStay(World world, int posX, int posY, int posZ) {
		
		int meta = world.getBlockMetadata(posX, posY, posZ);
		
		if (meta == 0)
		{
	        Material material = world.getBlock(posX, posY - 1, posZ).getMaterial();
	        return posY >= 0 && posY < 256 ? world.getBlock(posX, posY - 1, posZ).getMaterial() == Material.lava && world.getBlockMetadata(posX, posY - 1, posZ) == 0 : false;
		}
        Material material = world.getBlock(posX, posY - 1, posZ).getMaterial();
        return posY >= 0 && posY < 256 ? world.getBlock(posX, posY - 1, posZ).getMaterial() == Material.water && world.getBlockMetadata(posX, posY - 1, posZ) == 0 : false;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		int meta = world.getBlockMetadata(x, y, z);

		super.randomDisplayTick(world, x, y, z, rand);
		if (meta == 0) {
			if ((rand.nextInt(2) == 0)) {
				world.spawnParticle("flame", x + rand.nextFloat(), y, z + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
			}
		}
		if (meta == 1) {
			if ((rand.nextInt(2) == 0)) {
				world.spawnParticle("snowshovel", x + rand.nextFloat(), y, z + rand.nextFloat(), 0.0D, 0.1D, 0.0D);
			}
		}
		if (meta == 2) {
			if ((rand.nextInt(15) == 0)) {
				world.spawnParticle("heart", x + rand.nextFloat(), y + 0.1, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
		}
		if (meta == 3) {
			if (rand.nextInt(10) == 0) {
				world.spawnParticle("slime", x + rand.nextFloat(), y + 0.1, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
		}
		if (meta == 4 || meta == 9) {
			if (rand.nextInt(5) == 0) {
				world.spawnParticle("portal", x + rand.nextFloat(), y, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
		}
		if (meta == 7) {
			if (rand.nextInt(5) == 0) {
				world.spawnParticle("reddust", x + 0.5, y + 0.1, z + 0.5, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public void onEntityCollidedWithBlock(World world, int posX, int posY, int posZ, Entity entity) {
		
		int meta = world.getBlockMetadata(posX, posY, posZ);
		
		if (meta == 0)
			LilyPadHelper.instance().func_LavaLily(world, posX, posY, posZ, entity);
		if (meta == 1)
			LilyPadHelper.instance().func_IceLily(world, posX, posY, posZ, entity);
		if (meta == 2)
			LilyPadHelper.instance().func_LoveLily(world, posX, posY, posZ, entity);
		if (meta == 3)
			LilyPadHelper.instance().func_JungleLily(world, posX, posY, posZ, entity);
		if (meta == 4)
			LilyPadHelper.instance().func_EnderLily(world, posX, posY, posZ, entity);
		if (meta == 6)
			LilyPadHelper.instance().func_StealthLily(world, posX, posY, posZ, entity);
	}
	
	@Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		
		return false;
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		
		if (meta == 8)
			this.dropBlockAsItem(world, x, y, z, new ItemStack(ZenScape.blockZenLily, 1, 5));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if (meta == 5)
			return new TileVisibleLily();
		if (meta == 8)
			return new TileInvisibleLily();
		return null;
	}
	
	public static class ItemZenLily extends ItemBlock {

		public ItemZenLily(Block block) {
			
			super(block);
			setHasSubtypes(true);
			setMaxDamage(0);
		}

    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
		
    	@SideOnly(Side.CLIENT)
    	public IIcon getIconFromDamage(int meta) {
    		
    		return ZenScape.blockZenLily.getIcon(0, meta);
    	}
    	
		public int getMetadata(int meta) {
			
			return meta & 15;
		}
		
		public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
			
			MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
			if (movingobjectposition == null) 
			{
				return stack;
			}
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				if (!world.canMineBlock(player, i, j, k))
				{
					return stack;
				}
				if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack))
				{
					return stack;
				}
				if ((world.getBlock(i, j, k).getMaterial() == Material.water) && (world.getBlockMetadata(i, j, k) == 0) && (world.isAirBlock(i, j + 1, k)))
				{
					if (stack.getItemDamage() != 0)
					{
						world.setBlock(i, j + 1, k, ZenScape.blockZenLily, stack.getItemDamage(), 2);
						if (!player.capabilities.isCreativeMode)
						{
							stack.stackSize -= 1;
						}
					}
				}
				if ((world.getBlock(i, j, k).getMaterial() == Material.lava) && (world.getBlockMetadata(i, j, k) == 0) && (world.isAirBlock(i, j + 1, k)))
				{
					if (stack.getItemDamage() == 0)
					{
						world.setBlock(i, j + 1, k, ZenScape.blockZenLily, 0, 2);
						if (!player.capabilities.isCreativeMode)
						{
							stack.stackSize -= 1;
						}
					}
				}
			}
			return stack;
		}
	}
	
	public static class TileVisibleLily extends TileEntity {
		
		@Override
		public void updateEntity() {
			
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2));
			for (EntityPlayer player : players) {
				if (player != null)
				{
					worldObj.setBlock(xCoord, yCoord, zCoord, ZenScape.blockZenLily, 8, 2);
					Minecraft.getMinecraft().theWorld.spawnParticle("cloud", xCoord + 0.5, yCoord + 0.2, zCoord + 0.5, 0.0D, 0.01D, 0.0D);
				}
			}
		}
	}
	
	public static class TileInvisibleLily extends TileEntity {
		
		private int timer = -1;
		
		@Override
		public void updateEntity() {
			
			EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 3);
			
			if (player != null)
			{
				timer = 0;
			}
			if (player == null)
			{
				timer++;
			}
			if (timer >= 100 && player == null)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, ZenScape.blockZenLily, 5, 2);
			}
		}
	}
}
