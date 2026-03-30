package harou.netherite_shulkers.block;


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntity;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntity.AnimationStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NetheriteShulkerBoxBlock extends ShulkerBoxBlock {
  public static MapCodec<ShulkerBoxBlock> CODEC = RecordCodecBuilder.mapCodec(
    instance -> instance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter(block -> Optional.ofNullable(block.color)), propertiesCodec())
      .apply(instance, (color, settings) -> new NetheriteShulkerBoxBlock((DyeColor)color.orElse(null), settings))
  );
  public static final Map<Direction, VoxelShape> SHAPES_OPEN_SUPPORT = Shapes.rotateAll(Block.boxZ(16.0, 0.0, 1.0));
  public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
  public static final Identifier CONTENTS = Identifier.withDefaultNamespace("contents");
  
  @Override
  public MapCodec<ShulkerBoxBlock> codec() {
    return NetheriteShulkerBoxBlock.CODEC;
  }

  public NetheriteShulkerBoxBlock(@Nullable DyeColor color, Properties settings) {
    super(color, settings);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new NetheriteShulkerBoxBlockEntity(this.color, pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
    return createTickerHelper(type, (BlockEntityType<NetheriteShulkerBoxBlockEntity>) ModBlockEntities.NETHERITE_SHULKER_BOX, NetheriteShulkerBoxBlockEntity::tick);
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos blockPos, Player player, BlockHitResult hit) {
    if (level instanceof ServerLevel serverWorld
      && level.getBlockEntity(blockPos) instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity
      && canOpen(state, level, blockPos, shulkerBoxBlockEntity)) {
      player.openMenu(shulkerBoxBlockEntity);
      player.awardStat(Stats.OPEN_SHULKER_BOX);
      PiglinAi.angerNearbyPiglins(serverWorld, player, true);
    }

    return InteractionResult.SUCCESS;
  }

  private static boolean canOpen(BlockState state, Level level, BlockPos blockPos, NetheriteShulkerBoxBlockEntity entity) {
    if (entity.getAnimationStatus() != AnimationStatus.CLOSED) {
      return true;
    } else {
      AABB box = Shulker.getProgressDeltaAabb(1.0F, state.getValue(FACING), 0.0F, 0.5F, blockPos.getBottomCenter()).deflate(1.0E-6);
      return level.noCollision(box);
    }
  }

  @Override
  public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState state, Player player) {
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (blockEntity instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity) {
      if (!level.isClientSide() && player.preventsBlockDrops() && !netheriteShulkerBoxBlockEntity.isEmpty()) {
        ItemStack itemStack = getColoredItemStack(this.getColor());
        itemStack.applyComponents(blockEntity.collectComponents());
        ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, itemStack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
      } else {
        netheriteShulkerBoxBlockEntity.unpackLootTable(player);
      }
    }

    return super.playerWillDestroy(level, blockPos, state, player);
  }

  @Override
  protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
    BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
    if (blockEntity instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity) {
      builder = builder.withDynamicDrop(CONTENTS, consumer -> {
        for (int i = 0; i < netheriteShulkerBoxBlockEntity.getContainerSize(); i++) {
          consumer.accept(netheriteShulkerBoxBlockEntity.getItem(i));
        }
      });
    }

    return super.getDrops(state, builder);
  }

  @Override
  protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos blockPos) {
    return world.getBlockEntity(blockPos) instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity && !netheriteShulkerBoxBlockEntity.isClosed()
      ? (VoxelShape)SHAPES_OPEN_SUPPORT.get(((Direction)state.getValue(FACING)).getOpposite())
      : Shapes.block();
  }

  @Override
  protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
    return blockGetter.getBlockEntity(blockPos) instanceof NetheriteShulkerBoxBlockEntity netheriteShulkerBoxBlockEntity
      ? Shapes.create(netheriteShulkerBoxBlockEntity.getBoundingBox(blockState))
      : Shapes.block();
  }

  public static Block getBlockByColor(@Nullable DyeColor dyeColor) {
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

  public static ItemStack getColoredItemStack(@Nullable DyeColor color) {
    return new ItemStack(getBlockByColor(color));
  }
}
