package harou.netherite_shulkers.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import fuzs.iteminteractions.api.v1.DyeBackedColor;
import fuzs.iteminteractions.api.v1.data.AbstractItemContentsProvider;
import fuzs.iteminteractions.api.v1.provider.ItemContentsProvider;
import fuzs.iteminteractions.api.v1.provider.impl.ContainerProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;

public class EasyShulkerBoxesGenerator extends AbstractItemContentsProvider {
    
    public EasyShulkerBoxesGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(new DataProviderContext("easyshulkerboxes", output, registriesFuture));
    }

    @Override
    public void addItemProviders(RegistryWrapper.WrapperLookup registries) {
        RegistryWrapper.Impl<Item> items = registries.getOrThrow(RegistryKeys.ITEM);
        this.registerNetheriteShulkerBoxProviders(items);
    }

    private void registerNetheriteShulkerBoxProviders(RegistryWrapper.Impl<Item> items) {
        ItemContentsProvider provider = new ContainerProvider(9, 3).filterContainerItems(true);
        this.add(items, provider, ModItems.NETHERITE_SHULKER_BOX);

        for (DyeColor dyeColor : DyeColor.values()) {
            ItemContentsProvider coloredProvider = new ContainerProvider(9, 3, DyeBackedColor.fromDyeColor(dyeColor)).filterContainerItems(true);
            this.add(items, coloredProvider, NetheriteShulkerBoxItem.get(dyeColor));
        }
    }
}
