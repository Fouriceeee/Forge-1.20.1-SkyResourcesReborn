package com.ironsword.skyresourcesreborn.compat.jei.category;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.GrindingRecipe;
import com.ironsword.skyresourcesreborn.recipe.ItemStackWithMultiplier;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GrindingRecipeCategory implements IRecipeCategory<GrindingRecipe> {
    public static final RecipeType<GrindingRecipe> TYPE = RecipeType.create(SkyResourcesReborn.MODID,"grinding", GrindingRecipe.class);

    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public GrindingRecipeCategory(IGuiHelper helper) {
        title =Component.translatable("skyresourcesreborn.jei.grinding");
        ResourceLocation backgroundImage = new ResourceLocation(SkyResourcesReborn.MODID,"textures/gui/jei/grinding_recipe.png");
        this.background = helper.createDrawable(backgroundImage,0,0,176,80);
        this.icon = helper.createDrawableItemLike(ModItems.IRON_ROCK_GRINDER.get());
    }


    @Override
    public RecipeType<GrindingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GrindingRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addInputSlot(80, 12)
                .addIngredients(recipe.getBlock());
        int size = recipe.getOutputs().size();
        int i = 0;
        for (ItemStackWithMultiplier item : recipe.getOutputs()) {
            builder.addOutputSlot(8+18*i,56)
                    .addItemStack(item.getItemStack())
                    .addRichTooltipCallback(((iRecipeSlotView, iTooltipBuilder) -> {
                        iTooltipBuilder.add(Component.literal( String.format("%.2f%%",100*item.getMultiplier())));
                    }));
            i++;
        }

    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, GrindingRecipe recipe, IFocusGroup focuses) {
    }

    public static void addCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TYPE,
                ModItems.STONE_ROCK_GRINDER.get(),
                ModItems.IRON_ROCK_GRINDER.get(),
                ModItems.DIAMOND_ROCK_GRINDER.get()
        );
    }
}
