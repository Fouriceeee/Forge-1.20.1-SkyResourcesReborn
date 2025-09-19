package com.ironsword.skyresourcesreborn.registry;

import com.google.common.collect.Sets;
import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SkyResourcesReborn.MODID);

    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();
    public static LinkedHashSet<RegistryObject<? extends Block>> CREATIVE_TAB_BLOCKS = Sets.newLinkedHashSet();

    public static final RegistryObject<CreativeModeTab> TAB_SKY_RESOURCES_REBORN = CREATIVE_TABS.register(SkyResourcesReborn.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.skyresourcesreborn"))
                    .icon(() -> new ItemStack(Blocks.CRAFTING_TABLE))
                    .displayItems((parameters, output) -> {
                        CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get()));
                        CREATIVE_TAB_BLOCKS.forEach((block)-> output.accept(block.get()));
                    })
                    .build());
}
