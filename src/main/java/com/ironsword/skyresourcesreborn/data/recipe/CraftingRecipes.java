package com.ironsword.skyresourcesreborn.data.recipe;

import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class CraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        shapedRecipes(consumer);
        shapelessRecipes(consumer);
    }

    private static void shapedRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_ROCK_GRINDER.get())
                .pattern("#  ")
                .pattern(" # ")
                .pattern("  X")
                .define('#', Items.COBBLESTONE)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.COBBLESTONE),has(Items.COBBLESTONE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_ROCK_GRINDER.get())
                .pattern("#  ")
                .pattern(" # ")
                .pattern("  X")
                .define('#', Items.IRON_INGOT)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_ROCK_GRINDER.get())
                .pattern("#  ")
                .pattern(" # ")
                .pattern("  X")
                .define('#', Items.DIAMOND)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND),has(Items.DIAMOND))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_CUTTING_KNIFE.get())
                .pattern("#  ")
                .pattern("#X ")
                .pattern(" #X")
                .define('#', Items.COBBLESTONE)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.COBBLESTONE),has(Items.COBBLESTONE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_CUTTING_KNIFE.get())
                .pattern("#  ")
                .pattern("#X ")
                .pattern(" #X")
                .define('#', Items.IRON_INGOT)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_CUTTING_KNIFE.get())
                .pattern("#  ")
                .pattern("#X ")
                .pattern(" #X")
                .define('#', Items.DIAMOND)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND),has(Items.DIAMOND))
                .save(consumer);
    }

    private static void shapelessRecipes(Consumer<FinishedRecipe> consumer) {
    }

    private static String getHasName(ItemLike pItemLike) {
        return "has_" + getItemName(pItemLike);
    }

    private static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
