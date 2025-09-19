package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import com.ironsword.skyresourcesreborn.utility.Utils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class InsertingRecipeBuilder {
    private final Ingredient inputBlock;
    private final FluidStack fluid;
    private final ItemStack resultBlock;

    public InsertingRecipeBuilder(Ingredient inputBlock, FluidStack fluid, ItemStack resultBlock) {
        this.inputBlock = inputBlock;
        this.fluid = fluid;
        this.resultBlock = resultBlock;
    }

    public static InsertingRecipeBuilder recipe(Block resultBlock, Block inputBlock, Fluid fluid, int amount) {
        return new InsertingRecipeBuilder(Ingredient.of(inputBlock),new FluidStack(fluid,amount),new ItemStack(resultBlock));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(inputBlock.getItems()[0].getItem());
        build(consumerIn, SkyResourcesReborn.MODID + ":inserting/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(inputBlock.getItems()[0].getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Inserting Recipe " + save + " should remove its 'save' argument");
        } else {
            consumerIn.accept(new InsertingRecipeBuilder.Result(new ResourceLocation(save),inputBlock,fluid,resultBlock));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient inputBlock;
        private final FluidStack fluid;
        private final ItemStack resultBlock;

        public Result(ResourceLocation id, Ingredient inputBlock, FluidStack fluid, ItemStack resultBlock) {
            this.id = id;
            this.inputBlock = inputBlock;
            this.fluid = fluid;
            this.resultBlock = resultBlock;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input_block",inputBlock.toJson());
            pJson.add("fluid", Utils.serializeFluidStack(fluid));
            pJson.add("result_block",Utils.serializeItemStack(resultBlock));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.INSERTING.get();
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
