package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPowerLog extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	public IIcon top;

	public BlockPowerLog(Material material) {
		
		super(material);
		setTickRandomly(true);
	}
	
	public int damageDropped(int par1) {
		
		return 0;
	}
	
	public int getPowerTexture(World world, int x, int y, int z) {
		
		TileEntityPowerLog te = (TileEntityPowerLog)world.getTileEntity(x, y, z);
		te.getPowerAmount();
		return (int) Math.ceil(te.getPowerAmount() / 1000);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		icons = new IIcon[13];
		for (int i = 0; i < icons.length; i++) 
		{
			icons[i] = register.registerIcon("zenscape:" + this.textureName + i);
			top = register.registerIcon("zenscape:" + this.textureName + "_top");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		
		switch(par1)
		{
			case 0: return top;
			case 1: return top;
			default: return icons[par2];
		}
	}
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	TileEntityPowerLog te = (TileEntityPowerLog)world.getTileEntity(x, y, z);
        if (!world.isRemote)
        {
            if (!te.isPowered && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                te.isPowered = true;
            }
            else if (te.isPowered && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
            	te.isPowered = false;
            }
        }
    }
   
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		TileEntityPowerLog te = (TileEntityPowerLog)world.getTileEntity(x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, 20);
		if (!te.isPowered && world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			te.isPowered = true;
		}
		world.setBlockMetadataWithNotify(x, y, z, this.getPowerTexture(world, x, y, z), 2);
	}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
    
    	if (!world.isRemote)
    	{
			TileEntityPowerLog te = (TileEntityPowerLog) world.getTileEntity(x, y, z);
    		if (player.getCurrentEquippedItem() == null)
    		{
    			String s;
    			s = String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(te.getPowerAmount()) }) + " / " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(te.maxPower) });
    			player.addChatMessage(new ChatComponentTranslation(s));
    		}
    		if (player.getCurrentEquippedItem() != null)
    		{
    			if (te.getPowerAmount() != 12000)
    			{
    				if (player.getCurrentEquippedItem().getItem() == Items.redstone)
    				{
    					te.addPower(300);
    					world.setBlockMetadataWithNotify(x, y, z, this.getPowerTexture(world, x, y, z), 2);
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 12)
			this.copySparklingRedstoneOre(world, x, y, z);
	}
	
	private void copySparklingRedstoneOre(World world, int x, int y, int z) {
		
        Random random = world.rand;
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double d1 = (double)((float)x + random.nextFloat());
            double d2 = (double)((float)y + random.nextFloat());
            double d3 = (double)((float)z + random.nextFloat());

            if (l == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube())
            {
                d2 = (double)(y + 1) + d0;
            }

            if (l == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube())
            {
                d2 = (double)(y + 0) - d0;
            }

            if (l == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube())
            {
                d3 = (double)(z + 1) + d0;
            }

            if (l == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube())
            {
                d3 = (double)(z + 0) - d0;
            }

            if (l == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube())
            {
                d1 = (double)(x + 1) + d0;
            }

            if (l == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube())
            {
                d1 = (double)(x + 0) - d0;
            }

            if (d1 < (double)x || d1 > (double)(x + 1) || d2 < 0.0D || d2 > (double)(y + 1) || d3 < (double)z || d3 > (double)(z + 1))
            {
                world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
	}
	
	public boolean hasTileEntity(int meta) {
		
		return true;
	}
	
	public TileEntity createTileEntity(World world, int meta) {
		
		return new TileEntityPowerLog();
	}
	
	public static class TileEntityPowerLog extends TileEntityZenScape {
	
		private static final String NBT_POWER_AMOUNT = "storage";
		public final int maxPower = 12000;
		public int power = 0;
		public boolean isPowered;
		
		public void readFromNBT(NBTTagCompound nbt) {
			
			super.readFromNBT(nbt);
			power = nbt.getInteger(NBT_POWER_AMOUNT);
		}

		public void writeToNBT(NBTTagCompound nbt) {
			
			super.writeToNBT(nbt);
			nbt.setInteger(NBT_POWER_AMOUNT, power);
		}
		
		public int getPowerAmount() {
			
			return power;
		}
		
		public void setPowerAmount(int amount) {
			
			power = amount;
		}

		public int addPower(int count) {
			
			int totalPower = count + power;
			if (totalPower > maxPower) {
				power = maxPower;
				return totalPower - maxPower;
			}
			else
			{
				power = totalPower;
				return 0;
			}
		}
		
		public void drainPower() {
			
			if (!(power > 0))
				return;
			else
				power--;
		}
		
		public int drainPower(int count) {
			
			if (!(power > 0))
				return 0;
			else
				return power -= count;
		}
		
		@Override
		public void updateEntity() {
			
			if (worldObj.isThundering() && getPowerAmount() != maxPower && worldObj.rand.nextInt(1000) == 0)
			{
//				System.out.println("strike lightning");
				worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, this.xCoord, this.yCoord, this.zCoord));
				if (!worldObj.isRemote)
					this.addPower(3000);
			}
			if (!this.isPowered)
				return;
			else
				this.drainPower(20);
		}
	}
}
