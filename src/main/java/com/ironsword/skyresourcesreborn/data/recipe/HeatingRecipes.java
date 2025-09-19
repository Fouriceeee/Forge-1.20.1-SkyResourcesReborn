package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.data.builder.HeatingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

public class HeatingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        HeatingRecipeBuilder.recipe(ItemTags.STONE_TOOL_MATERIALS, Fluids.LAVA,1000,20).build(consumer,ItemTags.STONE_TOOL_MATERIALS);
        HeatingRecipeBuilder.recipe(Blocks.ICE,Fluids.WATER,1000,20).build(consumer);
    }
}
