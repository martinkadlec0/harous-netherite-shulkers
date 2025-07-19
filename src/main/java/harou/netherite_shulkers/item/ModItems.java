package harou.netherite_shulkers.item;

import harou.netherite_shulkers.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModItems {
	public static void initialize() {}
	
	public static final Item NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.NETHERITE_SHULKER_BOX);
	public static final Item WHITE_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.WHITE_NETHERITE_SHULKER_BOX);
	public static final Item ORANGE_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.ORANGE_NETHERITE_SHULKER_BOX);
	public static final Item MAGENTA_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.MAGENTA_NETHERITE_SHULKER_BOX);
	public static final Item LIGHT_BLUE_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.LIGHT_BLUE_NETHERITE_SHULKER_BOX);
	public static final Item YELLOW_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.YELLOW_NETHERITE_SHULKER_BOX);
	public static final Item LIME_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.LIME_NETHERITE_SHULKER_BOX);
	public static final Item PINK_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.PINK_NETHERITE_SHULKER_BOX);
	public static final Item GRAY_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.GRAY_NETHERITE_SHULKER_BOX);
	public static final Item LIGHT_GRAY_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.LIGHT_GRAY_NETHERITE_SHULKER_BOX);
	public static final Item CYAN_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.CYAN_NETHERITE_SHULKER_BOX);
	public static final Item PURPLE_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.PURPLE_NETHERITE_SHULKER_BOX);
	public static final Item BLUE_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.BLUE_NETHERITE_SHULKER_BOX);
	public static final Item BROWN_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.BROWN_NETHERITE_SHULKER_BOX);
	public static final Item GREEN_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.GREEN_NETHERITE_SHULKER_BOX);
	public static final Item RED_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.RED_NETHERITE_SHULKER_BOX);
	public static final Item BLACK_NETHERITE_SHULKER_BOX = registerShulker(ModBlocks.BLACK_NETHERITE_SHULKER_BOX);

	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
	}

	public static Item registerShulker(Block block) {
		return register(
			keyOf(block.getRegistryEntry().registryKey()), itemSettings -> new NetheriteShulkerBoxItem(block, itemSettings), new Item.Settings().useBlockPrefixedTranslationKey()
		);
	}

	// public static Item register(Block block, Item.Settings settings) {
	// 	return register(
	// 		keyOf(block.getRegistryEntry().registryKey()), itemSettings -> new BlockItem(block, itemSettings), settings.useBlockPrefixedTranslationKey()
	// 	);
	// }

	public static Item register(RegistryKey<Item> key, java.util.function.Function<Item.Settings, Item> factory, Item.Settings settings) {
		Item item = factory.apply(settings.registryKey(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registries.ITEM, key, item);
	}
} 