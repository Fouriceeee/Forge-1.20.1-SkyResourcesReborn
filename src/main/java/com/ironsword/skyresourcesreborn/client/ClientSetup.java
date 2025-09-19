package com.ironsword.skyresourcesreborn.client;

import com.ironsword.skyresourcesreborn.registry.ModBlocks;
import com.ironsword.skyresourcesreborn.registry.ModEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientSetup {
    public static void setup() {
        setupRenderTypes();
    }


    private static void setupRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRY_CACTUS.get(),RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRUCIBLE.get(), RenderType.cutout());
    }


}
