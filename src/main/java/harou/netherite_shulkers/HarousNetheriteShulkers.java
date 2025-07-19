package harou.netherite_shulkers;

import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarousNetheriteShulkers implements ModInitializer {
	public static final String MOD_ID = "harous-netherite-shulkers";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Wait for vanilla blocks to be registered first
		LOGGER.info("Starting Harou's Netherite Shulkers initialization...");
		
		ModBlockEntities.initialize();
		ModBlocks.initialize();
		ModItems.initialize();

		// Add Netherite Shulker Boxes to the creative inventory tab after vanilla shulker boxes
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
			// Insert after all vanilla shulker boxes
			entries.addAfter(Blocks.BLACK_SHULKER_BOX.asItem(),
				ModItems.NETHERITE_SHULKER_BOX
			);
			// Add colored variants
			for (DyeColor color : DyeColor.values()) {
				entries.add(NetheriteShulkerBoxItem.get(color));
			}
		});

		// Register dispenser behavior for Netherite Shulker Boxes (same as vanilla shulker boxes)
		BlockPlacementDispenserBehavior shulkerBehavior = new BlockPlacementDispenserBehavior();
		DispenserBlock.registerBehavior(ModItems.NETHERITE_SHULKER_BOX, shulkerBehavior);
		for (DyeColor color : DyeColor.values()) {
			DispenserBlock.registerBehavior(NetheriteShulkerBoxItem.get(color), shulkerBehavior);
		}

		LOGGER.info("Harou's Netherite Shulkers mod initialized!");
	}
}