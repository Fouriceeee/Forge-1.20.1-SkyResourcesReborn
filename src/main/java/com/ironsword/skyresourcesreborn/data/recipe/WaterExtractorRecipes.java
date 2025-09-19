package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.data.builder.ExtractingRecipeBuilder;
import com.ironsword.skyresourcesreborn.data.builder.InsertingRecipeBuilder;
import com.ironsword.skyresourcesreborn.registry.ModBlocks;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

public class WaterExtractorRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        extractingRecipes(consumer);
        insertingRecipes(consumer);
    }

    private static void extractingRecipes(Consumer<FinishedRecipe> consumer) {
        ExtractingRecipeBuilder.recipe(Blocks.SNOW_BLOCK,Fluids.WATER,50).build(consumer);
        ExtractingRecipeBuilder.recipe(ItemTags.LEAVES,Fluids.WATER,20).build(consumer,ItemTags.LEAVES);
        ExtractingRecipeBuilder.recipe(Blocks.CACTUS,Fluids.WATER,50,ModBlocks.DRY_CACTUS.get()).build(consumer);

    }

    private static void insertingRecipes(Consumer<FinishedRecipe> consumer) {
        InsertingRecipeBuilder.recipe(Blocks.CLAY,Blocks.DIRT,Fluids.WATER,200).build(consumer);
        InsertingRecipeBuilder.recipe(Blocks.CACTUS,ModBlocks.DRY_CACTUS.get(),Fluids.WATER,1200).build(consumer);
    }
}
