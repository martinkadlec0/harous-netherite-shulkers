package harou.netherite_shulkers;

import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.block.entity.ModTexturedRenderLayers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntityRenderer;
import harou.netherite_shulkers.item.NetheriteShulkerBoxModelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.SpecialBlockRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.DyeColor;

public class HarousNetheriteShulkersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register block entity renderer
        BlockEntityRendererFactories.register(
            ModBlockEntities.NETHERITE_SHULKER_BOX,
            NetheriteShulkerBoxBlockEntityRenderer::new
        );

        registerTexturedRenderLayers();
        registerSpecialModelRenderers();
    }

    private void registerSpecialModelRenderers() {
        // Register for the base netherite shulker box
        SpecialBlockRendererRegistry.register(
            ModBlocks.NETHERITE_SHULKER_BOX,
            new NetheriteShulkerBoxModelRenderer.Unbaked()
        );

        // Register for all colored variants
        for (DyeColor color : DyeColor.values()) {
            SpecialBlockRendererRegistry.register(
                NetheriteShulkerBoxBlock.get(color),
                new NetheriteShulkerBoxModelRenderer.Unbaked(color)
            );
        }
    }

    public void registerTexturedRenderLayers() {
        ModTexturedRenderLayers.registerNetheriteShulkerSpriteMapper(HarousNetheriteShulkers.MOD_ID);
		ModTexturedRenderLayers.registerMaterialDefaultSprite(HarousNetheriteShulkers.MOD_ID);
        ModTexturedRenderLayers.registerMaterialColoringSprites(HarousNetheriteShulkers.MOD_ID);
    }
} 