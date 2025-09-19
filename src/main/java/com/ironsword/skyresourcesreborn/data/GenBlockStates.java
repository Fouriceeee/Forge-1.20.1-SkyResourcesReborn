package com.ironsword.skyresourcesreborn.data;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.block.dirtFurnace.DirtFurnaceBlock;
import com.ironsword.skyresourcesreborn.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class GenBlockStates extends BlockStateProvider {
    public GenBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SkyResourcesReborn.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        horizontalBlock(ModBlocks.DIRT_FURNACE.get(),state -> {
//            String name = ForgeRegistries.BLOCKS.getKey(ModBlocks.DIRT_FURNACE.get()).getPath();
//            String suffix = state.getValue(DirtFurnaceBlock.LIT) ? "_on" : "";
//
//            return models().orientable(name + suffix,
//                    resourceBlock(name + "_side"),
//                    resourceBlock(name  + "front" + suffix),
//                    resourceBlock(name + "top"));
//        });


        //dirt furnace
        horizontalBlock(ModBlocks.DIRT_FURNACE.get(),state -> {
            String name = name(ModBlocks.DIRT_FURNACE.get());
            String suffix = state.getValue(DirtFurnaceBlock.LIT) ? "_on" : "";

            return models().orientable(name + suffix,
                    new ResourceLocation("minecraft:block/dirt"),
                    resourceBlock(name+suffix),
                    new ResourceLocation("minecraft:block/dirt"));
        });
        simpleItemModel(ModBlocks.DIRT_FURNACE.get());

        //crucible
        existingModelBlock(ModBlocks.CRUCIBLE.get());

        //dry cactus
        existingModelBlock(ModBlocks.DRY_CACTUS.get());

        //blaze powder block
        simpleBlockWithItem(ModBlocks.BLAZE_POWDER_BLOCK.get());

    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(name(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(SkyResourcesReborn.MODID, "block/" + path);
    }

    private void simpleItemModel(Block block) {
        itemModels().withExistingParent(name(block),resourceBlock(name(block)));
    }

    private void simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block,cubeAll(block));
    }

    private void existingModelBlock(Block block) {
        simpleBlockWithItem(block,existingModel(block));
    }
}
