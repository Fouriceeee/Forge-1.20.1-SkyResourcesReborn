package com.ironsword.skyresourcesreborn.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ironsword.skyresourcesreborn.registry.ModRecipeSerializers;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GrindingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient block;
    private final NonNullList<ItemStackWithMultiplier> outputs;

    public GrindingRecipe(ResourceLocation id, Ingredient block, NonNullList<ItemStackWithMultiplier> outputs) {
        this.id = id;
        this.block = block;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) { return false; }

        return block.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return outputs.get(0).getItemStack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public Ingredient getBlock() {
        return block;
    }

    public NonNullList<ItemStackWithMultiplier> getOutputs() {
        return outputs;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputs.get(0).getItemStack().copy();
    }

    public List<ItemStack> rollOutputs(RandomSource rand) {
        List<ItemStack> outputs = new ArrayList<>();
        for (ItemStackWithMultiplier itemStackM : this.outputs) {
            ItemStack itemStack = itemStackM.rollOutput(rand);
            if (!itemStack.isEmpty()){
                outputs.add(itemStack);
            }
        }
        return outputs;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.GRINDING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.GRINDING.get();
    }

    public static class Serializer implements RecipeSerializer<GrindingRecipe> {

        @Override
        public GrindingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient block = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(pJson,"block"),false);
            NonNullList<ItemStackWithMultiplier> outputs = NonNullList.create();
            for (JsonElement jsonElement: GsonHelper.getAsJsonArray(pJson,"outputs")){
                outputs.add(ItemStackWithMultiplier.deserialize(jsonElement));
            }

            return new GrindingRecipe(pRecipeId,block,outputs);
        }

        @Override
        public @Nullable GrindingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient block = Ingredient.fromNetwork(pBuffer);
            int i = pBuffer.readVarInt();
            NonNullList<ItemStackWithMultiplier> outputs = NonNullList.withSize(i, ItemStackWithMultiplier.EMPTY);
            for (int j=0;j<outputs.size();++j) {
                outputs.set(j, ItemStackWithMultiplier.read(pBuffer));
            }

            return new GrindingRecipe(pRecipeId,block,outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GrindingRecipe pRecipe) {
            pRecipe.block.toNetwork(pBuffer);
            pBuffer.writeVarInt(pRecipe.outputs.size());
            for (ItemStackWithMultiplier output: pRecipe.outputs) {
                output.write(pBuffer);
            }
        }
    }
}
