package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.zenscape.BlockZenScape;
import com.bafomdad.zenscape.TileEntityZenScape;
import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDokuPot extends Block {
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icon;

	public BlockDokuPot(Material material) {
		
		super(material);
	}
	
	public static int renderId;
	
	@Override
	public int getRenderType() {
		
		return renderId;
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	
    	return side == 1 ? icon[0] : (side == 0 ? icon[1] : (side != 2 && side != 3 ? icon[2] : icon[4]));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    	icon = new IIcon[6];
    	for (int i = 0; i < icon.length; i++) {
    		this.icon[i] = register.registerIcon("zenscape:dokupot" + i);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBlockTextureFromSide(IBlockAccess blockAccess, int i, int j, int k, int side) {
    	
    	if (side == 1) {
    		return this.icon[1];
    	}
    	if (side == 0) {
    		return this.icon[2];
    	}
    	return this.icon[3];
    }
	
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        
    	this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
    }
    
    public void setBlockBoundsForItemRender() {
    	
    	this.setBlockBounds(0.0F, 0.0F,  0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public boolean isOpaqueCube() {
    	
    	return false;
    }
    
    public boolean renderAsNormalBlock() {
    	
    	return false;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        
    	int l = func_150027_b(world.getBlockMetadata(x, y, z));
        float f = (float)y + (6.0F + (float)(3 * l)) / 26.0F;

        if (!world.isRemote)
        {
        	if (l > 0 && !(entity instanceof EntityItem))
        	{
        		if (!((EntityLivingBase)entity).isPotionActive(Potion.poison))
        			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(19, 160, 1));
        	}
        	else if (l < 6 && entity instanceof EntityItem)
        	{
            	ItemStack item = ((EntityItem)entity).getEntityItem();
            	if (item.getItem() == ZenScape.itemDokuBottle)
            	{
            		Minecraft.getMinecraft().theWorld.spawnParticle("explode", x, y, z, 0.0D, 0.2D, 0.0D);
            		((EntityItem)entity).setDead();
            		this.func_150024_a(world, x, y, z, l + 1);
            	}
        	}
        }
    }
    
    public static int func_150027_b(int p_150027_0_)
    {
        return p_150027_0_;
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = player.inventory.getCurrentItem();

            if (itemstack == null)
            {
                return true;
            }
            else
            {
                int i1 = world.getBlockMetadata(x, y, z);
                int j1 = func_150027_b(i1);
                {
                    if (itemstack.getItem() == Items.glass_bottle)
                    {
                        if (j1 > 0)
                        {
                            if (!player.capabilities.isCreativeMode)
                            {
                                ItemStack itemstack1 = new ItemStack(ZenScape.itemDokuBottle, 1, 0);

                                if (!player.inventory.addItemStackToInventory(itemstack1))
                                {
                                    world.spawnEntityInWorld(new EntityItem(world, (double)x + 0.5D, (double)y + 1.5D, (double)z + 0.5D, itemstack1));
                                }
                                else if (player instanceof EntityPlayerMP)
                                {
                                    ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
                                }

                                --itemstack.stackSize;

                                if (itemstack.stackSize <= 0)
                                {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                                }
                            }

                            this.func_150024_a(world, x, y, z, j1 - 1);
                        }
                    }
                    else if (j1 > 0 && itemstack.getItem() instanceof ItemArmor && ((ItemArmor)itemstack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH)
                    {
                        ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                        itemarmor.removeColor(itemstack);
                        this.func_150024_a(world, x, y, z, j1 - 1);
                        return true;
                    }

                    return false;
                }
            }
        }
    }
    
    public void func_150024_a(World world, int x, int y, int z, int p_150024_5_)
    {
        world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(p_150024_5_, 0, 6), 2);
        world.func_147453_f(x, y, z, this);
    }
    
    @SideOnly(Side.CLIENT)
    public static float getRenderLiquidLevel(int p_150025_0_)
    {
        int j = MathHelper.clamp_int(p_150025_0_, 0, 6);
        return (float)(6 + 3 * j) / 26.0F;
    }
}
