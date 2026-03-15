package com.tiviacz.cloudboots.item.armor;

import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.platform.Platform;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class DefaultArmorMaterial implements ArmorMaterial, ICustomArmorMaterial {
    private final LazyLoadedValue<Ingredient> cloudRepairIngredient;
    private final ArmorMaterial defaultMaterial;
    private final String materialName;

    public DefaultArmorMaterial(ArmorMaterial defaultMaterial, String materialName) {
        this.defaultMaterial = defaultMaterial;
        this.materialName = materialName;
        this.cloudRepairIngredient = new LazyLoadedValue(() -> Ingredient.of(Items.GOLD_INGOT, Platform.getItem(ModItems.GOLDEN_FEATHER_ID).get()));
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return defaultMaterial.getDurabilityForType(type);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        if(materialName.equals("cloud")) {
            return 5;
        }
        return defaultMaterial.getDefenseForType(type);
    }

    @Override
    public int getEnchantmentValue() {
        return defaultMaterial.getEnchantmentValue();
    }

    @Override
    public SoundEvent getEquipSound() {
        return defaultMaterial.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        if(materialName.equals("cloud")) {
            return cloudRepairIngredient.get();
        }
        return defaultMaterial.getRepairIngredient();
    }

    @Override
    public String getName() {
        if(materialName.equals("cloud")) {
            return "cloudboots:cloud";
        }
        return "cloudboots:" + defaultMaterial.getName() + "_cloud";
    }

    @Override
    public float getToughness() {
        return defaultMaterial.getToughness();
    }

    @Override
    public float getKnockbackResistance() {
        return defaultMaterial.getKnockbackResistance();
    }

    @Override
    public String getMaterialName() {
        return this.materialName;
    }
}