package com.ironsword.skyresourcesreborn.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PlantMatterItem extends BoneMealItem {
    public static final int TRIES = 100;

    public PlantMatterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(pContext.getClickedFace());
        if (applyBonemeal(pContext.getItemInHand(), level, blockpos, pContext.getPlayer())) {
            if (!level.isClientSide) {
                level.levelEvent(1505, blockpos, 0);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            BlockState blockstate = level.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy(level, blockpos, pContext.getClickedFace());
            if (flag && growWaterPlant(pContext.getItemInHand(), level, blockpos1, pContext.getClickedFace())) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, blockpos1, 0);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static boolean applyBonemeal(ItemStack pStack, Level pLevel, BlockPos pPos, net.minecraft.world.entity.player.Player player) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, pLevel, pPos, blockstate, pStack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock) {
            BonemealableBlock bonemealableblock = (BonemealableBlock)blockstate.getBlock();
            if (bonemealableblock.isValidBonemealTarget(pLevel, pPos, blockstate, pLevel.isClientSide)) {
                if (pLevel instanceof ServerLevel) {
                    if (bonemealableblock.isBonemealSuccess(pLevel, pLevel.random, pPos, blockstate)) {
                        for (int i=0;i<TRIES;i++) {
                            bonemealableblock.performBonemeal((ServerLevel) pLevel, pLevel.random, pPos, blockstate);
                        }
                    }
                    pStack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }
}
