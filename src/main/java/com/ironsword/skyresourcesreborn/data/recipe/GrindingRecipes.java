package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.data.builder.GrindingRecipeBuilder;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class GrindingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer){
        GrindingRecipeBuilder.mainOutputRecipe(Blocks.GRAVEL,1.0F,Blocks.COBBLESTONE).build(consumer);
        GrindingRecipeBuilder.mainOutputRecipe(Blocks.SAND,1.0F,Blocks.GRAVEL).addOutput(Items.FLINT,0.3F).build(consumer);
        GrindingRecipeBuilder.mainOutputRecipe(ModItems.CRUSHED_STONE.get(),0.44F,Blocks.STONE)
                .addOutput(Items.DIAMOND,0.033F)
                .addOutput(Items.EMERALD,0.015F)
                .addOutput(Items.LAPIS_LAZULI,0.54F)
                .build(consumer);
        GrindingRecipeBuilder.mainOutputRecipe(ModItems.SAWDUST.get(), 1.5F, ItemTags.LOGS).build(consumer,tagRecipeId(ItemTags.LOGS));

    }

    private static String tagRecipeId(TagKey<Item> tag) {
        return SkyResourcesReborn.MODID + ":grinding/" + tag.location().getPath();
    }
}
