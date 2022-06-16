package com.tiviacz.cloudboots;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
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
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void curioTick(SlotContext context)
    {
        if(context.entity() instanceof ServerPlayer)
        {
            if(context.entity().fallDistance >= 3.0F)
            {
                stack.hurt(1, context.entity().level.random, (ServerPlayer)context.entity());
                context.entity().fallDistance = 0.0F;

                if(!context.entity().level.isClientSide && context.entity().level instanceof ServerLevel)
                {
                    ((ServerLevel)context.entity().level).sendParticles(ParticleTypes.CLOUD, context.entity().xOld, context.entity().yOld, context.entity().zOld, 3, 0, 0, 0, (context.entity().level.random.nextFloat() - 0.5F));
                }
            }
        }
    }
}
