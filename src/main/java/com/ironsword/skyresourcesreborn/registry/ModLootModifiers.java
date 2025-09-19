package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.loot.CuttingKnifeLootModifier;
import com.ironsword.skyresourcesreborn.loot.RockGrinderLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SkyResourcesReborn.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> CUTTING = LOOT_MODIFIER_SERIALIZERS.register("cutting", CuttingKnifeLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> GRINDING = LOOT_MODIFIER_SERIALIZERS.register("grinding", RockGrinderLootModifier.CODEC);
}
