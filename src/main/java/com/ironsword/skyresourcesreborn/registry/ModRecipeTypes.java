package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SkyResourcesReborn.MODID);

    public static final RegistryObject<RecipeType<CuttingRecipe>> CUTTING = RECIPE_TYPES.register("cutting",()->registerRecipeType("cutting"));
    public static final RegistryObject<RecipeType<GrindingRecipe>> GRINDING = RECIPE_TYPES.register("grinding",()->registerRecipeType("grinding"));
    public static final RegistryObject<RecipeType<ExtractingRecipe>> EXTRACTING = RECIPE_TYPES.register("extracting",()->registerRecipeType("extracting"));
    public static final RegistryObject<RecipeType<InsertingRecipe>> INSERTING = RECIPE_TYPES.register("inserting",()->registerRecipeType("inserting"));

    public static final RegistryObject<RecipeType<HeatingRecipe>> HEATING = RECIPE_TYPES.register("heating",()->registerRecipeType("heating"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return SkyResourcesReborn.MODID + ":" + identifier;
            }
        };
    }
}
