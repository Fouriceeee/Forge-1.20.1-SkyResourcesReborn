package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.block.crucible.CrucibleBlockEntity;
import com.ironsword.skyresourcesreborn.block.dirtFurnace.DirtFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SkyResourcesReborn.MODID);

    public static final RegistryObject<BlockEntityType<DirtFurnaceBlockEntity>> DIRT_FURNACE_BLOCK_ENTITY = BLOCK_ENTITIES.register("dirt_furnace_block_entity",() -> BlockEntityType.Builder.of(DirtFurnaceBlockEntity::new , ModBlocks.DIRT_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("crucible_block_entity",()->BlockEntityType.Builder.of(CrucibleBlockEntity::new , ModBlocks.CRUCIBLE.get()).build(null));
}
