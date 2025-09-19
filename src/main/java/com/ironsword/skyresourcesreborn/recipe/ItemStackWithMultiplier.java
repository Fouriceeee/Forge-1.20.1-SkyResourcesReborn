package com.ironsword.skyresourcesreborn.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Credits to the Create team for the implementation of results with chances!
 */
public class ItemStackWithMultiplier {
    public static final ItemStackWithMultiplier EMPTY = new ItemStackWithMultiplier(ItemStack.EMPTY,1);

    private final ItemStack itemStack;
    private final float multiplier;

    public ItemStackWithMultiplier(ItemStack itemStack, float multiplier) {
        this.itemStack = itemStack;
        this.itemStack.setCount(1);
        this.multiplier = multiplier;
    }

    public ItemStackWithMultiplier(ItemLike item, float multiplier) {
        this.itemStack = new ItemStack(item);
        this.multiplier = multiplier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public ItemStack rollOutput(RandomSource rand) {
        if (multiplier == 1.0F) {
            return itemStack.copy();
        } else {
            int outputAmount = (int) multiplier;
            //multiplier < 1.0F
            if (outputAmount == 0) {
                if (rand.nextFloat() < multiplier) {
                    return itemStack.copy();
                } else {
                    return ItemStack.EMPTY;
                }
            }

            //multiplier > 1.0F
            ItemStack output = itemStack.copy();
            output.setCount(outputAmount);
            if (rand.nextFloat() < multiplier - outputAmount) {
                output.grow(1);
            }
            return output;
        }
    }

    public JsonElement serialize() {
        JsonObject json = new JsonObject();

        json.addProperty("item",ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());

        if (itemStack.hasTag()) json.add("nbt",new JsonParser().parse(itemStack.getTag().toString()));
        if (multiplier != 1) json.addProperty("multiplier", multiplier);

        return json;
    }

    public static ItemStackWithMultiplier deserialize(JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) throw new JsonSyntaxException("Must be a json object");

        JsonObject json = jsonElement.getAsJsonObject();
        String id = GsonHelper.getAsString(json,"item");
        float chance = GsonHelper.getAsFloat(json,"multiplier",1.0F);

        ItemStack itemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id)));

        if (GsonHelper.isValidPrimitive(json, "nbt")) {
            try {
                JsonElement element = json.get("nbt");
                itemStack.setTag(TagParser.parseTag(
                        element.isJsonObject() ? SkyResourcesReborn.GSON.toJson(element) : GsonHelper.convertToString(element, "nbt")));
            }
            catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        }

        return new ItemStackWithMultiplier(itemStack,chance);
    }

    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeItem(itemStack);
        pBuffer.writeFloat(multiplier);
    }

    public static ItemStackWithMultiplier read(FriendlyByteBuf pBuffer) {
        return new ItemStackWithMultiplier(pBuffer.readItem(),pBuffer.readFloat());
    }
}
