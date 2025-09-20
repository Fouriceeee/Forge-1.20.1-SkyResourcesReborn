package com.ironsword.skyresourcesreborn.block.crucible;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IHeatableBlock {
    default int getHeatLevel(Level level, BlockPos pos) {
        Block block = level.getBlockState(pos).getBlock();

        return HeatSource.HEAT_SOURCES.getOrDefault(block,0);
    }
}
