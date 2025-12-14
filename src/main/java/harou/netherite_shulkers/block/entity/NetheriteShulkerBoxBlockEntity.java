package harou.netherite_shulkers.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;

public class NetheriteShulkerBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	public static final int COLUMNS = 9;
	public static final int ROWS = 3;
	public static final int CONTAINER_SIZE = 27;
	public static final int EVENT_SET_OPEN_COUNT = 1;
	public static final int OPENING_TICK_LENGTH = 10;
	public static final float MAX_LID_HEIGHT = 0.5F;
	public static final float MAX_LID_ROTATION = 270.0F;
	private static final int[] SLOTS = IntStream.range(0, 27).toArray();
	private static final Component DEFAULT_NAME = Component.translatable("container.netheriteShulkerBox");
	private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
	private int openCount;
	private NetheriteShulkerBoxBlockEntity.AnimationStatus animationStatus = NetheriteShulkerBoxBlockEntity.AnimationStatus.CLOSED;
	private float progress;
	private float progressOld;
	@Nullable
	private final DyeColor color;

	public NetheriteShulkerBoxBlockEntity(@Nullable DyeColor dyeColor, BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.NETHERITE_SHULKER_BOX, blockPos, blockState);
		this.color = dyeColor;
	}

	public NetheriteShulkerBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.NETHERITE_SHULKER_BOX, blockPos, blockState);
		this.color = blockState.getBlock() instanceof NetheriteShulkerBoxBlock netheriteShulkerBoxBlock ? netheriteShulkerBoxBlock.getColor() : null;
	}

	public static void tick(Level world, BlockPos blockPos, BlockState blockState, NetheriteShulkerBoxBlockEntity blockEntity) {
		blockEntity.updateAnimation(world, blockPos, blockState);
	}

	private void updateAnimation(Level world, BlockPos blockPos, BlockState blockState) {
		this.progressOld = this.progress;
		switch (this.animationStatus) {
			case CLOSED:
				this.progress = 0.0F;
				break;
			case OPENING:
				this.progress += 0.1F;
				if (this.progressOld == 0.0F) {
					doNeighborUpdates(world, blockPos, blockState);
				}

				if (this.progress >= 1.0F) {
					this.animationStatus = NetheriteShulkerBoxBlockEntity.AnimationStatus.OPENED;
					this.progress = 1.0F;
					doNeighborUpdates(world, blockPos, blockState);
				}

				this.moveCollidedEntities(world, blockPos, blockState);
				break;
			case OPENED:
				this.progress = 1.0F;
				break;
			case CLOSING:
				this.progress -= 0.1F;
				if (this.progressOld == 1.0F) {
					doNeighborUpdates(world, blockPos, blockState);
				}

				if (this.progress <= 0.0F) {
					this.animationStatus = NetheriteShulkerBoxBlockEntity.AnimationStatus.CLOSED;
					this.progress = 0.0F;
					doNeighborUpdates(world, blockPos, blockState);
				}
		}
	}

	public NetheriteShulkerBoxBlockEntity.AnimationStatus getAnimationStatus() {
		return this.animationStatus;
	}

	public AABB getBoundingBox(BlockState state) {
		Vec3 vec3 = new Vec3(0.5, 0.0, 0.5);
		return Shulker.getProgressAabb(1.0F, state.getValue(NetheriteShulkerBoxBlock.FACING), 0.5F * this.getProgress(1.0F), vec3);
	}

	private void moveCollidedEntities(Level world, BlockPos pos, BlockState blockState) {
		if (blockState.getBlock() instanceof NetheriteShulkerBoxBlock) {
			Direction direction = blockState.getValue(NetheriteShulkerBoxBlock.FACING);
			AABB aABB = Shulker.getProgressDeltaAabb(1.0F, direction, this.progressOld, this.progress, pos.getBottomCenter());
			List<Entity> list = world.getEntities(null, aABB);
			if (!list.isEmpty()) {
				for (Entity entity : list) {
					if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
						entity.move(
							MoverType.SHULKER_BOX,
							new Vec3(
								(aABB.getXsize() + 0.01) * direction.getStepX(),
								(aABB.getYsize() + 0.01) * direction.getStepY(),
								(aABB.getZsize() + 0.01) * direction.getStepZ()
							)
						);
					}
				}
			}
		}
	}

	@Override
	public int getContainerSize() {
		return this.itemStacks.size();
	}

	@Override
	public boolean triggerEvent(int type, int data) {
		if (type == 1) {
			this.openCount = data;
			if (data == 0) {
				this.animationStatus = NetheriteShulkerBoxBlockEntity.AnimationStatus.CLOSING;
			}

			if (data == 1) {
				this.animationStatus = NetheriteShulkerBoxBlockEntity.AnimationStatus.OPENING;
			}

			return true;
		} else {
			return super.triggerEvent(type, data);
		}
	}

	private static void doNeighborUpdates(Level level, BlockPos blockPos, BlockState blockState) {
		blockState.updateNeighbourShapes(level, blockPos, Block.UPDATE_ALL);
		level.updateNeighborsAt(blockPos, blockState.getBlock());
	}

	@Override
	public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
	}

	@Override
	public void startOpen(ContainerUser containerUser) {
		if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
			if (this.openCount < 0) {
				this.openCount = 0;
			}

			this.openCount++;
			this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
			if (this.openCount == 1) {
				this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_OPEN, this.worldPosition);
				this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public void stopOpen(ContainerUser containerUser) {
		if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
			this.openCount--;
			this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
			if (this.openCount <= 0) {
				this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_CLOSE, this.worldPosition);
				this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	protected Component getDefaultName() {
		return DEFAULT_NAME;
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		this.loadFromTag(valueInput);
	}

	@Override
	protected void saveAdditional(ValueOutput valueInput) {
		super.saveAdditional(valueInput);
		if (!this.trySaveLootTable(valueInput)) {
			ContainerHelper.saveAllItems(valueInput, this.itemStacks, false);
		}
	}

	public void loadFromTag(ValueInput valueInput) {
		this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(valueInput)) {
			ContainerHelper.loadAllItems(valueInput, this.itemStacks);
		}
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.itemStacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> nonNullList) {
		this.itemStacks = nonNullList;
	}

	@Override
	public int[] getSlotsForFace(Direction direction) {
		return SLOTS;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction) {
		return !(Block.byItem(itemStack.getItem()) instanceof NetheriteShulkerBoxBlock);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
		return true;
	}

	public float getProgress(float tickProgress) {
		return Mth.lerp(tickProgress, this.progressOld, this.progress);
	}

	@Nullable
	public DyeColor getColor() {
		return this.color;
	}

	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new ShulkerBoxMenu(syncId, playerInventory, this);
	}

	public boolean isClosed() {
		return this.animationStatus == NetheriteShulkerBoxBlockEntity.AnimationStatus.CLOSED;
	}

	public static enum AnimationStatus {
		CLOSED,
		OPENING,
		OPENED,
		CLOSING;
	}
}
