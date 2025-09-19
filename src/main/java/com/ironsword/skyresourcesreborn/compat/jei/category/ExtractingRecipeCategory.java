package com.ironsword.skyresourcesreborn.compat.jei.category;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.compat.jei.JEIPlugin;
import com.ironsword.skyresourcesreborn.recipe.ExtractingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ExtractingRecipeCategory extends AbstractRecipeCategory<ExtractingRecipe> {
    public static final int width = 110;
    public static final int height = 34;

    public static final RecipeType<ExtractingRecipe> TYPE = RecipeType.create(SkyResourcesReborn.MODID,"extracting", ExtractingRecipe.class);

    private final IDrawable fluidBackground;
    private final IDrawable fluidOverlay;
    public ExtractingRecipeCategory(IGuiHelper helper) {
        super(
                TYPE,
                Component.translatable("skyresourcesreborn.jei.extracting"),
                helper.createDrawableItemLike(ModItems.WATER_EXTRACTOR.get()),
                width,
                height
        );
        fluidBackground = helper.createDrawable(JEIPlugin.ICONS,0,0,18,34);
        fluidOverlay = helper.createDrawable(JEIPlugin.ICONS,18,0,18,34);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder pBuilder, ExtractingRecipe pRecipe, IFocusGroup iFocusGroup) {
        pBuilder.addInputSlot(1,9)
                .setStandardSlotBackground()
                .addIngredients(pRecipe.getBlock());

        pBuilder.addOutputSlot(61,1)
                .setFluidRenderer(100,false,16,32)
                .setBackground(fluidBackground,-1,-1)
                .setOverlay(fluidOverlay,-1,-1)
                .addIngredient(ForgeTypes.FLUID_STACK, pRecipe.getOutputFluid());

        pBuilder.addOutputSlot(88,9)
                .setOutputSlotBackground()
                .addItemStack(pRecipe.getResultBlock());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ExtractingRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(26, 9);
    }

    @Override
    public boolean isHandled(ExtractingRecipe recipe) {
        return !recipe.isSpecial();
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(ExtractingRecipe recipe) {
        return recipe.getId();
    }

    public static void addCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TYPE,
                ModItems.WATER_EXTRACTOR.get()
        );
    }
}
