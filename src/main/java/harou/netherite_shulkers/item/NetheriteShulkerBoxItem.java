package harou.netherite_shulkers.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class NetheriteShulkerBoxItem extends BlockItem {
	public NetheriteShulkerBoxItem(Block block, Item.Properties settings) {
		super(block, settings
			.fireResistant()
			.stacksTo(1)
			.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
		);
	}

	public static Item get(@Nullable DyeColor dyeColor) {
		return dyeColor == null ? ModItems.NETHERITE_SHULKER_BOX : ModItems.DYED_NETHERITE_SHULKER_BOX.pick(dyeColor);
	}
} 