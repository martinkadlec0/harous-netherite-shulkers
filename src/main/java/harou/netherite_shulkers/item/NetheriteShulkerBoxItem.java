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
		if (dyeColor == null) {
			return ModItems.NETHERITE_SHULKER_BOX;
		} else {
			return switch (dyeColor) {
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
	}
} 