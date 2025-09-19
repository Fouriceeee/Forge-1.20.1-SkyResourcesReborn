package com.ironsword.skyresourcesreborn.block.crucible;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IHeatableBlock {
    default int getHeatLevel(Level level, BlockPos pos) {
        BlockState blockStateBelow = level.getBlockState(pos.below());

        return HeatSource.HEAT_SOURCES.getOrDefault(blockStateBelow,0);
    }

    default int getHeatLevel2(Level level, BlockPos pos) {
        Block block = level.getBlockState(pos.below()).getBlock();

        for (BlockState state:HeatSource.HEAT_SOURCES.keySet()) {
            if (block.equals(state.getBlock()))
                return 10;
        }
        return 0;
    }
}
