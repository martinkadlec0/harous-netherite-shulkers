package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.ModBlockItemIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagsProvider.BlockTagsProvider {
	public BlockTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		HarousNetheriteShulkers.LOGGER.info("Generating block tags for Netherite Shulker Boxes...");

		// Add all netherite shulker boxes to the shulker_boxes & mineable/pickaxe tags
		var pickaxeMineable = builder(BlockTags.MINEABLE_WITH_PICKAXE);
		var shulkerBoxes = builder(BlockTags.SHULKER_BOXES);

		// Add base netherite shulker box (no color)
		pickaxeMineable.add(ModBlockItemIds.NETHERITE_SHULKER_BOX.block());
		shulkerBoxes.add(ModBlockItemIds.NETHERITE_SHULKER_BOX.block());

		// Add all colored variants
		List<ResourceKey<Block>> dyedIds = ModBlockItemIds.DYED_NETHERITE_SHULKER_BOX
			.asList().stream().map(BlockItemId::block).toList();
		pickaxeMineable.addAll(dyedIds);
		shulkerBoxes.addAll(dyedIds);

		HarousNetheriteShulkers.LOGGER.info("Block tags generated successfully!");
	}
} 