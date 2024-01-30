package com.dunnewortel.coloredlamps;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColoredLamps implements ModInitializer {

	public static final String MODID = "cl";
	private static final Map<String, Block> COLORED_LAMPS = new LinkedHashMap<>();

	static {
		// Initialize colored lamps
		String[] colors = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
		FabricBlockSettings lampSettings = FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP);

		for (String color : colors) {
			COLORED_LAMPS.put(color + "_redstone_lamp", new RedstoneLampBlock(lampSettings));
		}
	}

	@Override
	public void onInitialize() {

		// Register each colored lamp
		COLORED_LAMPS.forEach((name, block) -> {
			Registry.register(Registries.BLOCK, new Identifier(MODID, name), block);
			Registry.register(Registries.ITEM, new Identifier(MODID, name), new BlockItem(block, new FabricItemSettings()));
		});

		// Modify the REDSTONE item group to include the new lamps
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
			COLORED_LAMPS.forEach((name, block) -> entries.addAfter(Items.REDSTONE_LAMP, Registries.ITEM.get(new Identifier(MODID, name))));
		});
	}
}