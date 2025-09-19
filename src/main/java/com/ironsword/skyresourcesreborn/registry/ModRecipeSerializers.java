package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.recipe.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SkyResourcesReborn.MODID);

    public static final RegistryObject<RecipeSerializer<CuttingRecipe>> CUTTING = SERIALIZERS.register("cutting", CuttingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<GrindingRecipe>> GRINDING = SERIALIZERS.register("grinding", GrindingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<ExtractingRecipe>> EXTRACTING = SERIALIZERS.register("extracting", ExtractingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<InsertingRecipe>> INSERTING = SERIALIZERS.register("inserting",InsertingRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<HeatingRecipe>> HEATING = SERIALIZERS.register("heating",HeatingRecipe.Serializer::new);
}
