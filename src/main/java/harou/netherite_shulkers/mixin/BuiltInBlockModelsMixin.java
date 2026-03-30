package harou.netherite_shulkers.mixin;

import harou.netherite_shulkers.block.ModBlocks;
import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxRenderer;
import harou.netherite_shulkers.item.NetheriteShulkerBoxSpecialRenderer;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.SpecialBlockModelWrapper;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.math.Transformation;
import java.util.Optional;

@Mixin(BuiltInBlockModels.class)
public class BuiltInBlockModelsMixin {

  @Inject(method = "addDefaults", at = @At("TAIL"))
  private static void addNetheriteShulkerBoxes(BuiltInBlockModels.Builder builder, CallbackInfo ci) {
    // For default shulker
    builder.put(
      BuiltInBlockModels.specialModelWithPropertyDispatch(
        ShulkerBoxBlock.FACING,
        facing -> modSpecial(
          new NetheriteShulkerBoxSpecialRenderer.Unbaked(),
          NetheriteShulkerBoxRenderer.modelTransform(facing)
        )
      ),
      ModBlocks.NETHERITE_SHULKER_BOX
    );

    // For colors
    for (DyeColor color : DyeColor.values()) {
      builder.put(
        BuiltInBlockModels.specialModelWithPropertyDispatch(
          ShulkerBoxBlock.FACING,
          facing -> modSpecial(
            new NetheriteShulkerBoxSpecialRenderer.Unbaked(color),
            NetheriteShulkerBoxRenderer.modelTransform(facing)
          )
        ),
        NetheriteShulkerBoxBlock.getBlockByColor(color)
      );
    }
  }

  private static BlockModel.Unbaked modSpecial(
    final SpecialModelRenderer.Unbaked<?> model,
    final Transformation transformation
  ) {
    return new SpecialBlockModelWrapper.Unbaked<>(model, Optional.of(transformation));
  }
}