package com.ironsword.skyresourcesreborn.block.crucible;

import com.ironsword.skyresourcesreborn.recipe.HeatingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModBlockEntities;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrucibleBlockEntity extends BlockEntity implements IHeatableBlock{
    private final ItemStackHandler itemHandler;
    private final FluidTank tank;

    private LazyOptional<IItemHandler> lazyItemHandler;
    private LazyOptional<IFluidHandler> lazyFluidHandler;

    private int progress = 0;
    private int maxProgress = 20;
    private int heatLevel = 0;

    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRUCIBLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        itemHandler = createItemStackHandler();
        lazyItemHandler = LazyOptional.of(()->itemHandler);
        tank = new FluidTank(4000);
        lazyFluidHandler = LazyOptional.of(()->tank);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return lazyItemHandler.cast();
        }else if (cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()->itemHandler);
        lazyFluidHandler = LazyOptional.of(()->tank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory",itemHandler.serializeNBT());
        if (!tank.isEmpty()) {
            pTag.put("fluid",tank.writeToNBT(new CompoundTag()));
        }
        pTag.putInt("progress",progress);

    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("fluid")) {
            tank.readFromNBT(pTag.getCompound("fluid"));
        }
        progress = pTag.getInt("progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        heatLevel = getHeatLevel(pLevel,pPos.below());

        if (itemHandler.getStackInSlot(0).isEmpty()) return;

        SimpleContainer container = new SimpleContainer(itemHandler.getStackInSlot(0));
        Optional<HeatingRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.HEATING.get(),container,level);
        if (recipe.isPresent() && recipe.get().getIngredient().test(itemHandler.getStackInSlot(0))) {
            maxProgress = recipe.get().getProcessingTime();
            progress += heatLevel;
            setChanged(pLevel,pPos,pState);

            if (progress >= maxProgress) {
                if (tryAssemble(recipe.get().getResult())){
                    progress = 0;
                }else {
                    progress = maxProgress;
                }
            }
        }else {
            progress = 0;
        }
    }

    public boolean tryAssemble(FluidStack result) {
        if (tank.fill(result, IFluidHandler.FluidAction.SIMULATE) != result.getAmount())
            return false;

        itemHandler.getStackInSlot(0).shrink(1);
        tank.fill(result, IFluidHandler.FluidAction.EXECUTE);
        return true;
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    public ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler() {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
