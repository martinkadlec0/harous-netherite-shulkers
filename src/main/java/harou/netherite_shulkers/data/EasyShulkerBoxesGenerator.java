package harou.netherite_shulkers.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import fuzs.iteminteractions.common.api.v2.data.AbstractItemStorageDefinitionsProvider;
import fuzs.iteminteractions.common.api.v2.world.item.DyeBackedColor;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ContainerStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.StorageOptions;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;

import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;

public class EasyShulkerBoxesGenerator extends AbstractItemStorageDefinitionsProvider {

	public EasyShulkerBoxesGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(new DataProviderContext("easyshulkerboxes", output, registriesFuture));
	}

	@Override
	public void addItemStorageDefinitions(HolderLookup.Provider registries) {
		StorageOptions options = StorageOptions.DEFAULT.setFilterContainerItems(true);

		ItemStorage provider = new ContainerStorage(9, 3, null, options);
		this.add(provider, ModItems.NETHERITE_SHULKER_BOX);

		for (DyeColor dyeColor : DyeColor.values()) {
			ItemStorage coloredProvider = new ContainerStorage(9, 3, DyeBackedColor.fromDyeColor(dyeColor), options);
			this.add(coloredProvider, NetheriteShulkerBoxItem.get(dyeColor));
		}
	}
}
