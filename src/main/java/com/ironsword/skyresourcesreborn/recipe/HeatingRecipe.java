package com.ironsword.skyresourcesreborn.recipe;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import com.ironsword.skyresourcesreborn.utility.Utils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class HeatingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final FluidStack result;
    private final int processingTime;

    public HeatingRecipe(ResourceLocation id, Ingredient ingredient, FluidStack result, int processingTime) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.processingTime = processingTime;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public FluidStack getResult() {
        return result;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;

        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.HEATING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.HEATING.get();
    }

    public static class Serializer implements RecipeSerializer<HeatingRecipe> {

        @Override
        public HeatingRecipe fromJson(ResourceLocation pId, JsonObject pJson) {
            Ingredient ingredient = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(pJson,"ingredient"),false);
            FluidStack result = Utils.deserializeFluidStack(GsonHelper.getAsJsonObject(pJson,"result"));
            int processingTime = GsonHelper.getAsInt(pJson,"processing_time");

            return new HeatingRecipe(pId,ingredient,result,processingTime);
        }

        @Override
        public @Nullable HeatingRecipe fromNetwork(ResourceLocation pId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            FluidStack result = FluidStack.readFromPacket(pBuffer);
            int processingTime = pBuffer.readVarInt();

            return new HeatingRecipe(pId,ingredient,result,processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HeatingRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pRecipe.result.writeToPacket(pBuffer);
            pBuffer.writeVarInt(pRecipe.processingTime);
        }
    }
}
