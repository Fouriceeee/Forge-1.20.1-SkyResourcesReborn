package com.ironsword.skyresourcesreborn.block.dirtFurnace;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;

public class DirtFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private ItemStack itemStack;
    private FluidStack fluidStack;

    public DirtFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DIRT_FURNACE_BLOCK_ENTITY.get(), pPos, pBlockState, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(SkyResourcesReborn.MODID+".container.dirt_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new FurnaceMenu(pId, pPlayer, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack pFuel) {
        return super.getBurnDuration(pFuel)/2;
    }
}
