package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CuttingRecipeBuilder {
    private final Ingredient block;
    private final ItemStack output;

    public CuttingRecipeBuilder(Ingredient block, ItemStack output) {
        this.block = block;
        this.output = output;
    }

    public static CuttingRecipeBuilder recipe(ItemLike item, int count, ItemLike block) {
        return new CuttingRecipeBuilder(Ingredient.of(block),new ItemStack(item,count));
    }

    public static CuttingRecipeBuilder recipe(ItemLike item, int count, TagKey<Item> tag) {
        return new CuttingRecipeBuilder(Ingredient.of(tag),new ItemStack(item,count));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(output.getItem());
        build(consumerIn, SkyResourcesReborn.MODID + ":cutting/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(output.getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Cutting Recipe " + save + " should remove its 'save' argument");
        } else {
            consumerIn.accept(new CuttingRecipeBuilder.Result(new ResourceLocation(save),block,output));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient block;
        private final ItemStack output;

        public Result(ResourceLocation id, Ingredient block, ItemStack output) {
            this.id = id;
            this.block = block;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("block", block.toJson());

            JsonObject objectOutput = new JsonObject();
            objectOutput.addProperty("item", ForgeRegistries.ITEMS.getKey(output.getItem()).toString());
            if (output.getCount() > 1) {
                objectOutput.addProperty("count", output.getCount());
            }
            pJson.add("output", objectOutput);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.CUTTING.get();
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
