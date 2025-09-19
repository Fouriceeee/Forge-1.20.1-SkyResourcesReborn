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

public class InsertingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient inputBlock;
    private final FluidStack fluid;
    private final ItemStack resultBlock;

    public InsertingRecipe(ResourceLocation id, Ingredient inputBlock, FluidStack fluid, ItemStack resultBlock) {
        this.id = id;
        this.inputBlock = inputBlock;
        this.fluid = fluid;
        this.resultBlock = resultBlock;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) { return false; }

        return inputBlock.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return resultBlock.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public Ingredient getInputBlock() {
        return inputBlock;
    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return resultBlock.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.INSERTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.INSERTING.get();
    }

    public static class Serializer implements RecipeSerializer<InsertingRecipe> {

        @Override
        public InsertingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient inputBlock = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(pJson,"input_block"),false);
            FluidStack fluid = Utils.deserializeFluidStack(GsonHelper.getAsJsonObject(pJson,"fluid"));
            ItemStack resultBlock = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson,"result_block"),true);

            return new InsertingRecipe(pRecipeId,inputBlock,fluid,resultBlock);
        }

        @Override
        public @Nullable InsertingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient inputBlock = Ingredient.fromNetwork(pBuffer);
            FluidStack fluid = FluidStack.readFromPacket(pBuffer);
            ItemStack resultBlock = pBuffer.readItem();

            return new InsertingRecipe(pRecipeId,inputBlock,fluid,resultBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, InsertingRecipe pRecipe) {
            pRecipe.inputBlock.toNetwork(pBuffer);
            pRecipe.fluid.writeToPacket(pBuffer);
            pBuffer.writeItem(pRecipe.resultBlock);
        }
    }
}
