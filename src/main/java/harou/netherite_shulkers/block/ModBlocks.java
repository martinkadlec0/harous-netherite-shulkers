package harou.netherite_shulkers.block;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import harou.netherite_shulkers.HarousNetheriteShulkers;

public class ModBlocks {
  public static void initialize() {}
  
  private static final BlockBehaviour.StatePredicate SHULKER_BOX_SUFFOCATES_PREDICATE = (state, world, pos) -> world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity
    ? shulkerBoxBlockEntity.isClosed()
    : true;
  
  public static final Block NETHERITE_SHULKER_BOX = register("netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(null, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_PURPLE));
  public static final Block WHITE_NETHERITE_SHULKER_BOX = register(
    "white_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.WHITE, settings), createNetheriteShulkerBoxSettings(MapColor.SNOW)
  );
  public static final Block ORANGE_NETHERITE_SHULKER_BOX = register(
    "orange_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.ORANGE, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_ORANGE)
  );
  public static final Block MAGENTA_NETHERITE_SHULKER_BOX = register(
    "magenta_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.MAGENTA, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_MAGENTA)
  );
  public static final Block LIGHT_BLUE_NETHERITE_SHULKER_BOX = register(
    "light_blue_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_BLUE, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_LIGHT_BLUE)
  );
  public static final Block YELLOW_NETHERITE_SHULKER_BOX = register(
    "yellow_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.YELLOW, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_YELLOW)
  );
  public static final Block LIME_NETHERITE_SHULKER_BOX = register(
    "lime_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIME, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_LIGHT_GREEN)
  );
  public static final Block PINK_NETHERITE_SHULKER_BOX = register(
    "pink_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.PINK, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_PINK)
  );
  public static final Block GRAY_NETHERITE_SHULKER_BOX = register(
    "gray_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.GRAY, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_GRAY)
  );
  public static final Block LIGHT_GRAY_NETHERITE_SHULKER_BOX = register(
    "light_gray_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_GRAY, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_LIGHT_GRAY)
  );
  public static final Block CYAN_NETHERITE_SHULKER_BOX = register(
    "cyan_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.CYAN, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_CYAN)
  );
  public static final Block PURPLE_NETHERITE_SHULKER_BOX = register(
    "purple_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.PURPLE, settings), createNetheriteShulkerBoxSettings(MapColor.TERRACOTTA_PURPLE)
  );
  public static final Block BLUE_NETHERITE_SHULKER_BOX = register(
    "blue_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BLUE, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_BLUE)
  );
  public static final Block BROWN_NETHERITE_SHULKER_BOX = register(
    "brown_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BROWN, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_BROWN)
  );
  public static final Block GREEN_NETHERITE_SHULKER_BOX = register(
    "green_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.GREEN, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_GREEN)
  );
  public static final Block RED_NETHERITE_SHULKER_BOX = register(
    "red_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.RED, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_RED)
  );
  public static final Block BLACK_NETHERITE_SHULKER_BOX = register(
    "black_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BLACK, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_BLACK)
  );

    private static BlockBehaviour.Properties createNetheriteShulkerBoxSettings(MapColor mapColor) {
    return BlockBehaviour.Properties.of()
      .mapColor(mapColor)
      .forceSolidOn()
      .strength(2.0f, 1200.0f)
      .dynamicShape()
      .noOcclusion()
      .isSuffocating(SHULKER_BOX_SUFFOCATES_PREDICATE)
      .isViewBlocking(SHULKER_BOX_SUFFOCATES_PREDICATE)
      .pushReaction(PushReaction.DESTROY);
  }

  public static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    Block block = (Block)factory.apply(settings.setId(key));
    return Registry.register(BuiltInRegistries.BLOCK, key, block);
  }

  public static Block register(ResourceKey<Block> key, BlockBehaviour.Properties settings) {
    return register(key, Block::new, settings);
  }

  private static ResourceKey<Block> keyOf(String id) {
    return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, id));
  }

  private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    return register(keyOf(id), factory, settings);
  }

}
