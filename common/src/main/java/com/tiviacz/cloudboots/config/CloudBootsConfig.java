package com.tiviacz.cloudboots.config;

import com.tiviacz.cloudboots.item.armor.ICustomArmorMaterial;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Locale;

public class CloudBootsConfig {
    public static Server.TierConfig getProperConfig(ICustomArmorMaterial material) {
        if(material.getMaterialName().equals("iron")) {
            return CloudBootsConfig.SERVER.iron;
        }
        if(material.getMaterialName().equals("gold")) {
            return CloudBootsConfig.SERVER.gold;
        }
        if(material.getMaterialName().equals("diamond")) {
            return CloudBootsConfig.SERVER.diamond;
        }
        if(material.getMaterialName().equals("netherite")) {
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

        private Server(ForgeConfigSpec.Builder builder) {
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
            public final ForgeConfigSpec.IntValue jumpBoostLevel;
            public final ForgeConfigSpec.DoubleValue speedModifier;
            public final ForgeConfigSpec.DoubleValue flyingSpeedModifier;
            public final ForgeConfigSpec.BooleanValue negatesFallDamage;

            public TierConfig(ForgeConfigSpec.Builder builder, String tier, int jumpBoost, double speed, double flyingSpeed, boolean fallDamageNegation) {
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
        public final ForgeConfigSpec.BooleanValue spawnParticles;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client-only settings")
                    .push("client");

            spawnParticles = builder
                    .comment("Whether to spawn particles when jumping while wearing Cloud Boots")
                    .define("spawnParticles", true);

            builder.pop();
        }
    }

    //Specs
    public static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverPair.getRight();
        SERVER = serverPair.getLeft();
        Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}