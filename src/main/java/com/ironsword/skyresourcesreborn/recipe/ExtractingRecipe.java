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

public class ExtractingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient block;
    private final FluidStack outputFluid;
    private final ItemStack resultBlock;

    public ExtractingRecipe(ResourceLocation id, Ingredient block, FluidStack outputFluid, ItemStack resultBlock) {
        this.id = id;
        this.block = block;
        this.outputFluid = outputFluid;
        this.resultBlock = resultBlock;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) { return false; }

        return block.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public Ingredient getBlock() {
        return block;
    }

    public FluidStack getOutputFluid() {
        return outputFluid.copy();
    }

    public ItemStack getResultBlock() {
        return resultBlock;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EXTRACTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.EXTRACTING.get();
    }

    public static class Serializer implements RecipeSerializer<ExtractingRecipe> {

        @Override
        public ExtractingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient block = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(pJson,"block"),false);
            FluidStack output = Utils.deserializeFluidStack(GsonHelper.getAsJsonObject(pJson,"fluid"));
            ItemStack resultBlock = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson,"result_block"),true);

            return new ExtractingRecipe(pRecipeId,block,output,resultBlock);
        }

        @Override
        public @Nullable ExtractingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient block = Ingredient.fromNetwork(pBuffer);
            FluidStack output = FluidStack.readFromPacket(pBuffer);
            ItemStack resultBlock =pBuffer.readItem();

            return new ExtractingRecipe(pRecipeId,block,output,resultBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ExtractingRecipe pRecipe) {
            pRecipe.block.toNetwork(pBuffer);
            pRecipe.outputFluid.writeToPacket(pBuffer);
            pBuffer.writeItem(pRecipe.resultBlock);
        }
    }
}
