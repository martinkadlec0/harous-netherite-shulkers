package harou.netherite_shulkers.block.entity;

import org.jetbrains.annotations.Nullable;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class NetheriteShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {

    public NetheriteShulkerBoxBlockEntity(@Nullable DyeColor color, BlockPos pos, BlockState state) {
		super(color, pos, state);
		// this.type = ModBlockEntities.NETHERITE_SHULKER_BOX;
	}

	public NetheriteShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		// this.type = ModBlockEntities.NETHERITE_SHULKER_BOX;
	}

	@Override
	public boolean supports(BlockState state) {
		return ModBlockEntities.NETHERITE_SHULKER_BOX.supports(state);
	}

    @Override
	protected Text getContainerName() {
		return Text.translatable("container.netheriteShulkerBox");
	}
} 