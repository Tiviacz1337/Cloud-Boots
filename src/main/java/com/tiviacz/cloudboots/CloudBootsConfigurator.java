package com.tiviacz.cloudboots;

import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CloudBootsConfigurator
{
    public static final String GAME_LOCATION = Minecraft.getInstance().gameDir.getPath();
    public static final String DIRECTORY_LOCATION = GAME_LOCATION + "/config";
    public static final Path CONFIGURATION_PATH = Paths.get(DIRECTORY_LOCATION);
    public static final String CONFIGURATION_LOCATION = DIRECTORY_LOCATION + "/cloud-boots-configuration";

    //Boots
    public static int durability = 1337;
    public static int damageReductionAmount = 3;
    public static int enchantability = 10;
    public static float toughness = 2.0F;
    public static float knockbackResistance = 0.0F;
    public static double bonusSpeed = 0.15D;
    public static int jumpBoost = 4;
    public static double jumpMovementFactor = 0.012D;
    public static boolean fallDamage = false;
    public static boolean spawnParticles = true;

    //Golden Feather
    public static int goldenFeatherDurability = 385;
    public static boolean goldenFeatherSpawnParticles = true;

    public static void applyValues()
    {
        if(doesConfigurationExsist())
        {
            if(readConfiguration() != null)
            {
                JsonObject cfg = readConfiguration();

                if(cfg.has("durability"))
                {
                    durability = cfg.get("durability").getAsInt();
                }

                if(cfg.has("damageReductionAmount"))
                {
                    damageReductionAmount = cfg.get("damageReductionAmount").getAsInt();
                }

                if(cfg.has("enchantability"))
                {
                    enchantability = cfg.get("enchantability").getAsInt();
                }

                if(cfg.has("toughness"))
                {
                    toughness = cfg.get("toughness").getAsFloat();
                }

                if(cfg.has("knockbackResistance"))
                {
                    knockbackResistance = cfg.get("knockbackResistance").getAsFloat();
                }

                if(cfg.has("bonusSpeed"))
                {
                    bonusSpeed = cfg.get("bonusSpeed").getAsDouble();
                }

                if(cfg.has("jumpBoost"))
                {
                    jumpBoost = cfg.get("jumpBoost").getAsInt();
                }

                if(cfg.has("jumpMovementFactor"))
                {
                    jumpMovementFactor = cfg.get("jumpMovementFactor").getAsDouble();
                }

                if(cfg.has("fallDamage"))
                {
                    fallDamage = cfg.get("fallDamage").getAsBoolean();
                }

                if(cfg.has("spawnParticles"))
                {
                    spawnParticles = cfg.get("spawnParticles").getAsBoolean();
                }

                if(cfg.has("goldenFeatherDurability"))
                {
                    goldenFeatherDurability = cfg.get("goldenFeatherDurability").getAsInt();
                }

                if(cfg.has("goldenFeatherSpawnParticles"))
                {
                    goldenFeatherSpawnParticles = cfg.get("goldenFeatherSpawnParticles").getAsBoolean();
                }
            }
        }
    }

    public static JsonObject readConfiguration()
    {
        if(doesConfigurationExsist())
        {
            try {
                JsonParser parser = new JsonParser();

                FileReader reader = new FileReader(CONFIGURATION_LOCATION);
                Object obj = parser.parse(reader);

                return (JsonObject) obj;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean doesConfigurationExsist()
    {
        if(Files.exists(CONFIGURATION_PATH))
        {
            if(Files.exists(Paths.get(CONFIGURATION_LOCATION)));
            {
                return true;
            }
        }
        return false;
    }

    public void executeConfiguration() throws IOException
    {
        if(Files.exists(CONFIGURATION_PATH))
        {
            if(!Files.exists(Paths.get(CONFIGURATION_LOCATION)))
            {
                JsonObject cfg = new JsonObject();

                cfg.addProperty("durability", 1337);
                cfg.addProperty("damageReductionAmount", 3);
                cfg.addProperty("enchantability", 10);
                cfg.addProperty("toughness", 2.0F);
                cfg.addProperty("knockbackResistance", 0.0F);

                cfg.addProperty("bonusSpeed", 0.15D);
                cfg.addProperty("jumpBoost", 4);
                cfg.addProperty("jumpMovementFactor", 0.012D);

                cfg.addProperty("fallDamage", false);

                cfg.addProperty("spawnParticles", true);

                cfg.addProperty("goldenFeatherDurability", 385);
                cfg.addProperty("goldenFeatherSpawnParticles", true);

                try {
                    FileWriter file = new FileWriter(CONFIGURATION_LOCATION);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(cfg.toString());
                    String formatedJson = gson.toJson(je);

                    file.write(formatedJson);
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            applyValues();
        }
    }
}
