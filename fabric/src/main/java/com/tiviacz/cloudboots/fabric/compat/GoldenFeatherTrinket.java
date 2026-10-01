package com.tiviacz.cloudboots.fabric.compat;

public class GoldenFeatherTrinket //implements Trinket
 {
    public static void registerTrinket() {
        //TrinketsApi.registerTrinket(ModItems.GOLDEN_FEATHER, new GoldenFeatherTrinket());
    }

    /*@Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer.level(), serverPlayer, e -> onBreak(stack, slot, entity));
            }
        }
    }*/
}