package harou.netherite_shulkers;

import harou.netherite_shulkers.data.LootTableGenerator;
import harou.netherite_shulkers.data.RecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class HarousNetheriteShulkersDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RecipeGenerator::new);
		pack.addProvider(LootTableGenerator::new);
	}
}
