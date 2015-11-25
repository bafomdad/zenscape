package com.bafomdad.zenscape.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlowerBoots extends ItemArmor {
	
	public String textureName = "zenscape:textures/model/bootsflower.png";

	public ItemFlowerBoots(ArmorMaterial material, int type) {
		
		super(material, 3, type);
		setMaxDamage(50);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		
		return this.textureName;
	}
	
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    	
		if (player.onGround) {
			if (!world.isRemote && world.rand.nextInt(5) == 0) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						
						int x = ((int)player.posX -1) + i;
						int y = (int)player.posY;
						int z = (int)player.posZ + j;

						Block loopblock = world.getBlock(x, y - 1, z);
						boolean isAir = world.isAirBlock(x, y, z);
						
						if (loopblock != null && loopblock == Blocks.grass && isAir)
						{
							world.setBlock(x, y, z, Blocks.tallgrass, 1, 2);
						}
					}
				}
			}
    	}
    }
}
