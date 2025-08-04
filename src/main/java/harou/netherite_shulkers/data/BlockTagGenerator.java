package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.DyeColor;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        HarousNetheriteShulkers.LOGGER.info("Generating block tags for Netherite Shulker Boxes...");
        
        // Add all netherite shulker boxes to the mineable/pickaxe tag
        var pickaxeMineable = valueLookupBuilder(BlockTags.PICKAXE_MINEABLE);
        
        // Add base netherite shulker box (no color)
        pickaxeMineable.add(ModBlocks.NETHERITE_SHULKER_BOX);
        
        // Add all colored variants using the static get method
        for (DyeColor color : DyeColor.values()) {
            pickaxeMineable.add(NetheriteShulkerBoxBlock.get(color));
        }
        
        HarousNetheriteShulkers.LOGGER.info("Block tags generated successfully!");
    }
} 