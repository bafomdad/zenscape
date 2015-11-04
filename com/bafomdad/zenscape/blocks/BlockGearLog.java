package com.bafomdad.zenscape.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGearLog extends BlockLog implements ITileEntityProvider {
	
	public BlockGearLog(Material material) {
		
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon side, top;
	
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int side) {
    	
    	return false;
    }
    
    public boolean renderAsNormalBlock() {
    	
    	return false;
    }
    
    public boolean isOpaqueCube() {
    	
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int par1) {
        
		return top;
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int par1) {
		
		return side;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {

		top = register.registerIcon("zenscape:log_gears_top");
		side = register.registerIcon("zenscape:log_gears");
	}

	public int damageDropped(int par1) {
		
		return par1 & 0x3;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		return new TileGearLog();
	}
	
	public static class TileGearLog extends TileEntity {
		
		@SideOnly(Side.CLIENT)
		public float rotation;
		
		@SideOnly(Side.CLIENT)
		public float renderRotation;
		
		@SideOnly(Side.CLIENT)
		private static final float ROTATION_SPEED = 5.0F;
		
		@SideOnly(Side.CLIENT)
		public boolean isRotating = false, reverseRotation = false;
		
		public int facingMeta = 0;
		
		public void updateEntity() {
			
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			
			if (meta != 0)
				facingMeta = meta;
			
			if (worldObj.isRemote) {
				
				if (!isRotating && worldObj.rand.nextInt(100) + 60 % 10 == 0)
				{
					if (worldObj.rand.nextInt(3) == 0)
					{
						reverseRotation = true;
					}
					isRotating = true;
				}
				if (isRotating) 
				{
					if (reverseRotation) 
					{
						rotation -= ROTATION_SPEED;
						if (rotation <= -90.0F) {
							rotation += 90.0F;
							renderRotation += 90.0F;
							isRotating = false;
							reverseRotation = false;
						}
					}
					if (!reverseRotation)
					{
						rotation += ROTATION_SPEED;
						if (rotation >= 90.0F) {
							rotation -= 90.0F;
							renderRotation -= 90.0F;
							isRotating = false;
						}
					}
				}
			}
		}
	}
}
