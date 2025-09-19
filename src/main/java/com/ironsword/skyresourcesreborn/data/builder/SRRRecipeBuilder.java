package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.recipe.ItemStackWithMultiplier;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SRRRecipeBuilder {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final List<FluidStack> inputFluids = new ArrayList<>();
    private final List<ItemStackWithMultiplier> results = new ArrayList<>();
    private final List<FluidStack> outputFluids = new ArrayList<>();

    public SRRRecipeBuilder() {}

    public SRRRecipeBuilder ingredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public SRRRecipeBuilder inputFluid(FluidStack stack) {
        inputFluids.add(stack);
        return this;
    }

    public SRRRecipeBuilder result(ItemStackWithMultiplier itemStack) {
        results.add(itemStack);
        return this;
    }

    public SRRRecipeBuilder outputFluid(FluidStack stack) {
        outputFluids.add(stack);
        return this;
    }

    public static abstract class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> ingredients = new ArrayList<>();
        private final List<FluidStack> inputFluids = new ArrayList<>();
        private final List<ItemStackWithMultiplier> results = new ArrayList<>();
        private final List<FluidStack> outputFluids = new ArrayList<>();

        protected Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {

        }



        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
