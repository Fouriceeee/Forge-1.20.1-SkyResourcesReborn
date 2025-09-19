package com.ironsword.skyresourcesreborn.data;

import com.ironsword.skyresourcesreborn.SkyResourcesReborn;
import com.ironsword.skyresourcesreborn.registry.ModBlocks;
import com.ironsword.skyresourcesreborn.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GenLang extends LanguageProvider {
    private final String itemLang = String.format("item.%s.",SkyResourcesReborn.MODID);
    private final String blockLang = String.format("block.%s.",SkyResourcesReborn.MODID);
    private final String fluidLang = String.format("fluid.%s.",SkyResourcesReborn.MODID);

    public GenLang(PackOutput output, String locale) {
        super(output, SkyResourcesReborn.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        JEI();
        add("itemGroup."+SkyResourcesReborn.MODID,"Sky Resources Reborn");
        add(SkyResourcesReborn.MODID + ".container.dirt_furnace","Dirt Furnace");
        addItems();
        addBlocks();
    }

    private void JEI() {
        add(SkyResourcesReborn.MODID+".jei.cutting","Cutting");
        add(SkyResourcesReborn.MODID+".jei.grinding","Grinding");
        add(SkyResourcesReborn.MODID+".jei.extracting","Extracting");
        add(SkyResourcesReborn.MODID+".jei.inserting","Inserting");
        add(SkyResourcesReborn.MODID+".jei.heat_source","Heat Source");
        add(SkyResourcesReborn.MODID+".jei.heating","Heating");
    }

    private void addItems() {
        //Take Dark Matter as an exapmle
        //itemRegistryObject.getPath() -> "dark_matter"
        //itemRegistryObject.getId().toString() -> "skyresourcesreborn:dark_matter"
        //itemRegistryObject.getKey().toString() -> "ResourceKey[minecraft:item / skyresourcesreborn:dark_matter]"

        ModItems.ITEMS.getEntries().forEach(item -> addKey(itemLang,item.getId().getPath()));
    }

    private void addBlocks() {
        ModBlocks.BLOCKS.getEntries().forEach(block-> addKey(blockLang,block.getId().getPath()));
    }

    private void addItem(String name) {
        add(itemLang+name,convertToName(name));
    }

    private void addKey(String prefix,String name) {
        add(prefix+name,convertToName(name));
    }

    private static String convertToName(String id) {
        StringBuilder builder = new StringBuilder();
        String[] subStrings = id.split("_");
        for (String s:subStrings) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
        }
        return builder.toString();
    }
}
