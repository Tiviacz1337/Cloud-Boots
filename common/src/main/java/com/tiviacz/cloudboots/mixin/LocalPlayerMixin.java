package com.tiviacz.cloudboots.mixin;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = {"playSound", "method_5783", "m_5496_"}, at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void cancelBlockFallSound(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer)(Object)this;
        if((sound == localPlayer.getFallSounds().big() || sound == localPlayer.getFallSounds().small()) && CloudBootsItem.isCloudGearEquipped(localPlayer)) {
            ci.cancel();
        }
    }
}