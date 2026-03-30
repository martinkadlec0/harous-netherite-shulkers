package harou.netherite_shulkers.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DyeHelper {
  public static Item getItemByColor(DyeColor color) {
    return switch (color) {
      case WHITE -> Items.WHITE_DYE;
      case ORANGE -> Items.ORANGE_DYE;
      case MAGENTA -> Items.MAGENTA_DYE;
      case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
      case YELLOW -> Items.YELLOW_DYE;
      case LIME -> Items.LIME_DYE;
      case PINK -> Items.PINK_DYE;
      case GRAY -> Items.GRAY_DYE;
      case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
      case CYAN -> Items.CYAN_DYE;
      case PURPLE -> Items.PURPLE_DYE;
      case BLUE -> Items.BLUE_DYE;
      case BROWN -> Items.BROWN_DYE;
      case GREEN -> Items.GREEN_DYE;
      case RED -> Items.RED_DYE;
      case BLACK -> Items.BLACK_DYE;
    };
  }
}