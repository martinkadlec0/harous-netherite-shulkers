package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.ModBlockItemIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
	public static final TagKey<Item> NETHERITE_SHULKER_BOXES = TagKey.create(
		Registries.ITEM,
		Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, "netherite_shulker_boxes")
	);

	public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		HarousNetheriteShulkers.LOGGER.info("Generating item tags for Netherite Shulker Boxes...");

		// Add all netherite shulker boxes to the shulker_boxes tag
		var shulkerBoxes = builder(ConventionalItemTags.SHULKER_BOXES);

		// Create custom tag for netherite shulker boxes
		var netheriteShulkerBoxes = builder(NETHERITE_SHULKER_BOXES);

		// Add base netherite shulker box (no color)
		shulkerBoxes.add(ModBlockItemIds.NETHERITE_SHULKER_BOX.item());
		netheriteShulkerBoxes.add(ModBlockItemIds.NETHERITE_SHULKER_BOX.item());

		// Add all colored variants
		List<ResourceKey<Item>> dyedIds = ModBlockItemIds.DYED_NETHERITE_SHULKER_BOX
			.asList().stream().map(BlockItemId::item).toList();
		shulkerBoxes.addAll(dyedIds);
		netheriteShulkerBoxes.addAll(dyedIds);

		HarousNetheriteShulkers.LOGGER.info("Item tags generated successfully!");
	}
} 