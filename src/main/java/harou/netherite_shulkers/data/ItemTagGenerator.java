package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<net.minecraft.item.Item> NETHERITE_SHULKER_BOXES = TagKey.of(
        net.minecraft.registry.RegistryKeys.ITEM, 
        Identifier.of("harous-netherite-shulkers", "netherite_shulker_boxes")
    );
    
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        HarousNetheriteShulkers.LOGGER.info("Generating item tags for Netherite Shulker Boxes...");
        
        // Add all netherite shulker boxes to the shulker_boxes tag
        var shulkerBoxes = valueLookupBuilder(ConventionalItemTags.SHULKER_BOXES);
        
        // Create custom tag for netherite shulker boxes
        var netheriteShulkerBoxes = valueLookupBuilder(NETHERITE_SHULKER_BOXES);
        
        // Add base netherite shulker box (no color)
        shulkerBoxes.add(ModItems.NETHERITE_SHULKER_BOX);
        netheriteShulkerBoxes.add(ModItems.NETHERITE_SHULKER_BOX);
        
        // Add all colored variants using the static get method
        for (DyeColor color : DyeColor.values()) {
            shulkerBoxes.add(NetheriteShulkerBoxItem.get(color));
            netheriteShulkerBoxes.add(NetheriteShulkerBoxItem.get(color));
        }
        
        HarousNetheriteShulkers.LOGGER.info("Item tags generated successfully!");
    }
} 