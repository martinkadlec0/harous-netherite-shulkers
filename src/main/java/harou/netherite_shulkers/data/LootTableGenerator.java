package harou.netherite_shulkers.data;

import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import java.util.concurrent.CompletableFuture;

public class LootTableGenerator extends FabricBlockLootSubProvider {
  public LootTableGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate() {
    // Generate loot tables for all netherite shulker boxes
    add(ModBlocks.NETHERITE_SHULKER_BOX, this::createNetheriteShulkerBoxDrop);
    
    // Add all colored variants using the static get method
    for (DyeColor color : DyeColor.values()) {
      add(NetheriteShulkerBoxBlock.getBlockByColor(color), this::createNetheriteShulkerBoxDrop);
    }
  }

  private LootTable.Builder createNetheriteShulkerBoxDrop(Block block) {
    return LootTable.lootTable()
      .withPool(LootPool.lootPool()
        .setRolls(ConstantValue.exactly(1))
        .add(LootItem.lootTableItem(block)
          .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
            .include(DataComponents.CONTAINER)
            .include(DataComponents.CONTAINER_LOOT)
            .include(DataComponents.CUSTOM_NAME)
            .include(DataComponents.LOCK)
          )
        )
      );
  }
} 