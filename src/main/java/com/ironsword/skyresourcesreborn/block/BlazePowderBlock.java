package com.ironsword.skyresourcesreborn.block;

import com.ironsword.skyresourcesreborn.block.crucible.IHeatableBlock;
import com.ironsword.skyresourcesreborn.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlazePowderBlock extends Block implements IHeatableBlock {
    public BlazePowderBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isClientSide) return;

        float chance = (float) getHeatLevel2(pLevel, pPos) /10;

        if (pLevel.getRandom().nextFloat() <= chance) {
            pLevel.setBlockAndUpdate(pPos,Blocks.LAVA.defaultBlockState());
        }
    }
}
