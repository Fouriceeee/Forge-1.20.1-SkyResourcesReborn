package com.ironsword.skyresourcesreborn.block.crucible;

import com.ironsword.skyresourcesreborn.registry.ModBlockEntities;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends BaseEntityBlock {
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);

    public CrucibleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return INSIDE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), crucibleBlockEntity.getItemHandler().getStackInSlot(0));
                pLevel.updateNeighbourForOutputSignal(pPos,this);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
                ItemStack itemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                ItemStackHandler itemHandler = crucibleBlockEntity.getItemHandler();
                IFluidHandler fluidHandler = crucibleBlockEntity.getTank();

                if (pPlayer.isShiftKeyDown()) {
                    ItemStack crucibleItem = itemHandler.getStackInSlot(0);
                    if (!crucibleItem.isEmpty()) {
                        pPlayer.addItem(crucibleItem.copyAndClear());
                    }
                } else if (FluidUtil.interactWithFluidHandler(pPlayer,pHand,fluidHandler)) {
                    pPlayer.getInventory().setChanged();
                    
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                }
            }

        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
            if (pEntity instanceof ItemEntity itemEntity) {
                ItemStack remainStack = crucibleBlockEntity.getItemHandler().insertItem(0,itemEntity.getItem().copy(),false);
                if (remainStack.isEmpty()) {
                    itemEntity.discard();
                }
                else{
                    itemEntity.setItem(remainStack);
                }
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrucibleBlockEntity(pPos,pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.CRUCIBLE_BLOCK_ENTITY.get(),(pLevel1, pPos, pState1, pBlockEntity)->pBlockEntity.tick(pLevel1,pPos,pState1));
    }
}
