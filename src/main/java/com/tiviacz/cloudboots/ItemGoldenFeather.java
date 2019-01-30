package com.tiviacz.cloudboots;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemGoldenFeather extends Item
{
	public ItemGoldenFeather()
	{
		setUnlocalizedName("golden_feather");
		setRegistryName("golden_feather");
		setCreativeTab(CreativeTabs.MATERIALS);
		setMaxStackSize(1);
		setMaxDamage(385);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		if(isSelected)
		{
			if(entityIn.fallDistance >= 3.0F)
			{
				stack.damageItem(1, (EntityLivingBase)entityIn);
				entityIn.fallDistance = 0.0F;
				
				if(!worldIn.isRemote && worldIn instanceof WorldServer)
				{
					((WorldServer)worldIn).spawnParticle(EnumParticleTypes.CLOUD, false, entityIn.posX, entityIn.posY, entityIn.posZ, 3, 0, 0, 0, (itemRand.nextFloat() - 0.5F));
				}
				
		/*		for(int i = 0; i < 3; i++)
				{
					worldIn.spawnParticle(EnumParticleTypes.CLOUD, entityIn.posX, entityIn.posY, entityIn.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
				}   */
			}
		}
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if(repair.getItem() == Items.GOLD_INGOT)
        {
        	return true;
        }
        return false;
    }
}