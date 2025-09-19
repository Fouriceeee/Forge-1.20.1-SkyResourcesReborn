package com.ironsword.skyresourcesreborn.data;

import com.google.common.collect.Sets;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class GenItemModels extends ItemModelProvider {
    public static final String GENERATED = "item/generated";

    public GenItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SkyResourcesReborn.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> handheldItems = Sets.newHashSet(
                ModItems.CACTUS_CUTTING_KNIFE.get(),
                ModItems.STONE_CUTTING_KNIFE.get(),
                ModItems.IRON_CUTTING_KNIFE.get(),
                ModItems.DIAMOND_CUTTING_KNIFE.get(),
                ModItems.STONE_ROCK_GRINDER.get(),
                ModItems.IRON_ROCK_GRINDER.get(),
                ModItems.DIAMOND_ROCK_GRINDER.get(),
                ModItems.WATER_EXTRACTOR.get()
        );
        handheldItems.forEach(this::itemHandheldModel);

        Set<Item> simpleItems = Sets.newHashSet(
                ModItems.DARK_MATTER.get(),
                ModItems.LIGHT_MATTER.get(),
                ModItems.PLANT_MATTER.get(),
                ModItems.ALCHCOAL.get(),
                ModItems.ALCHDIAMOND.get(),
                ModItems.ALCHIRON_INGOT.get(),
                ModItems.ALCHGOLD_INGOT.get(),
                ModItems.ALCHGOLD_NEEDLE.get(),
                ModItems.FROZEN_IRON_INGOT.get(),
                ModItems.CRYSTAL_SHARD.get(),
                ModItems.SAWDUST.get(),
                ModItems.CRUSHED_STONE.get(),
                ModItems.HEAVY_SNOWBALL.get(),
                ModItems.HEAVY_EXPLOSIVE_SNOWBALL.get(),
                ModItems.CACTUS_FRUIT.get(),
                ModItems.CACTUS_NEEDLE.get(),
                ModItems.BLOODY_CACTUS_NEEDLE.get()
        );

        simpleItems.forEach(this::itemGeneratedModel);
    }

    private void itemGeneratedModel(Item item) {
        withExistingParent(itemName(item), "item/generated").texture("layer0", resourceItem(ForgeRegistries.ITEMS.getKey(item).getPath()));
    }
    private void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), "item/generated").texture("layer0", texture);
    }

    private void itemHandheldModel(Item item) {
        withExistingParent(itemName(item), "item/handheld").texture("layer0", resourceItem(ForgeRegistries.ITEMS.getKey(item).getPath()));
    }
    private void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), "item/handheld").texture("layer0", texture);
    }

    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    private ResourceLocation resourceItem(String path) {
        return new ResourceLocation(SkyResourcesReborn.MODID, "item/" + path);
    }

}
