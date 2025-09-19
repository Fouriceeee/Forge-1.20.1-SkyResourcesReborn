package com.ironsword.skyresourcesreborn.compat.jei;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.compat.jei.category.*;
import com.ironsword.skyresourcesreborn.recipe.HeatingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(SkyResourcesReborn.MODID,"jei_plugin");
    public static final ResourceLocation ICONS = new ResourceLocation(SkyResourcesReborn.MODID,"textures/gui/icons.png");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CuttingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new GrindingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ExtractingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new InsertingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HeatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(ExtractingRecipeCategory.TYPE,manager.getAllRecipesFor(ModRecipeTypes.EXTRACTING.get()));
        registration.addRecipes(CuttingRecipeCategory.TYPE,manager.getAllRecipesFor(ModRecipeTypes.CUTTING.get()));
        registration.addRecipes(GrindingRecipeCategory.TYPE,manager.getAllRecipesFor(ModRecipeTypes.GRINDING.get()));
        registration.addRecipes(InsertingRecipeCategory.TYPE,manager.getAllRecipesFor(ModRecipeTypes.INSERTING.get()));
        registration.addRecipes(HeatingRecipeCategory.TYPE,manager.getAllRecipesFor(ModRecipeTypes.HEATING.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        CuttingRecipeCategory.addCatalysts(registration);
        GrindingRecipeCategory.addCatalysts(registration);
        ExtractingRecipeCategory.addCatalysts(registration);
        InsertingRecipeCategory.addCatalysts(registration);
        HeatingRecipeCategory.addCatalysts(registration);
    }
}
