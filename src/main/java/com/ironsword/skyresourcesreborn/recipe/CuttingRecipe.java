package com.ironsword.skyresourcesreborn.recipe;

import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
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
import org.jetbrains.annotations.Nullable;

public class CuttingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient block;
    private final ItemStack output;

    public CuttingRecipe(ResourceLocation id, Ingredient block, ItemStack output) {
        this.id = id;
        this.block = block;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) { return false; }

        return block.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public Ingredient getBlock() {
        return block;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CUTTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CUTTING.get();
    }

    public static class Serializer implements RecipeSerializer<CuttingRecipe> {

        @Override
        public CuttingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient block = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(pJson,"block"),false);
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson,"output"),true);

            return new CuttingRecipe(pRecipeId,block,output);
        }

        @Override
        public @Nullable CuttingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient block = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();

            return new CuttingRecipe(pRecipeId,block,output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingRecipe pRecipe) {
            pRecipe.block.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
