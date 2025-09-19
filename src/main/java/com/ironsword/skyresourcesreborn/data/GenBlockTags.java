package com.ironsword.skyresourcesreborn.data;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModBlocks;
import com.ironsword.skyresourcesreborn.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GenBlockTags extends BlockTagsProvider {
    public GenBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SkyResourcesReborn.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModBlockTags.MINEABLE_WITH_ROCK_GRINDER)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.LOGS)
                .add(Blocks.GRAVEL);
        tag(ModBlockTags.TEST_BLOCK_TAG)
                .add(Blocks.TORCH)
                .add(Blocks.GLOWSTONE);
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CRUCIBLE.get());
    }
}
