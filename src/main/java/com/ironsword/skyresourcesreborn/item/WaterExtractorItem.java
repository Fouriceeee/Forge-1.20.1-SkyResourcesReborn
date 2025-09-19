package com.ironsword.skyresourcesreborn.item;

import com.ironsword.skyresourcesreborn.recipe.ExtractingRecipe;
import com.ironsword.skyresourcesreborn.recipe.InsertingRecipe;
import com.ironsword.skyresourcesreborn.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WaterExtractorItem extends Item{
    private static final int maxAmount = 4000;
    private static final int fullTime = 25;
    private static final int shortTime = 5;
    private static final int tankLevels = 7;

    public WaterExtractorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, maxAmount);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return fullTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return new InteractionResultHolder<>(InteractionResult.PASS,pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeCharged) {
        if (pEntity instanceof Player player) {
            if (fullTime - pTimeCharged <= shortTime) {
                BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel,player,ClipContext.Fluid.NONE);
                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                    IFluidHandlerItem fluidHandlerItem = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

                    BlockPos hitBlockPos = blockHitResult.getBlockPos();
                    BlockState hitBlockState = pLevel.getBlockState(hitBlockPos);
                    InteractionHand hand = player.getUsedItemHand();
                    Direction hitDirection = blockHitResult.getDirection();
                    BlockPos relativeBlockPos = hitBlockPos.relative(hitDirection);

                    //insert liquid to block interaction
                    SimpleContainer container = new SimpleContainer(hitBlockState.getBlock().asItem().getDefaultInstance());
                    Optional<InsertingRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(ModRecipeTypes.INSERTING.get(),container,pLevel);

                    if (recipe.isPresent()) {
                        Block resultBlock = Block.byItem(recipe.get().getResultItem(null).getItem());
                        FluidStack fluidStack = recipe.get().getFluid();
                        if (fluidHandlerItem.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE).getAmount() == fluidStack.getAmount()
                                && resultBlock != Blocks.AIR) {
                            fluidHandlerItem.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                            pLevel.setBlockAndUpdate(hitBlockPos,resultBlock.defaultBlockState());
                            pLevel.playSound(null,hitBlockPos, SoundEvents.PLAYER_SPLASH,SoundSource.BLOCKS,1.0F,1.0F);
                            return;
                        }
                    }

                    //place liquid block interaction
                    if (player.isShiftKeyDown()) {
                        if (FluidUtil.tryPlaceFluid(player, pLevel, hand, hitBlockPos, fluidHandlerItem, fluidHandlerItem.getFluidInTank(0))) {
                            player.displayClientMessage(
                                    Component.literal(getContent(fluidHandlerItem)),
                                    true
                            );
                        } else if (FluidUtil.tryPlaceFluid(player, pLevel, hand, relativeBlockPos, fluidHandlerItem, fluidHandlerItem.getFluidInTank(0))) {
                            player.displayClientMessage(
                                    Component.literal(getContent(fluidHandlerItem)),
                                    true
                            );
                        }
                    }


                }
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel, player, ClipContext.Fluid.SOURCE_ONLY);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                IFluidHandlerItem fluidHandlerItem = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);

                BlockPos hitBlockPos = blockHitResult.getBlockPos();
                BlockState hitBlockState = pLevel.getBlockState(hitBlockPos);
                Direction hitDirection = blockHitResult.getDirection();

                //extract from cauldron interaction
                //WIP

                //extract liquid block interaction
                FluidActionResult fluidActionResult = FluidUtil.tryPickUpFluid(pStack,player,pLevel,hitBlockPos,hitDirection);
                if (fluidActionResult.isSuccess()) {
                    ItemStack resultStack = fluidActionResult.getResult();
                    IFluidHandlerItem fluidHandlerItem1 = resultStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
                    player.displayClientMessage(
                            Component.literal(getContent(fluidHandlerItem1)),
                            true
                    );
                    player.getCooldowns().addCooldown(this,10);
                    return resultStack;
                }

                //extract from block interaction
                SimpleContainer container = new SimpleContainer(hitBlockState.getBlock().asItem().getDefaultInstance());
                Optional<ExtractingRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(ModRecipeTypes.EXTRACTING.get(),container,pLevel);

                if (recipe.isEmpty()) {
                    return pStack;
                }

                if (fluidHandlerItem.fill(recipe.get().getOutputFluid(), IFluidHandler.FluidAction.SIMULATE) == recipe.get().getOutputFluid().getAmount()) {
                    pLevel.setBlockAndUpdate(hitBlockPos,Blocks.AIR.defaultBlockState());

                    recipe.get().getOutputFluid().getFluid().getPickupSound().ifPresent(soundEvent -> pLevel.playSound(null,hitBlockPos,soundEvent, SoundSource.BLOCKS,1.0F,1.0F));
                    
                    fluidHandlerItem.fill(recipe.get().getOutputFluid(), IFluidHandler.FluidAction.EXECUTE);

                    Block resultBlock = Block.byItem(recipe.get().getResultBlock().getItem());

                    pLevel.setBlockAndUpdate(hitBlockPos,resultBlock.defaultBlockState());
                }
            }
        }
        return pStack;
    }

    private String getContent(IFluidHandlerItem fluidHandlerItem) {
        return ForgeRegistries.FLUIDS.getKey(fluidHandlerItem.getFluidInTank(0).getFluid()).getPath() + ":" + fluidHandlerItem.getFluidInTank(0).getAmount();
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return FluidUtil.getFluidContained(pStack).isPresent();
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        IFluidHandlerItem fluidHandlerItem = pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        return Math.round((float)fluidHandlerItem.getFluidInTank(0).getAmount() / (float) maxAmount * 13.0F);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0x3F76E4;
    }
}
