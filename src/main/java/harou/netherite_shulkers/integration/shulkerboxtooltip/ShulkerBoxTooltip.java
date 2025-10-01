package harou.netherite_shulkers.integration.shulkerboxtooltip;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;

import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.item.ModItems;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ShulkerBoxTooltip implements ShulkerBoxTooltipApi {
    static Item[] ITEMS = {
        ModItems.NETHERITE_SHULKER_BOX,
        ModItems.WHITE_NETHERITE_SHULKER_BOX,
        ModItems.ORANGE_NETHERITE_SHULKER_BOX,
        ModItems.MAGENTA_NETHERITE_SHULKER_BOX,
        ModItems.LIGHT_BLUE_NETHERITE_SHULKER_BOX,
        ModItems.YELLOW_NETHERITE_SHULKER_BOX,
        ModItems.LIME_NETHERITE_SHULKER_BOX,
        ModItems.PINK_NETHERITE_SHULKER_BOX,
        ModItems.GRAY_NETHERITE_SHULKER_BOX,
        ModItems.LIGHT_GRAY_NETHERITE_SHULKER_BOX,
        ModItems.CYAN_NETHERITE_SHULKER_BOX,
        ModItems.PURPLE_NETHERITE_SHULKER_BOX,
        ModItems.BLUE_NETHERITE_SHULKER_BOX,
        ModItems.BROWN_NETHERITE_SHULKER_BOX,
        ModItems.GREEN_NETHERITE_SHULKER_BOX,
        ModItems.RED_NETHERITE_SHULKER_BOX,
        ModItems.BLACK_NETHERITE_SHULKER_BOX
    };

    @Override
    public void registerProviders(PreviewProviderRegistry registry) { 
        String namespace = BlockEntityType.getId(ModBlockEntities.NETHERITE_SHULKER_BOX).getNamespace();
        var identifier = Identifier.of(namespace, "netherite_shulker_box");
        registry.register(identifier, new NetheriteShulkerBoxPreviewProvider(), ITEMS);    
    }
}
