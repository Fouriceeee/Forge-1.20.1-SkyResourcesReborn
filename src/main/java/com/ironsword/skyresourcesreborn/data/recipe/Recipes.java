package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.recipe.HeatingRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        CuttingRecipes.register(pWriter);
        GrindingRecipes.register(pWriter);
        CraftingRecipes.register(pWriter);
        WaterExtractorRecipes.register(pWriter);

        HeatingRecipes.register(pWriter);
    }
}
