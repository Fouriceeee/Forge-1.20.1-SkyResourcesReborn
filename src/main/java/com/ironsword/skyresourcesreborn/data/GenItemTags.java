package com.ironsword.skyresourcesreborn.data;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import com.ironsword.skyresourcesreborn.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GenItemTags extends ItemTagsProvider {
    public GenItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output,provider,blockTagProvider, SkyResourcesReborn.MODID,existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        registerModTags();
    }

    private void registerModTags() {
        tag(ModItemTags.CUTTING_KNIFE)
                .add(ModItems.STONE_CUTTING_KNIFE.get())
                .add(ModItems.IRON_CUTTING_KNIFE.get())
                .add(ModItems.DIAMOND_CUTTING_KNIFE.get());
        tag(ModItemTags.ROCK_GRINDER)
                .add(ModItems.STONE_ROCK_GRINDER.get())
                .add(ModItems.IRON_ROCK_GRINDER.get())
                .add(ModItems.DIAMOND_ROCK_GRINDER.get());
    }
}
