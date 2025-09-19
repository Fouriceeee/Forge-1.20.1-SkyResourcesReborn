package com.ironsword.skyresourcesreborn.recipe.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBlockContainer implements Container {
    private final NonNullList<Block> blocks = NonNullList.create();

    public SimpleBlockContainer(Block block) {
        this.blocks.add(block);
    }

    @Override
    public int getContainerSize() {
        return blocks.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public Block getBlock(int pSlot) {
        return blocks.get(pSlot);
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return blocks.get(pSlot).asItem().getDefaultInstance();
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        blocks.remove(pSlot);
        setChanged();
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        blocks.remove(pSlot);
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if (pStack.getItem() instanceof BlockItem blockItem) {
            blocks.set(pSlot,blockItem.getBlock());
        }
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        blocks.clear();
    }
}
