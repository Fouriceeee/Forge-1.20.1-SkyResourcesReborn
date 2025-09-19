package com.ironsword.skyresourcesreborn.compat.jei.category;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.compat.jei.JEIPlugin;
import com.ironsword.skyresourcesreborn.recipe.InsertingRecipe;
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

public class InsertingRecipeCategory extends AbstractRecipeCategory<InsertingRecipe> {
    public static final int width = 114;//514
    public static final int height = 34;

    public static final RecipeType<InsertingRecipe> TYPE = RecipeType.create(SkyResourcesReborn.MODID,"inserting", InsertingRecipe.class);

    private final IDrawable fluidBackground;
    private final IDrawable fluidOverlay;
    private final IDrawable plusSign;

    public InsertingRecipeCategory(IGuiHelper helper) {
        super(
                TYPE,
                Component.translatable("skyresourcesreborn.jei.inserting"),
                helper.createDrawableItemLike(ModItems.WATER_EXTRACTOR.get()),
                width,
                height);
        fluidBackground = helper.createDrawable(JEIPlugin.ICONS,0,0,18,34);
        fluidOverlay = helper.createDrawable(JEIPlugin.ICONS,18,0,18,34);
        plusSign = helper.createDrawable(JEIPlugin.ICONS,0,34,9,9);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder pBuilder, InsertingRecipe pRecipe, IFocusGroup iFocusGroup) {
        pBuilder.addInputSlot(1,1)
                .setFluidRenderer(100,false,16,32)
                .setBackground(fluidBackground,-1,-1)
                .setOverlay(fluidOverlay,-1,-1)
                .addIngredient(ForgeTypes.FLUID_STACK, pRecipe.getFluid());

        pBuilder.addInputSlot(33,9)
                .setStandardSlotBackground()
                .addIngredients(pRecipe.getInputBlock());

        pBuilder.addOutputSlot(93,9)
                .setOutputSlotBackground()
                .addItemStack(pRecipe.getResultItem(null));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, InsertingRecipe recipe, IFocusGroup focuses) {
        builder.addDrawable(plusSign).setPosition(20,12);
        builder.addRecipeArrow().setPosition(58,9);
    }

    public static void addCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TYPE,
                ModItems.WATER_EXTRACTOR.get()
        );
    }
}
