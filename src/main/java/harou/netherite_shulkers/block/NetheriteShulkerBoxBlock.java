package harou.netherite_shulkers.block;


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntity;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntity.AnimationStage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NetheriteShulkerBoxBlock extends BlockWithEntity {
	public static MapCodec<NetheriteShulkerBoxBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter(block -> Optional.ofNullable(block.color)), createSettingsCodec())
			.apply(instance, (color, settings) -> new NetheriteShulkerBoxBlock((DyeColor)color.orElse(null), settings))
	);
	public static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = VoxelShapes.createFacingShapeMap(Block.createCuboidZShape(16.0, 0.0, 1.0));
	public static final EnumProperty<Direction> FACING = FacingBlock.FACING;
	public static final Identifier CONTENTS_DYNAMIC_DROP_ID = Identifier.ofVanilla("contents");
	@Nullable
	public DyeColor color;

	@Override
	public MapCodec<NetheriteShulkerBoxBlock> getCodec() {
		return CODEC;
	}

	public NetheriteShulkerBoxBlock(@Nullable DyeColor color, Settings settings) {
		super(settings);
		this.color = color;
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NetheriteShulkerBoxBlockEntity(this.color, pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, (BlockEntityType<NetheriteShulkerBoxBlockEntity>) ModBlockEntities.NETHERITE_SHULKER_BOX, NetheriteShulkerBoxBlockEntity::tick);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world instanceof ServerWorld serverWorld
			&& world.getBlockEntity(pos) instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity
			&& canOpen(state, world, pos, shulkerBoxBlockEntity)) {
			player.openHandledScreen(shulkerBoxBlockEntity);
			player.incrementStat(Stats.OPEN_SHULKER_BOX);
			PiglinBrain.onGuardedBlockInteracted(serverWorld, player, true);
		}

		return ActionResult.SUCCESS;
	}

	private static boolean canOpen(BlockState state, World world, BlockPos pos, NetheriteShulkerBoxBlockEntity entity) {
		if (entity.getAnimationStage() != AnimationStage.CLOSED) {
			return true;
		} else {
			Box box = ShulkerEntity.calculateBoundingBox(1.0F, state.get(FACING), 0.0F, 0.5F, pos.toBottomCenterPos()).contract(1.0E-6);
			return world.isSpaceEmpty(box);
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getSide());
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity) {
			if (!world.isClient && player.isCreative() && !netheriteShulkerBoxBlockEntity.isEmpty()) {
				ItemStack itemStack = getItemStack(this.getColor());
				itemStack.applyComponentsFrom(blockEntity.createComponentMap());
				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			} else {
				netheriteShulkerBoxBlockEntity.generateLoot(player);
			}
		}

		return super.onBreak(world, pos, state, player);
	}

	@Override
	protected List<ItemStack> getDroppedStacks(BlockState state, net.minecraft.loot.context.LootWorldContext.Builder builder) {
		BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity) {
			builder = builder.addDynamicDrop(CONTENTS_DYNAMIC_DROP_ID, lootConsumer -> {
				for (int i = 0; i < netheriteShulkerBoxBlockEntity.size(); i++) {
					lootConsumer.accept(netheriteShulkerBoxBlockEntity.getStack(i));
				}
			});
		}

		return super.getDroppedStacks(state, builder);
	}

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		ItemScatterer.onStateReplaced(state, world, pos);
	}

	@Override
	protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return world.getBlockEntity(pos) instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity && !netheriteShulkerBoxBlockEntity.suffocates()
			? (VoxelShape)SHAPES_BY_DIRECTION.get(((Direction)state.get(FACING)).getOpposite())
			: VoxelShapes.fullCube();
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return world.getBlockEntity(pos) instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity
			? VoxelShapes.cuboid(netheriteShulkerBoxBlockEntity.getBoundingBox(state))
			: VoxelShapes.fullCube();
	}

	@Override
	protected boolean isTransparent(BlockState state) {
		return false;
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	public static Block get(@Nullable DyeColor dyeColor) {
		if (dyeColor == null) {
			return ModBlocks.NETHERITE_SHULKER_BOX;
		} else {
			return switch (dyeColor) {
				case WHITE -> ModBlocks.WHITE_NETHERITE_SHULKER_BOX;
				case ORANGE -> ModBlocks.ORANGE_NETHERITE_SHULKER_BOX;
				case MAGENTA -> ModBlocks.MAGENTA_NETHERITE_SHULKER_BOX;
				case LIGHT_BLUE -> ModBlocks.LIGHT_BLUE_NETHERITE_SHULKER_BOX;
				case YELLOW -> ModBlocks.YELLOW_NETHERITE_SHULKER_BOX;
				case LIME -> ModBlocks.LIME_NETHERITE_SHULKER_BOX;
				case PINK -> ModBlocks.PINK_NETHERITE_SHULKER_BOX;
				case GRAY -> ModBlocks.GRAY_NETHERITE_SHULKER_BOX;
				case LIGHT_GRAY -> ModBlocks.LIGHT_GRAY_NETHERITE_SHULKER_BOX;
				case CYAN -> ModBlocks.CYAN_NETHERITE_SHULKER_BOX;
				case BLUE -> ModBlocks.BLUE_NETHERITE_SHULKER_BOX;
				case BROWN -> ModBlocks.BROWN_NETHERITE_SHULKER_BOX;
				case GREEN -> ModBlocks.GREEN_NETHERITE_SHULKER_BOX;
				case RED -> ModBlocks.RED_NETHERITE_SHULKER_BOX;
				case BLACK -> ModBlocks.BLACK_NETHERITE_SHULKER_BOX;
				case PURPLE -> ModBlocks.PURPLE_NETHERITE_SHULKER_BOX;
			};
		}
	}

	@Nullable
	public DyeColor getColor() {
		return this.color;
	}

	public static ItemStack getItemStack(@Nullable DyeColor color) {
		return new ItemStack(get(color));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
}
