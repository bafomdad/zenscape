package com.bafomdad.zenscape.blocks;

import java.util.List;
import java.util.Random;

import com.bafomdad.zenscape.ZenScape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZenLog3 extends BlockLog {

	public IIcon[] icons;
	public IIcon[] top;
	public String[] textureNames = new String[] { "log_negawood", "log_copycat", "log_hunger", "log_murder" };
	
	public BlockZenLog3(Material material) {
		
		setTickRandomly(true);
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		int meta = world.getBlockMetadata(x, y, z) & 0x3;
		int radius = 5;
		
		if (meta == 0) {
			world.scheduleBlockUpdate(x, y, z, this, 100);
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					for (int k = -radius; k <= radius; k++) {
						if (i * i + j * j + k * k < (radius + 0.5F) * (radius + 0.5F)) {
							if (world.isAirBlock(x + i, y + j, z + k) && !(world.getBlock(x + i, y + j - 1, z + k) instanceof IGrowable) && world.getBlockLightValue(x + i, y + j, z + k) >= 3) {
								world.setBlock(x + i, y + j, z + k, ZenScape.blockDarkAir);
							}
							else if (world.getBlock(x+ i, y + j, z + k) == Blocks.torch) {
								EntityItem itemTorch = new EntityItem(world, x + i, y + j, z + k, new ItemStack(Blocks.torch));
								world.spawnEntityInWorld(itemTorch);
								world.setBlockToAir(x + i, y + j, z + k);
							}
						}
					}
				}
			}
		}
		if (meta == 2) {
			world.scheduleBlockUpdate(x, y, z, this, 100);
			EntityPlayer player = world.getClosestPlayer(x, y, z, 6);
			if (player != null)
			{
				if (!player.isPotionActive(Potion.hunger))
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 2));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int par1) {
		
		return this.icons[par1 % icons.length];
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int par1) {
        
		return this.top[par1 % this.top.length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		
		this.icons = new IIcon[textureNames.length];
		this.top = new IIcon[textureNames.length];
		
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon("zenscape:" + textureNames[i]);
			this.top[i] = register.registerIcon("zenscape:" + textureNames[i] + "_top");
		}
	}

	@Override
	public int damageDropped(int par1) {
		
		return par1 & 0x3;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		
		for (int i = 0; i < 4; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	public static class ItemZenscapeLog3 extends ItemBlock {
		
		public ItemZenscapeLog3(Block block) {
			
			super(block);
			setHasSubtypes(true);
		}
		
    	@Override
    	public String getUnlocalizedName(ItemStack stack) {
    		
    		return this.getUnlocalizedName() + "." + stack.getItemDamage();
    	}
    	
    	public int getMetadata(int par1) {
    		
    		return par1;
    	}
	}
}
