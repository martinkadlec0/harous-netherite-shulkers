package harou.netherite_shulkers.integration.shulkerboxtooltip;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.color.ColorKey;
import com.misterpemodder.shulkerboxtooltip.api.provider.BlockEntityPreviewProvider;

import net.minecraft.block.Block;
import net.minecraft.util.DyeColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;

public class NetheriteShulkerBoxPreviewProvider extends BlockEntityPreviewProvider {
    static int SIZE = 27;
    static int MAX_ROW_SIZE = 9;

    public NetheriteShulkerBoxPreviewProvider() {
        super(SIZE, true);
    }

    @Override
    public boolean showTooltipHints(PreviewContext context) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ColorKey getWindowColorKey(PreviewContext context) {
        DyeColor dye = ((NetheriteShulkerBoxBlock) Block.getBlockFromItem(context.stack().getItem())).getColor();

        if (dye == null)
            return ColorKey.SHULKER_BOX;
        return switch (dye) {
            case ORANGE -> ColorKey.ORANGE_SHULKER_BOX;
            case MAGENTA -> ColorKey.MAGENTA_SHULKER_BOX;
            case LIGHT_BLUE -> ColorKey.LIGHT_BLUE_SHULKER_BOX;
            case YELLOW -> ColorKey.YELLOW_SHULKER_BOX;
            case LIME -> ColorKey.LIME_SHULKER_BOX;
            case PINK -> ColorKey.PINK_SHULKER_BOX;
            case GRAY -> ColorKey.GRAY_SHULKER_BOX;
            case LIGHT_GRAY -> ColorKey.LIGHT_GRAY_SHULKER_BOX;
            case CYAN -> ColorKey.CYAN_SHULKER_BOX;
            case PURPLE -> ColorKey.PURPLE_SHULKER_BOX;
            case BLUE -> ColorKey.BLUE_SHULKER_BOX;
            case BROWN -> ColorKey.BROWN_SHULKER_BOX;
            case GREEN -> ColorKey.GREEN_SHULKER_BOX;
            case RED -> ColorKey.RED_SHULKER_BOX;
            case BLACK -> ColorKey.BLACK_SHULKER_BOX;
            default -> ColorKey.WHITE_SHULKER_BOX;
        };
    }

    @Override
    public int getMaxRowSize(PreviewContext context) {
        return MAX_ROW_SIZE;
    }
}
