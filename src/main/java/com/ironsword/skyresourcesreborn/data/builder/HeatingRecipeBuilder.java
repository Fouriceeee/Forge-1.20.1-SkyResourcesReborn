package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.HeatingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import com.ironsword.skyresourcesreborn.utility.Utils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HeatingRecipeBuilder {
    private final Ingredient ingredient;
    private final FluidStack result;
    private final int processingTime;

    public HeatingRecipeBuilder(Ingredient ingredient, FluidStack result, int processingTime) {
        this.ingredient = ingredient;
        this.result = result;
        this.processingTime = processingTime;
    }

    public static HeatingRecipeBuilder recipe(ItemLike item, Fluid fluid,int amount,int processingTime) {
        return new HeatingRecipeBuilder(Ingredient.of(item),new FluidStack(fluid,amount),processingTime);
    }

    public static HeatingRecipeBuilder recipe(TagKey<Item> tag, Fluid fluid, int amount, int processingTime) {
        return new HeatingRecipeBuilder(Ingredient.of(tag),new FluidStack(fluid,amount),processingTime);
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem());
        build(consumerIn, SkyResourcesReborn.MODID + ":heating/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn,TagKey<Item> tag) {
        consumerIn.accept(new HeatingRecipeBuilder.Result(new ResourceLocation(SkyResourcesReborn.MODID + ":heating/" + tag.location().getPath()), ingredient,result,processingTime));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Heating Recipe " + save + " should remove its 'save' argument");
        } else {
            consumerIn.accept(new HeatingRecipeBuilder.Result(new ResourceLocation(save),ingredient,result,processingTime));
        }
    }

    public static class Result implements FinishedRecipe{
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final FluidStack result;
        private final int processingTime;

        public Result(ResourceLocation id, Ingredient ingredient, FluidStack result, int processingTime) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.processingTime = processingTime;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient",ingredient.toJson());
            pJson.add("result", Utils.serializeFluidStack(result));
            pJson.addProperty("processing_time",processingTime);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.HEATING.get();
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
