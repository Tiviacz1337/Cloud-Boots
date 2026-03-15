package com.tiviacz.cloudboots.config;

import com.tiviacz.cloudboots.init.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Locale;

public class CloudBootsConfig {
    public static Server.TierConfig getProperConfig(Holder<ArmorMaterial> material) {
        if(material.is(ModArmorMaterials.IRON_CLOUD)) {
            return CloudBootsConfig.SERVER.iron;
        }
        if(material.is(ModArmorMaterials.GOLD_CLOUD)) {
            return CloudBootsConfig.SERVER.gold;
        }
        if(material.is(ModArmorMaterials.DIAMOND_CLOUD)) {
            return CloudBootsConfig.SERVER.diamond;
        }
        if(material.is(ModArmorMaterials.NETHERITE_CLOUD)) {
            return CloudBootsConfig.SERVER.netherite;
        }
        return CloudBootsConfig.SERVER.cloud;
    }

    public static class Server {
        public final TierConfig iron;
        public final TierConfig gold;
        public final TierConfig diamond;
        public final TierConfig netherite;
        public final TierConfig cloud;

        private Server(ModConfigSpec.Builder builder) {
            builder.comment("Server config settings")
                    .push("server");

            iron = new TierConfig(builder, "Iron", 1, 0.05D, 0.01D, true);
            gold = new TierConfig(builder, "Gold", 2, 0.10D, 0.02D, true);
            diamond = new TierConfig(builder, "Diamond", 3, 0.15D, 0.025D, true);
            netherite = new TierConfig(builder, "Netherite", 4, 0.20D, 0.03D, true);
            cloud = new TierConfig(builder, "Cloud", 4, 0.15D, 0.03D, true);

            builder.pop();
        }

        public static class TierConfig {
            public final ModConfigSpec.IntValue jumpBoostLevel;
            public final ModConfigSpec.DoubleValue speedModifier;
            public final ModConfigSpec.DoubleValue flyingSpeedModifier;
            public final ModConfigSpec.BooleanValue negatesFallDamage;

            public TierConfig(ModConfigSpec.Builder builder, String tier, int jumpBoost, double speed, double flyingSpeed, boolean fallDamageNegation) {
                builder.comment(tier + " Cloud Boots Settings").push(tier.toLowerCase(Locale.ENGLISH) + "CloudBoots");
                jumpBoostLevel = builder
                        .worldRestart()
                        .defineInRange("jumpBoostLevel", jumpBoost, 0, 10);
                speedModifier = builder
                        .worldRestart()
                        .defineInRange("speedModifier", speed, 0, 5.0);
                flyingSpeedModifier = builder
                        .worldRestart()
                        .defineInRange("flyingSpeedModifier", flyingSpeed, 0, 5.0);
                negatesFallDamage = builder
                        .worldRestart()
                        .define("negatesFallDamage", fallDamageNegation);
                builder.pop();
            }
        }
    }

    public static class Client {
        public final ModConfigSpec.BooleanValue spawnParticles;

        Client(ModConfigSpec.Builder builder) {
            builder.comment("Client-only settings")
                    .push("client");

            spawnParticles = builder
                    .comment("Whether to spawn particles when jumping while wearing Cloud Boots")
                    .define("spawnParticles", true);

            builder.pop();
        }
    }

    //Specs
    public static final ModConfigSpec serverSpec;
    public static final Server SERVER;
    public static final ModConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        Pair<Server, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(Server::new);
        serverSpec = serverPair.getRight();
        SERVER = serverPair.getLeft();
        Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}