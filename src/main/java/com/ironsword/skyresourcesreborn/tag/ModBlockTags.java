package com.ironsword.skyresourcesreborn.tag;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> MINEABLE_WITH_ROCK_GRINDER = modBlockTag("mineable_with_rock_grinder");

    public static final TagKey<Block> TEST_BLOCK_TAG = modBlockTag("test_block_tag");

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(SkyResourcesReborn.MODID, path));
    }
}
