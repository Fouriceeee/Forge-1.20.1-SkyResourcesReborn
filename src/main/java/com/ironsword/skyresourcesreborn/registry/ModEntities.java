package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.item.snowball.HeavyExplosiveSnowballEntity;
import com.ironsword.skyresourcesreborn.item.snowball.HeavySnowballEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SkyResourcesReborn.MODID);

    public static final RegistryObject<EntityType<HeavySnowballEntity>> HEAVY_SNOWBALL_ENTITY =
            ENTITY_TYPES.register("heavy_snowball",
                    () -> EntityType.Builder.<HeavySnowballEntity>of(HeavySnowballEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("heavy_snowball"));

    public static final RegistryObject<EntityType<HeavyExplosiveSnowballEntity>> HEAVY_EXPLOSIVE_SNOWBALL_ENTITY =
            ENTITY_TYPES.register("heavy_explosive_snowball",
                    ()-> EntityType.Builder.<HeavyExplosiveSnowballEntity>of(HeavyExplosiveSnowballEntity::new,MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("heavy_explosive_snowball"));
}
