package com.tiviacz.cloudboots;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class GoldenFeatherCurio implements ICurio
{
    private final ItemStack stack;

    public static ICurio createGoldenFeatherCurioProvider(ItemStack stack)
    {
        return new GoldenFeatherCurio(stack);
    }

    public GoldenFeatherCurio(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity)
    {
        if(livingEntity instanceof ServerPlayerEntity)
        {
            if(livingEntity.fallDistance >= 3.0F)
            {
                stack.hurtAndBreak(1, (ServerPlayerEntity)livingEntity, e -> curioBreak(stack, livingEntity));
                livingEntity.fallDistance = 0.0F;

                if(!livingEntity.level.isClientSide && livingEntity.level instanceof ServerWorld)
                {
                    ((ServerWorld)livingEntity.level).sendParticles(ParticleTypes.CLOUD, livingEntity.xOld, livingEntity.yOld, livingEntity.zOld, 3, 0, 0, 0, (livingEntity.level.random.nextFloat() - 0.5F));
                }

		/*		for(int i = 0; i < 3; i++)
				{
					worldIn.spawnParticle(EnumParticleTypes.CLOUD, entityIn.posX, entityIn.posY, entityIn.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
				}   */
            }
        }
    }
}
