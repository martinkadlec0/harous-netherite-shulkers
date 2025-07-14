package harou.netherite_shulkers.block;

import harou.netherite_shulkers.block.entity.ModBlockEntities;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class NetheriteShulkerBoxBlock extends ShulkerBoxBlock {

    public static final MapCodec<NetheriteShulkerBoxBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter(block -> Optional.ofNullable(block.color)), createSettingsCodec())
			.apply(instance, (color, settings) -> new NetheriteShulkerBoxBlock((DyeColor)color.orElse(null), settings))
	);

    public NetheriteShulkerBoxBlock(@Nullable DyeColor color, AbstractBlock.Settings settings) {
        super(color, settings);
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

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NetheriteShulkerBoxBlockEntity(this.color, pos, state);
    }

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, ModBlockEntities.NETHERITE_SHULKER_BOX, NetheriteShulkerBoxBlockEntity::tick);
	}
}