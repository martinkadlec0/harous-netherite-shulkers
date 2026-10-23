package harou.netherite_shulkers.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.DyeColor;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import fuzs.iteminteractions.common.api.v2.data.ItemStorageProvider;
import fuzs.iteminteractions.common.api.v2.world.item.DyeBackedColor;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ContainerStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.StorageOptions;

import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;

public class EasyShulkerBoxesGenerator extends FabricDynamicRegistryProvider {
	public static final ItemStorageProvider ITEM_STORAGE_DEFINITIONS = new ItemStorageProvider() {
		@Override
		public void run(BootstrapContext<ItemStorage.Definition> context) {
			StorageOptions options = StorageOptions.DEFAULT.setFilterContainerItems(true);

			ItemStorage provider = new ContainerStorage(9, 3, null, options);
			this.add(context, provider, ModItems.NETHERITE_SHULKER_BOX);

			for (DyeColor dyeColor : DyeColor.values()) {
				ItemStorage coloredProvider = new ContainerStorage(9, 3, DyeBackedColor.fromDyeColor(dyeColor), options);
				this.add(context, coloredProvider, NetheriteShulkerBoxItem.get(dyeColor));
			}
		}
	};

	public EasyShulkerBoxesGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(ItemStorage.Definition.REGISTRY_KEY));
	}

	@Override
	public String getName() {
		return "Easy Shulker Boxes Item Storage Definitions";
	}
}
