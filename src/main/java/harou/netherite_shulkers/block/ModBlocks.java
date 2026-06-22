package harou.netherite_shulkers.block;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
	public static void initialize() {}
	
	private static final BlockBehaviour.StatePredicate SHULKER_BOX_SUFFOCATES_PREDICATE = (state, world, pos) -> world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity
		? shulkerBoxBlockEntity.isClosed()
		: true;
	
	public static final Block NETHERITE_SHULKER_BOX = register(
		ModBlockItemIds.NETHERITE_SHULKER_BOX, settings -> new NetheriteShulkerBoxBlock(null, settings), createNetheriteShulkerBoxSettings(MapColor.COLOR_PURPLE)
	);
	public static final ColorCollection<Block> DYED_NETHERITE_SHULKER_BOX = ColorCollection.registerBlocks(
		ModBlockItemIds.DYED_NETHERITE_SHULKER_BOX,
		ModBlocks::register,
		NetheriteShulkerBoxBlock::new,
		color -> createNetheriteShulkerBoxSettings(color == DyeColor.PURPLE ? MapColor.TERRACOTTA_PURPLE : color.getMapColor())
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

	public static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
		return register(id.block(), factory, settings);
	}

	public static Block register(ResourceKey<Block> key, BlockBehaviour.Properties settings) {
		return register(key, Block::new, settings);
	}

}
