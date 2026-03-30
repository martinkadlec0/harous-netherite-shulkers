package harou.netherite_shulkers;

// import harou.netherite_shulkers.block.ModBlocks;
// import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.block.entity.ModTexturedRenderLayers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxRenderer;
import harou.netherite_shulkers.item.NetheriteShulkerBoxSpecialRenderer;
import net.fabricmc.api.ClientModInitializer;
// import net.fabricmc.fabric.api.client.rendering.v1.SpecialBlockRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;

public class HarousNetheriteShulkersClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    // Register block entity renderer
    BlockEntityRenderers.register(
      ModBlockEntities.NETHERITE_SHULKER_BOX,
      NetheriteShulkerBoxRenderer::new
    );

    registerTexturedRenderLayers();
    registerSpecialModelRenderers();
  }

  // private void registerSpecialModelRenderers() {
  //     // Register for the base netherite shulker box
  //     SpecialBlockRendererRegistry.register(
  //         ModBlocks.NETHERITE_SHULKER_BOX,
  //         new NetheriteShulkerBoxSpecialRenderer.Unbaked()
  //     );

  //     // Register for all colored variants
  //     for (DyeColor color : DyeColor.values()) {
  //         SpecialBlockRendererRegistry.register(
  //             NetheriteShulkerBoxBlock.getBlockByColor(color),
  //             new NetheriteShulkerBoxSpecialRenderer.Unbaked(color)
  //         );
  //     }
  // }

  /**
   * Fabric doesn't have SpecialBlockRendererRegistry for 26.1, so instead we register stuff using mixin
   * @see {BuiltInBlockModelsMixin}
   */
  private void registerSpecialModelRenderers() {
    // assets/item -> model.model.type
    SpecialModelRenderers.ID_MAPPER.put(
      Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, "netherite_shulker_box"),
      NetheriteShulkerBoxSpecialRenderer.Unbaked.MAP_CODEC
    );
  }

  public void registerTexturedRenderLayers() {
    ModTexturedRenderLayers.registerNetheriteShulkerSpriteMapper(HarousNetheriteShulkers.MOD_ID);
    ModTexturedRenderLayers.registerMaterialDefaultSprite(HarousNetheriteShulkers.MOD_ID);
    ModTexturedRenderLayers.registerMaterialColoringSprites(HarousNetheriteShulkers.MOD_ID);
  }
} 