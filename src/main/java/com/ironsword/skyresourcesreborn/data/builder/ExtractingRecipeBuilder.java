package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
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

public class ExtractingRecipeBuilder {
    private static final String PREFIX = SkyResourcesReborn.MODID + ":extracting/";
    private final Ingredient block;
    private final FluidStack output;
    private final ItemStack resultBlock;

    public ExtractingRecipeBuilder(Ingredient block, FluidStack output, ItemStack resultBlock) {
        this.block = block;
        this.output = output;
        this.resultBlock = resultBlock;
    }

    public static ExtractingRecipeBuilder recipe(ItemLike block, Fluid fluid,int amount) {
        return new ExtractingRecipeBuilder(Ingredient.of(block),new FluidStack(fluid,amount),ItemStack.EMPTY);
    }

    public static ExtractingRecipeBuilder recipe(TagKey<Item> tag,Fluid fluid, int amount) {
        return new ExtractingRecipeBuilder(Ingredient.of(tag),new FluidStack(fluid,amount),ItemStack.EMPTY);
    }

    public static ExtractingRecipeBuilder recipe(ItemLike block, Fluid fluid,int amount,ItemLike resultBlock) {
        return new ExtractingRecipeBuilder(Ingredient.of(block),new FluidStack(fluid,amount),new ItemStack(resultBlock));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(block.getItems()[0].getItem());
        build(consumerIn, PREFIX + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn,TagKey<Item> tag) {
        consumerIn.accept(new ExtractingRecipeBuilder.Result(new ResourceLocation(PREFIX + tag.location().getPath()), block, output,resultBlock));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(block.getItems()[0].getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Extracting Recipe " + save + " should remove its 'save' argument");
        } else {
            consumerIn.accept(new ExtractingRecipeBuilder.Result(new ResourceLocation(save),block,output,resultBlock));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient block;
        private final FluidStack output;
        private final ItemStack resultBlock;

        public Result(ResourceLocation id, Ingredient block, FluidStack output, ItemStack resultBlock) {
            this.id = id;
            this.block = block;
            this.output = output;
            this.resultBlock = resultBlock;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("block",block.toJson());
            pJson.add("fluid", Utils.serializeFluidStack(output));
            pJson.add("result_block",Utils.serializeItemStack(resultBlock));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.EXTRACTING.get();
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
