package harou.netherite_shulkers.item;

import harou.netherite_shulkers.block.ModBlockItemIds;
import harou.netherite_shulkers.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;

public class ModItems {
	public static void initialize() {}

	public static final Item NETHERITE_SHULKER_BOX = registerShulker(ModBlockItemIds.NETHERITE_SHULKER_BOX, ModBlocks.NETHERITE_SHULKER_BOX);
	
	public static final ColorCollection<Item> DYED_NETHERITE_SHULKER_BOX = ColorCollection.registerBlockItems(
		ModBlockItemIds.DYED_NETHERITE_SHULKER_BOX, ModBlocks.DYED_NETHERITE_SHULKER_BOX, (id, block, color) -> registerShulker(id, block)
	);

	public static Item registerShulker(BlockItemId id, Block block) {
		return register(id.item(), itemSettings -> new NetheriteShulkerBoxItem(block, itemSettings), new Item.Properties().useBlockDescriptionPrefix());
	}

	public static Item register(ResourceKey<Item> key, java.util.function.Function<Item.Properties, Item> factory, Item.Properties settings) {
		Item item = factory.apply(settings.setId(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.registerBlocks(Item.BY_BLOCK, item);
		}

		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
} 