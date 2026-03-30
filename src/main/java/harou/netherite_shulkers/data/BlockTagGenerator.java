package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagsProvider.BlockTagsProvider {
  public BlockTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider arg) {
    HarousNetheriteShulkers.LOGGER.info("Generating block tags for Netherite Shulker Boxes...");
    
    // Add all netherite shulker boxes to the shulker_boxes & mineable/pickaxe tags
    var pickaxeMineable = valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
    var shulkerBoxes = valueLookupBuilder(BlockTags.SHULKER_BOXES);
    
    // Add base netherite shulker box (no color)
    pickaxeMineable.add(ModBlocks.NETHERITE_SHULKER_BOX);
    shulkerBoxes.add(ModBlocks.NETHERITE_SHULKER_BOX);
    
    // Add all colored variants
    for (DyeColor color : DyeColor.values()) {
      pickaxeMineable.add(NetheriteShulkerBoxBlock.getBlockByColor(color));
      shulkerBoxes.add(NetheriteShulkerBoxBlock.getBlockByColor(color));
    }
    
    HarousNetheriteShulkers.LOGGER.info("Block tags generated successfully!");
  }
} 