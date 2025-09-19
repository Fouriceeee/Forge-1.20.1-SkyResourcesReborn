package com.ironsword.skyresourcesreborn.client.renderer.crucible;

import com.ironsword.skyresourcesreborn.block.crucible.CrucibleBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CrucibleFluidRenderer implements BlockEntityRenderer<CrucibleBlockEntity> {
    @Override
    public void render(CrucibleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        FluidStack fluidStack = pBlockEntity.getTank().getFluid();
        int capability = pBlockEntity.getTank().getCapacity();

        if (!fluidStack.isEmpty() && capability > 0) {
            Matrix4f matrix = pPoseStack.last().pose();
            Matrix3f normal = pPoseStack.last().normal();
        }
    }
}
