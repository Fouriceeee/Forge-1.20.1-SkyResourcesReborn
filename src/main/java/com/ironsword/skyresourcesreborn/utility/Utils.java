package com.ironsword.skyresourcesreborn.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.openjdk.nashorn.internal.ir.debug.JSONWriter;

public class Utils {
    public static JsonElement serializeItemStack(ItemStack stack) {
        JsonObject object = new JsonObject();
        object.addProperty("item",ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
        if (stack.getCount() > 1) {
            object.addProperty("count",stack.getCount());
        }
        if (stack.hasTag()) {
            object.addProperty("nbt",stack.getTag().toString());
        }
        return object;
    }

    public static FluidStack deserializeFluidStack(JsonObject pJson) {
        ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(pJson,"fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);
        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid '" + id + "'");
        }
        if (fluid == Fluids.EMPTY) {
            throw new JsonSyntaxException("Invalid empty fluid '" + id + "'");
        }
        int amount = GsonHelper.getAsInt(pJson,"amount");
        FluidStack fluidStack = new FluidStack(fluid,amount);

        if (pJson.has("nbt")) {
            try{
                JsonElement element = pJson.get("nbt");
                fluidStack.setTag(TagParser.parseTag(
                        element.isJsonObject() ? SkyResourcesReborn.GSON.toJson(element) : GsonHelper.convertToString(element,"nbt")));
            }catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        }

        return fluidStack;
    }

    public static JsonElement serializeFluidStack(FluidStack stack) {
        JsonObject object = new JsonObject();
        object.addProperty("fluid",ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString());
        object.addProperty("amount",stack.getAmount());
        if (stack.hasTag()) {
            object.addProperty("nbt",stack.getTag().toString());
        }
        return object;
    }
}
