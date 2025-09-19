package com.ironsword.skyresourcesreborn.tag;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class ModItemTags {
    public static final TagKey<Item> CUTTING_KNIFE = modItemTag("cutting_knife");
    public static final TagKey<Item> ROCK_GRINDER = modItemTag("rock_grinder");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(SkyResourcesReborn.MODID, path));
    }
}
