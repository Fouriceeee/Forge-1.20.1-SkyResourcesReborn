package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.data.builder.CuttingRecipeBuilder;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CartographyTableBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        List<Block> planksList = List.of(
                Blocks.OAK_PLANKS,
                Blocks.BIRCH_PLANKS,
                Blocks.SPRUCE_PLANKS,
                Blocks.JUNGLE_PLANKS,
                Blocks.ACACIA_PLANKS,
                Blocks.DARK_OAK_PLANKS,
                Blocks.CHERRY_PLANKS,
                Blocks.MANGROVE_PLANKS,
                Blocks.CRIMSON_PLANKS,
                Blocks.WARPED_PLANKS,
                Blocks.BAMBOO_PLANKS
        );

        List<Block> logList = List.of(
                Blocks.OAK_LOG,
                Blocks.BIRCH_LOG,
                Blocks.SPRUCE_LOG,
                Blocks.JUNGLE_LOG,
                Blocks.ACACIA_LOG,
                Blocks.DARK_OAK_LOG,
                Blocks.CHERRY_LOG,
                Blocks.MANGROVE_LOG,
                Blocks.CRIMSON_STEM,
                Blocks.WARPED_STEM,
                Blocks.BAMBOO_BLOCK
        );

        List<TagKey<Item>> logTagList = List.of(
                ItemTags.OAK_LOGS,
                ItemTags.BIRCH_LOGS,
                ItemTags.SPRUCE_LOGS,
                ItemTags.JUNGLE_LOGS,
                ItemTags.ACACIA_LOGS,
                ItemTags.DARK_OAK_LOGS,
                ItemTags.CHERRY_LOGS,
                ItemTags.MANGROVE_LOGS,
                ItemTags.CRIMSON_STEMS,
                ItemTags.WARPED_STEMS,
                ItemTags.BAMBOO_BLOCKS
        );

        for (int i = 0;i<planksList.size();i++) {
            CuttingRecipeBuilder.recipe(planksList.get(i),6,logTagList.get(i)).build(consumer);
        }

        CuttingRecipeBuilder.recipe(Items.STICK,6, ItemTags.PLANKS).build(consumer);
        CuttingRecipeBuilder.recipe(Items.MELON_SLICE,9,Blocks.MELON).build(consumer);
        CuttingRecipeBuilder.recipe(ModItems.CACTUS_FRUIT.get(), 2,Blocks.CACTUS).build(consumer);
    }
}
