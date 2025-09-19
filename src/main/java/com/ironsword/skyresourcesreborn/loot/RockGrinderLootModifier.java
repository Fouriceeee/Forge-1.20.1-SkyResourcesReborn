package com.ironsword.skyresourcesreborn.loot;

import com.google.common.base.Suppliers;
import com.ironsword.skyresourcesreborn.recipe.CuttingRecipe;
import com.ironsword.skyresourcesreborn.recipe.GrindingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class RockGrinderLootModifier extends LootModifier {
    public static final Supplier<Codec<RockGrinderLootModifier>> CODEC =
            Suppliers.memoize(
                    ()->
                            RecordCodecBuilder.create(
                                    inst ->codecStart(inst).apply(inst,RockGrinderLootModifier::new)));

    public RockGrinderLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        @Nullable final BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        if (blockState == null) {
            return generatedLoot;
        }

        SimpleContainer inventory = new SimpleContainer(blockState.getBlock().asItem().getDefaultInstance());
        Optional<GrindingRecipe> pRecipe = context.getLevel().getRecipeManager().getRecipeFor(ModRecipeTypes.GRINDING.get(),inventory,context.getLevel());

        if (pRecipe.isEmpty()) {
            return generatedLoot;
        }

        List<ItemStack> outputs = pRecipe.get().rollOutputs(context.getLevel().random);
        generatedLoot = new ObjectArrayList<>(outputs);

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
