package com.ironsword.skyresourcesreborn.block.crucible;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class HeatSource {
    public static final Object2IntMap<BlockState> HEAT_SOURCES = new Object2IntOpenHashMap<>();

    public static void init() {
        HEAT_SOURCES.put(Blocks.TORCH.defaultBlockState(),1);
        HEAT_SOURCES.put(Blocks.OBSIDIAN.defaultBlockState(),3);
        HEAT_SOURCES.put(Blocks.LAVA.defaultBlockState(),6);
        HEAT_SOURCES.put(Blocks.FIRE.defaultBlockState(),8);
        HEAT_SOURCES.put(Blocks.MAGMA_BLOCK.defaultBlockState(),9);
    }
}
