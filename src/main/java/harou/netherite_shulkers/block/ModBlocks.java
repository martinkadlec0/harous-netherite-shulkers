package harou.netherite_shulkers.block;

import java.util.function.Function;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static void initialize() {}
	
	private static final AbstractBlock.ContextPredicate SHULKER_BOX_SUFFOCATES_PREDICATE = (state, world, pos) -> world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity
		? shulkerBoxBlockEntity.suffocates()
		: true;
	
	public static final Block NETHERITE_SHULKER_BOX = register("netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(null, settings), createNetheriteShulkerBoxSettings(MapColor.PURPLE));
	public static final Block WHITE_NETHERITE_SHULKER_BOX = register(
		"white_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.WHITE, settings), createNetheriteShulkerBoxSettings(MapColor.WHITE)
	);
	public static final Block ORANGE_NETHERITE_SHULKER_BOX = register(
		"orange_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.ORANGE, settings), createNetheriteShulkerBoxSettings(MapColor.ORANGE)
	);
	public static final Block MAGENTA_NETHERITE_SHULKER_BOX = register(
		"magenta_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.MAGENTA, settings), createNetheriteShulkerBoxSettings(MapColor.MAGENTA)
	);
	public static final Block LIGHT_BLUE_NETHERITE_SHULKER_BOX = register(
		"light_blue_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_BLUE, settings), createNetheriteShulkerBoxSettings(MapColor.LIGHT_BLUE)
	);
	public static final Block YELLOW_NETHERITE_SHULKER_BOX = register(
		"yellow_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.YELLOW, settings), createNetheriteShulkerBoxSettings(MapColor.YELLOW)
	);
	public static final Block LIME_NETHERITE_SHULKER_BOX = register(
		"lime_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIME, settings), createNetheriteShulkerBoxSettings(MapColor.LIME)
	);
	public static final Block PINK_NETHERITE_SHULKER_BOX = register(
		"pink_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.PINK, settings), createNetheriteShulkerBoxSettings(MapColor.PINK)
	);
	public static final Block GRAY_NETHERITE_SHULKER_BOX = register(
		"gray_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.GRAY, settings), createNetheriteShulkerBoxSettings(MapColor.GRAY)
	);
	public static final Block LIGHT_GRAY_NETHERITE_SHULKER_BOX = register(
		"light_gray_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_GRAY, settings), createNetheriteShulkerBoxSettings(MapColor.LIGHT_GRAY)
	);
	public static final Block CYAN_NETHERITE_SHULKER_BOX = register(
		"cyan_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.CYAN, settings), createNetheriteShulkerBoxSettings(MapColor.CYAN)
	);
	public static final Block PURPLE_NETHERITE_SHULKER_BOX = register(
		"purple_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.PURPLE, settings), createNetheriteShulkerBoxSettings(MapColor.TERRACOTTA_PURPLE)
	);
	public static final Block BLUE_NETHERITE_SHULKER_BOX = register(
		"blue_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BLUE, settings), createNetheriteShulkerBoxSettings(MapColor.BLUE)
	);
	public static final Block BROWN_NETHERITE_SHULKER_BOX = register(
		"brown_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BROWN, settings), createNetheriteShulkerBoxSettings(MapColor.BROWN)
	);
	public static final Block GREEN_NETHERITE_SHULKER_BOX = register(
		"green_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.GREEN, settings), createNetheriteShulkerBoxSettings(MapColor.GREEN)
	);
	public static final Block RED_NETHERITE_SHULKER_BOX = register(
		"red_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.RED, settings), createNetheriteShulkerBoxSettings(MapColor.RED)
	);
	public static final Block BLACK_NETHERITE_SHULKER_BOX = register(
		"black_netherite_shulker_box", settings -> new NetheriteShulkerBoxBlock(DyeColor.BLACK, settings), createNetheriteShulkerBoxSettings(MapColor.BLACK)
	);

    private static AbstractBlock.Settings createNetheriteShulkerBoxSettings(MapColor mapColor) {
		return AbstractBlock.Settings.create()
			.mapColor(mapColor)
			.solid()
			.strength(2.0f, 1200.0f)
			.dynamicBounds()
			.nonOpaque()
			.suffocates(SHULKER_BOX_SUFFOCATES_PREDICATE)
			.blockVision(SHULKER_BOX_SUFFOCATES_PREDICATE)
			.pistonBehavior(PistonBehavior.DESTROY);
			
	}

	public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		Block block = (Block)factory.apply(settings.registryKey(key));
		return Registry.register(Registries.BLOCK, key, block);
	}

	public static Block register(RegistryKey<Block> key, AbstractBlock.Settings settings) {
		return register(key, Block::new, settings);
	}

	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(HarousNetheriteShulkers.MOD_ID, id));
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return register(keyOf(id), factory, settings);
	}

}
