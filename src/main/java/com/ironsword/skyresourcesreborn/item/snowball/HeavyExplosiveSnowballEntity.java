package com.ironsword.skyresourcesreborn.item.snowball;

import com.ironsword.skyresourcesreborn.registry.ModEntities;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class HeavyExplosiveSnowballEntity extends ThrowableItemProjectile {
    public HeavyExplosiveSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HeavyExplosiveSnowballEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.HEAVY_EXPLOSIVE_SNOWBALL_ENTITY.get(), pShooter, pLevel);
    }

    public HeavyExplosiveSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.HEAVY_EXPLOSIVE_SNOWBALL_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.HEAVY_EXPLOSIVE_SNOWBALL.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        int i = entity instanceof Blaze ? 20 : 12;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)i);
        this.level().explode(entity,entity.getX(),entity.getY(),entity.getZ(),1.0F, false,Level.ExplosionInteraction.NONE).explode();
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
