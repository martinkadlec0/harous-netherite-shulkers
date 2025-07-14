package harou.netherite_shulkers.item;

import harou.netherite_shulkers.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;

public class ModItems {
	public static void initialize() {}
	
	public static final Item NETHERITE_SHULKER_BOX = register(
		ModBlocks.NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item WHITE_NETHERITE_SHULKER_BOX = register(
		ModBlocks.WHITE_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item ORANGE_NETHERITE_SHULKER_BOX = register(
		ModBlocks.ORANGE_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item MAGENTA_NETHERITE_SHULKER_BOX = register(
		ModBlocks.MAGENTA_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item LIGHT_BLUE_NETHERITE_SHULKER_BOX = register(
		ModBlocks.LIGHT_BLUE_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item YELLOW_NETHERITE_SHULKER_BOX = register(
		ModBlocks.YELLOW_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item LIME_NETHERITE_SHULKER_BOX = register(
		ModBlocks.LIME_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item PINK_NETHERITE_SHULKER_BOX = register(
		ModBlocks.PINK_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item GRAY_NETHERITE_SHULKER_BOX = register(
		ModBlocks.GRAY_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item LIGHT_GRAY_NETHERITE_SHULKER_BOX = register(
		ModBlocks.LIGHT_GRAY_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item CYAN_NETHERITE_SHULKER_BOX = register(
		ModBlocks.CYAN_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item PURPLE_NETHERITE_SHULKER_BOX = register(
		ModBlocks.PURPLE_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item BLUE_NETHERITE_SHULKER_BOX = register(
		ModBlocks.BLUE_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item BROWN_NETHERITE_SHULKER_BOX = register(
		ModBlocks.BROWN_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item GREEN_NETHERITE_SHULKER_BOX = register(
		ModBlocks.GREEN_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item RED_NETHERITE_SHULKER_BOX = register(
		ModBlocks.RED_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);
	public static final Item BLACK_NETHERITE_SHULKER_BOX = register(
		ModBlocks.BLACK_NETHERITE_SHULKER_BOX, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
	);

	public static Item getColoredNetheriteShulkerBoxItem(DyeColor color) {
		return switch (color) {
			case WHITE -> ModItems.WHITE_NETHERITE_SHULKER_BOX;
			case ORANGE -> ModItems.ORANGE_NETHERITE_SHULKER_BOX;
			case MAGENTA -> ModItems.MAGENTA_NETHERITE_SHULKER_BOX;
			case LIGHT_BLUE -> ModItems.LIGHT_BLUE_NETHERITE_SHULKER_BOX;
			case YELLOW -> ModItems.YELLOW_NETHERITE_SHULKER_BOX;
			case LIME -> ModItems.LIME_NETHERITE_SHULKER_BOX;
			case PINK -> ModItems.PINK_NETHERITE_SHULKER_BOX;
			case GRAY -> ModItems.GRAY_NETHERITE_SHULKER_BOX;
			case LIGHT_GRAY -> ModItems.LIGHT_GRAY_NETHERITE_SHULKER_BOX;
			case CYAN -> ModItems.CYAN_NETHERITE_SHULKER_BOX;
			case PURPLE -> ModItems.PURPLE_NETHERITE_SHULKER_BOX;
			case BLUE -> ModItems.BLUE_NETHERITE_SHULKER_BOX;
			case BROWN -> ModItems.BROWN_NETHERITE_SHULKER_BOX;
			case GREEN -> ModItems.GREEN_NETHERITE_SHULKER_BOX;
			case RED -> ModItems.RED_NETHERITE_SHULKER_BOX;
			case BLACK -> ModItems.BLACK_NETHERITE_SHULKER_BOX;
		};
	}

	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
	}

	public static Item register(Block block, Item.Settings settings) {
		return register(
			keyOf(block.getRegistryEntry().registryKey()), itemSettings -> new BlockItem(block, itemSettings), settings.useBlockPrefixedTranslationKey()
		);
	}

	public static Item register(RegistryKey<Item> key, java.util.function.Function<Item.Settings, Item> factory, Item.Settings settings) {
		Item item = factory.apply(settings.registryKey(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registries.ITEM, key, item);
	}
} 