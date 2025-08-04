package harou.netherite_shulkers.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

public class NetheriteShulkerBoxItem extends BlockItem {
    public NetheriteShulkerBoxItem(Block block, Item.Settings settings) {
        super(block, settings
            .fireproof()
            .maxCount(1)
            .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
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