package harou.netherite_shulkers.integration.shulkerboxtooltip;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import java.util.stream.Stream;

public class ShulkerBoxTooltip implements ShulkerBoxTooltipApi {
	static Item[] ITEMS = Stream.concat(Stream.of(ModItems.NETHERITE_SHULKER_BOX), ModItems.DYED_NETHERITE_SHULKER_BOX.asList().stream()).toArray(Item[]::new);

	@Override
	public void registerProviders(PreviewProviderRegistry registry) { 
		var identifier = Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, "netherite_shulker_box");
		registry.register(identifier, new NetheriteShulkerBoxPreviewProvider(), ITEMS);    
	}
}
