package com.ironsword.skyresourcesreborn.compat.jei.category;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.CuttingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class CuttingRecipeCategory extends AbstractRecipeCategory<CuttingRecipe> {
    public static final int width = 82;
    public static final int height = 34;

    public static final RecipeType<CuttingRecipe> TYPE = RecipeType.create(SkyResourcesReborn.MODID,"cutting", CuttingRecipe.class);

    public CuttingRecipeCategory(IGuiHelper helper) {
        super(
                TYPE,
                Component.translatable("skyresourcesreborn.jei.cutting"),
                helper.createDrawableItemLike(ModItems.IRON_CUTTING_KNIFE.get()),
                width,
                height
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addInputSlot(1, 9)
                .setStandardSlotBackground()
                .addIngredients(recipe.getBlock());

        builder.addOutputSlot(61,  9)
                .setOutputSlotBackground()
                .addItemStack(recipe.getOutput());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CuttingRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(26, 9);
    }

    @Override
    public boolean isHandled(CuttingRecipe recipe) {
        return !recipe.isSpecial();
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(CuttingRecipe recipe) {
        return recipe.getId();
    }

    public static void addCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TYPE,
                ModItems.CACTUS_CUTTING_KNIFE.get(),
                ModItems.STONE_CUTTING_KNIFE.get(),
                ModItems.IRON_CUTTING_KNIFE.get(),
                ModItems.DIAMOND_CUTTING_KNIFE.get()
        );
    }
}
