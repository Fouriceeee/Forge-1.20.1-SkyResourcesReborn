package com.ironsword.skyresourcesreborn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ironsword.skyresourcesreborn.block.crucible.HeatSource;
import com.ironsword.skyresourcesreborn.client.ClientSetup;
import com.ironsword.skyresourcesreborn.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SkyResourcesReborn.MODID)
public class SkyResourcesReborn
{
    public static final String MODID = "skyresourcesreborn";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public SkyResourcesReborn(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);

        SRRFluids.FLUIDS.register(modEventBus);

        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        ModRecipeSerializers.SERIALIZERS.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        HeatSource.init();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(ClientSetup::setup);
        }

        @SubscribeEvent
        public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.HEAVY_SNOWBALL_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.HEAVY_EXPLOSIVE_SNOWBALL_ENTITY.get(),ThrownItemRenderer::new);
        }
    }
}
