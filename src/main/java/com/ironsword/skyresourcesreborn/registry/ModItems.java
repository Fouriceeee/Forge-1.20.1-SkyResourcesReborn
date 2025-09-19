package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.item.*;
import com.ironsword.skyresourcesreborn.item.snowball.HeavyExplosiveSnowballItem;
import com.ironsword.skyresourcesreborn.item.snowball.HeavySnowballItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SkyResourcesReborn.MODID);

    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        ModCreativeTabs.CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    public static final RegistryObject<Item> DARK_MATTER = registerWithTab("dark_matter",()->new Item(basicItem()));
    public static final RegistryObject<Item> LIGHT_MATTER = registerWithTab("light_matter",()->new Item(basicItem()));
    public static final RegistryObject<Item> PLANT_MATTER = registerWithTab("plant_matter",()->new PlantMatterItem(basicItem()));

    public static final RegistryObject<Item> ALCHCOAL = registerWithTab("alchcoal",()->new Item(basicItem()));
    public static final RegistryObject<Item> ALCHDIAMOND = registerWithTab("alchdiamond",()->new Item(basicItem()));
    public static final RegistryObject<Item> ALCHIRON_INGOT = registerWithTab("alchiron_ingot",()->new Item(basicItem()));
    public static final RegistryObject<Item> ALCHGOLD_INGOT = registerWithTab("alchgold_ingot",()->new Item(basicItem()));
    public static final RegistryObject<Item> ALCHGOLD_NEEDLE = registerWithTab("alchgold_needle",()->new Item(basicItem()));
    public static final RegistryObject<Item> FROZEN_IRON_INGOT = registerWithTab("frozen_iron_ingot",()->new Item(basicItem()));

    public static final RegistryObject<Item> CRYSTAL_SHARD = registerWithTab("crystal_shard",()->new Item(basicItem()));
    public static final RegistryObject<Item> SAWDUST = registerWithTab("sawdust",()->new Item(basicItem()));
    public static final RegistryObject<Item> CRUSHED_STONE = registerWithTab("crushed_stone",()->new Item(basicItem()));

    public static final RegistryObject<Item> HEAVY_SNOWBALL = registerWithTab("heavy_snowball",()->new HeavySnowballItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> HEAVY_EXPLOSIVE_SNOWBALL = registerWithTab("heavy_explosive_snowball",()->new HeavyExplosiveSnowballItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CACTUS_FRUIT = registerWithTab("cactus_fruit",()->new Item(basicItem()));
    public static final RegistryObject<Item> CACTUS_NEEDLE = registerWithTab("cactus_needle",()->new Item(basicItem()));
    public static final RegistryObject<Item> BLOODY_CACTUS_NEEDLE = registerWithTab("bloody_cactus_needle",()->new Item(basicItem()));

    public static final RegistryObject<Item> CACTUS_CUTTING_KNIFE = registerWithTab("cactus_cutting_knife",()->new CuttingKnifeItem(Tiers.WOOD,6.0F, -3.2F, basicItem()));
    public static final RegistryObject<Item> STONE_CUTTING_KNIFE = registerWithTab("stone_cutting_knife",()->new CuttingKnifeItem(Tiers.STONE,7.0F, -3.2F, basicItem()));
    public static final RegistryObject<Item> IRON_CUTTING_KNIFE = registerWithTab("iron_cutting_knife",()->new CuttingKnifeItem(Tiers.IRON,6.0F, -3.1F, basicItem()));
    public static final RegistryObject<Item> DIAMOND_CUTTING_KNIFE = registerWithTab("diamond_cutting_knife",()->new CuttingKnifeItem(Tiers.DIAMOND,5.0F, -3.0F, basicItem()));

    public static final RegistryObject<Item> STONE_ROCK_GRINDER = registerWithTab("stone_rock_grinder",()->new RockGrinderItem(Tiers.STONE,1, -2.8F, basicItem()));
    public static final RegistryObject<Item> IRON_ROCK_GRINDER = registerWithTab("iron_rock_grinder",()->new RockGrinderItem(Tiers.IRON,1, -2.8F, basicItem()));
    public static final RegistryObject<Item> DIAMOND_ROCK_GRINDER = registerWithTab("diamond_rock_grinder",()->new RockGrinderItem(Tiers.DIAMOND,1, -2.8F, basicItem()));

    public static final RegistryObject<Item> WATER_EXTRACTOR = registerWithTab("water_extractor",()->new WaterExtractorItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SURVIVOR_FISHING_ROD = registerWithTab("survivor_fishing_rod",()->new SurvivorFishingRodItem(basicItem()));
}
