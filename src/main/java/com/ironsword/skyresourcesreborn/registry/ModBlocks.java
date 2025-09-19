package com.ironsword.skyresourcesreborn.registry;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.block.BlazePowderBlock;
import com.ironsword.skyresourcesreborn.block.crucible.CrucibleBlock;
import com.ironsword.skyresourcesreborn.block.dirtFurnace.DirtFurnaceBlock;
import com.ironsword.skyresourcesreborn.block.DryCactusBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SkyResourcesReborn.MODID);

    public static final RegistryObject<Block> DIRT_FURNACE = registerBlockWithTab("dirt_furnace",
            ()->new DirtFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> DRY_CACTUS = registerBlockWithTab("dry_cactus",
            ()->new DryCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.4F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> CRUCIBLE = registerBlockWithTab("crucible",
            ()->new CrucibleBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> BLAZE_POWDER_BLOCK = registerBlockWithTab("blaze_powder_block",
            ()->new BlazePowderBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL)));

    private static <T extends Block>RegistryObject<T> registerBlockWithTab(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        ModCreativeTabs.CREATIVE_TAB_BLOCKS.add(toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return  toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));
    }
}
