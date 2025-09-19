package com.ironsword.skyresourcesreborn.data;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.loot.CuttingKnifeLootModifier;
import com.ironsword.skyresourcesreborn.loot.RockGrinderLootModifier;
import com.ironsword.skyresourcesreborn.tag.ModItemTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class GenLootModifier extends GlobalLootModifierProvider {
    public GenLootModifier(PackOutput output) {
        super(output, SkyResourcesReborn.MODID);
    }

    @Override
    protected void start() {
        add("cutting",new CuttingKnifeLootModifier(
                new LootItemCondition[]{
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItemTags.CUTTING_KNIFE)).build()
            }
        ));
        add("grinding",new RockGrinderLootModifier(
                new LootItemCondition[] {
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItemTags.ROCK_GRINDER)).build()
                }
        ));
    }
}
