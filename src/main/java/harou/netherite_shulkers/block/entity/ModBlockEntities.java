package harou.netherite_shulkers.block.entity;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
	public static void initialize() {}
	
	public static final BlockEntityType<NetheriteShulkerBoxBlockEntity> NETHERITE_SHULKER_BOX = register(
		"netherite_shulker_box",
		FabricBlockEntityTypeBuilder.<NetheriteShulkerBoxBlockEntity>create(
			(pos, state) -> {
				Block block = state.getBlock();
				DyeColor color = null;
				if (block instanceof NetheriteShulkerBoxBlock shulker) {
					color = shulker.getColor();
				}
				return new NetheriteShulkerBoxBlockEntity(color, pos, state);
			},
			ModBlocks.NETHERITE_SHULKER_BOX,
			ModBlocks.WHITE_NETHERITE_SHULKER_BOX,
			ModBlocks.ORANGE_NETHERITE_SHULKER_BOX,
			ModBlocks.MAGENTA_NETHERITE_SHULKER_BOX,
			ModBlocks.LIGHT_BLUE_NETHERITE_SHULKER_BOX,
			ModBlocks.YELLOW_NETHERITE_SHULKER_BOX,
			ModBlocks.LIME_NETHERITE_SHULKER_BOX,
			ModBlocks.PINK_NETHERITE_SHULKER_BOX,
			ModBlocks.GRAY_NETHERITE_SHULKER_BOX,
			ModBlocks.LIGHT_GRAY_NETHERITE_SHULKER_BOX,
			ModBlocks.CYAN_NETHERITE_SHULKER_BOX,
			ModBlocks.PURPLE_NETHERITE_SHULKER_BOX,
			ModBlocks.BLUE_NETHERITE_SHULKER_BOX,
			ModBlocks.BROWN_NETHERITE_SHULKER_BOX,
			ModBlocks.GREEN_NETHERITE_SHULKER_BOX,
			ModBlocks.RED_NETHERITE_SHULKER_BOX,
			ModBlocks.BLACK_NETHERITE_SHULKER_BOX
		).build()
	);

	public static BlockEntityType<NetheriteShulkerBoxBlockEntity> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType<NetheriteShulkerBoxBlockEntity> blockEntityType) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, blockEntityType);
	}

	private static ResourceKey<BlockEntityType<?>> keyOf(String id) {
		return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, id));
	}

	private static BlockEntityType<NetheriteShulkerBoxBlockEntity> register(String id, BlockEntityType<NetheriteShulkerBoxBlockEntity> blockEntityType) {
		return register(keyOf(id), blockEntityType);
	}
} 