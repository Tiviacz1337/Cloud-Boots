package com.tiviacz.cloudboots;

import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.EnumHelper;

public class ItemCloudBoots extends ItemArmor
{	
	public static final ArmorMaterial CLOUD = EnumHelper.addArmorMaterial("cloud_boots", CloudBoots.MODID + ":cloud", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
	private AttributeModifier movementSpeed;
	
	public ItemCloudBoots()
	{ 		
		super(CLOUD, 1, EntityEquipmentSlot.FEET);
		
		setCreativeTab(CreativeTabs.COMBAT);
		setRegistryName("cloud_boots");
		setUnlocalizedName("cloud_boots");
		
		this.movementSpeed = new AttributeModifier("CloudBootsMovementSpeedModifier", 0.15D, 2);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		if(slot == EntityEquipmentSlot.FEET && stack.getItem() == this)
		{
			multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), this.movementSpeed);
		}
		return multimap;
    }
			
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) 
	{
		player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 0, 4, false, false));
	
		if(!player.onGround)
		{
			player.jumpMovementFactor += 0.04F;
			
			if(player.fallDistance >= 1.0F)
			{
				if(!world.isRemote && world instanceof WorldServer)
				{
					((WorldServer)world).spawnParticle(EnumParticleTypes.CLOUD, false, player.posX, player.posY, player.posZ, 3, 0, 0, 0, (itemRand.nextFloat() - 0.5F));
				}
				
			/*	else if(world.isRemote)
				{
					for(int i = 0; i < 3; i++)
					{
						world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY, player.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
					}
				} */
					
				player.fallDistance = 0F;
			}
		}
		
		if(player.isSprinting())
		{
			if(!world.isRemote && world instanceof WorldServer)
			{
				((WorldServer)world).spawnParticle(EnumParticleTypes.CLOUD, false, player.posX, player.posY, player.posZ, 1, 0, 0, 0, (itemRand.nextFloat() - 0.5F));
			}

		/*	else if(world.isRemote)
			{
				world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY, player.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
			} */
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