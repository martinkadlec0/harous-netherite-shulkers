package harou.netherite_shulkers;

import harou.netherite_shulkers.data.BlockTagGenerator;
import harou.netherite_shulkers.data.ItemTagGenerator;
import harou.netherite_shulkers.data.LootTableGenerator;
import harou.netherite_shulkers.data.RecipeGenerator;
import harou.netherite_shulkers.data.EasyShulkerBoxesGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorage;

public class HarousNetheriteShulkersDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RecipeGenerator::new);
		pack.addProvider(LootTableGenerator::new);
		pack.addProvider(BlockTagGenerator::new);
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(EasyShulkerBoxesGenerator::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(ItemStorage.Definition.REGISTRY_KEY, EasyShulkerBoxesGenerator.ITEM_STORAGE_DEFINITIONS);
	}
}
