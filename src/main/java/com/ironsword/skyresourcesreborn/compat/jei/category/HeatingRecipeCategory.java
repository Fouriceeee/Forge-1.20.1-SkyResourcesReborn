package com.ironsword.skyresourcesreborn.compat.jei.category;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.compat.jei.JEIPlugin;
import com.ironsword.skyresourcesreborn.recipe.HeatingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModBlocks;
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

public class HeatingRecipeCategory extends AbstractRecipeCategory<HeatingRecipe> {
    public static final int width = 110;
    public static final int height = 34;

    public static final RecipeType<HeatingRecipe> TYPE = RecipeType.create(SkyResourcesReborn.MODID,"heating",HeatingRecipe.class);

    private final IDrawable fluidBackground;
    private final IDrawable fluidOverlay;

    public HeatingRecipeCategory(IGuiHelper helper) {
        super(
                TYPE,
                Component.translatable(SkyResourcesReborn.MODID+".jei.heating"),
                helper.createDrawableItemLike(ModBlocks.CRUCIBLE.get()),
                width,
                height
        );
        fluidBackground = helper.createDrawable(JEIPlugin.ICONS,0,0,18,34);
        fluidOverlay = helper.createDrawable(JEIPlugin.ICONS,18,0,18,34);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder pBuilder, HeatingRecipe pRecipe, IFocusGroup iFocusGroup) {
        pBuilder.addInputSlot(1,9)
                .setStandardSlotBackground()
                .addIngredients(pRecipe.getIngredient());

        pBuilder.addOutputSlot(61,1)
                .setFluidRenderer(100,false,16,32)
                .setBackground(fluidBackground,-1,-1)
                .setOverlay(fluidOverlay,-1,-1)
                .addIngredient(ForgeTypes.FLUID_STACK, pRecipe.getResult());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, HeatingRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(26, 9);
    }

    public static void addCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TYPE,
                ModBlocks.CRUCIBLE.get()
        );
    }
}
