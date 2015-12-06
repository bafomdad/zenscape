package com.bafomdad.zenscape.blocks;

import com.bafomdad.zenscape.ZenScape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockHeartCake extends BlockCake {
	
	IIcon heartcakeInner;
	IIcon heartcakeTop;
	IIcon heartcakeBottom;

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(this.getTextureName() + "_side");
        this.heartcakeInner = register.registerIcon(this.getTextureName() + "_inner");
        this.heartcakeTop = register.registerIcon(this.getTextureName() + "_top");
        this.heartcakeBottom = register.registerIcon(this.getTextureName() + "_bottom");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.heartcakeTop : (p_149691_1_ == 0 ? this.heartcakeBottom : (p_149691_2_ > 0 && p_149691_1_ == 4 ? this.heartcakeInner : this.blockIcon));
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(ZenScape.blockHeartCake);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	
    	if (player.getHealth() >= player.getMaxHealth())
    		return false;
    	this.healingCake(world, x, y, z, player);

    	return true;
    }
    
    private void healingCake(World world, int x, int y, int z, EntityPlayer player) {
    	
    	int meta = world.getBlockMetadata(x, y, z) + 1;
    	
    	player.heal(2.0F);
    	if (meta >= 6)
    		world.setBlockToAir(x, y, z);
    	else
    		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }
}