package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SRRFluids {
    //气动工艺，林业，Ars附属,余烬，核电工艺

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SkyResourcesReborn.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SkyResourcesReborn.MODID);

    public static final RegistryObject<FluidType> CRYSTAL_FLUID_TYPE = FLUID_TYPES.register("crystal_fluid", CrystalFluid::new);
    public static final RegistryObject<FlowingFluid> CRYSTAL_FLUID = FLUIDS.register("crystal_fluid",()->
            new ForgeFlowingFluid.Source(fluidProperties()));
    public static final RegistryObject<Fluid> CRYSTAL_FLUID_FLOWING = FLUIDS.register("crystal_fluid_flowing",()->
            new ForgeFlowingFluid.Flowing(fluidProperties()));
    public static final RegistryObject<LiquidBlock> CRYSTAL_FLUID_BLOCK = ModBlocks.BLOCKS.register("crystal_fluid_block",()->
            new LiquidBlock(CRYSTAL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
    public static final RegistryObject<BucketItem> CRYSTAL_FLUID_BUCKET = ModItems.ITEMS.register("crystal_fluid_bucket",()->
            new BucketItem(CRYSTAL_FLUID,new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    private static ForgeFlowingFluid.Properties fluidProperties() {
        return new ForgeFlowingFluid.Properties(CRYSTAL_FLUID_TYPE, CRYSTAL_FLUID, CRYSTAL_FLUID_FLOWING)
                .block(CRYSTAL_FLUID_BLOCK)
                .bucket(CRYSTAL_FLUID_BUCKET);
    }
}
