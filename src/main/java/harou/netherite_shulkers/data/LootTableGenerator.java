package harou.netherite_shulkers.data;

import harou.netherite_shulkers.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LootTableGenerator extends FabricBlockLootTableProvider {
    public LootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        // Generate loot tables for all netherite shulker boxes
        addDrop(ModBlocks.NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.WHITE_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.ORANGE_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.MAGENTA_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.LIGHT_BLUE_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.YELLOW_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.LIME_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.PINK_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.GRAY_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.LIGHT_GRAY_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.CYAN_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.PURPLE_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.BLUE_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.BROWN_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.GREEN_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.RED_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
        addDrop(ModBlocks.BLACK_NETHERITE_SHULKER_BOX, this::createShulkerBoxDrop);
    }

    private LootTable.Builder createShulkerBoxDrop(Block block) {
        return LootTable.builder()
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(block)
                    .apply(CopyComponentsLootFunction.builder(CopyComponentsLootFunction.Source.BLOCK_ENTITY)
                        .include(DataComponentTypes.CONTAINER)
                        .include(DataComponentTypes.CONTAINER_LOOT)
                        .include(DataComponentTypes.CUSTOM_NAME)
                        .include(DataComponentTypes.LOCK)
                    )
                )
            );
    }
} 