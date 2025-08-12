package harou.netherite_shulkers.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;

import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class EasyShulkerBoxesDataProviderContext extends DataProviderContext {
    public EasyShulkerBoxesDataProviderContext(String modId, DataOutput packOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(modId, packOutput, registries);
    }
}
