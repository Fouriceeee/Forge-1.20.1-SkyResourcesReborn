package com.ironsword.skyresourcesreborn.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.ItemStackWithMultiplier;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GrindingRecipeBuilder {
    private final Ingredient block;
    private final List<ItemStackWithMultiplier> outputs = new ArrayList<>();

    public GrindingRecipeBuilder(Ingredient block,List<ItemStackWithMultiplier> outputs) {
        this.block = block;
        this.outputs.addAll(outputs);
    }

    public GrindingRecipeBuilder(Ingredient block, ItemStackWithMultiplier itemStackC) {
        this.block = block;
        this.outputs.add(itemStackC);
    }

    public GrindingRecipeBuilder(Ingredient block) {
        this.block = block;
    }

    public static GrindingRecipeBuilder mainOutputRecipe(ItemLike mainItem,float multiplier, ItemLike block) {
        return new GrindingRecipeBuilder(Ingredient.of(block),new ItemStackWithMultiplier(mainItem,multiplier));
    }

    public static GrindingRecipeBuilder mainOutputRecipe(ItemLike mainItem, float multiplier, TagKey<Item> tag) {
        return new GrindingRecipeBuilder(Ingredient.of(tag),new ItemStackWithMultiplier(mainItem,multiplier));
    }

    public static GrindingRecipeBuilder recipe(ItemLike block) {
        return new GrindingRecipeBuilder(Ingredient.of(block));
    }

    public static GrindingRecipeBuilder recipe(TagKey<Item> tag) {
        return new GrindingRecipeBuilder(Ingredient.of(tag));
    }

    public GrindingRecipeBuilder addOutput(ItemLike item, float multiplier) {
        outputs.add(new ItemStackWithMultiplier(item,multiplier));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(block.getItems()[0].getItem());
        build(consumerIn, SkyResourcesReborn.MODID + ":grinding/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(block.getItems()[0].getItem());

        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Grinding Recipe " + save + " should remove its 'save' argument");
        } else {
            consumerIn.accept(new GrindingRecipeBuilder.Result(new ResourceLocation(save),block,outputs));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient block;
        private final List<ItemStackWithMultiplier> outputs;

        public Result(ResourceLocation id, Ingredient block, List<ItemStackWithMultiplier> outputs) {
            this.id = id;
            this.block = block;
            this.outputs = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("block", block.toJson());

            JsonArray arrayOutputs = new JsonArray();
            for (ItemStackWithMultiplier itemStackM : outputs) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item",ForgeRegistries.ITEMS.getKey(itemStackM.getItemStack().getItem()).toString());
                if (itemStackM.getMultiplier() != 1) {
                    jsonObject.addProperty("multiplier",itemStackM.getMultiplier());
                }
                arrayOutputs.add(jsonObject);
            }
            pJson.add("outputs", arrayOutputs);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.GRINDING.get();
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
