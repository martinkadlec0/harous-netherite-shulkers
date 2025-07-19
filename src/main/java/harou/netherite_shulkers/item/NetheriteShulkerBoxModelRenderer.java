package harou.netherite_shulkers.item;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntityRenderer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxModelRenderer implements SimpleSpecialModelRenderer {
	private final NetheriteShulkerBoxBlockEntityRenderer blockEntityRenderer;
	private final float openness;
	private final Direction orientation;
	private final SpriteIdentifier textureId;

	public NetheriteShulkerBoxModelRenderer(NetheriteShulkerBoxBlockEntityRenderer blockEntityRenderer, float openness, Direction facing, SpriteIdentifier textureId) {
		this.blockEntityRenderer = blockEntityRenderer;
		this.openness = openness;
		this.orientation = facing;
		this.textureId = textureId;
	}

	@Override
	public void render(
		ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint
	) {
		this.blockEntityRenderer.render(matrices, vertexConsumers, light, overlay, this.orientation, this.openness, this.textureId);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(Identifier texture, float openness, Direction facing) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<NetheriteShulkerBoxModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Identifier.CODEC.fieldOf("texture").forGetter(NetheriteShulkerBoxModelRenderer.Unbaked::texture),
					Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(NetheriteShulkerBoxModelRenderer.Unbaked::openness),
					Direction.CODEC.optionalFieldOf("orientation", Direction.UP).forGetter(NetheriteShulkerBoxModelRenderer.Unbaked::facing)
				)
				.apply(instance, NetheriteShulkerBoxModelRenderer.Unbaked::new)
		);

		public Unbaked() {
			this(Identifier.of(HarousNetheriteShulkers.MOD_ID, "netherite_shulker"), 0.0F, Direction.UP);
		}

		public Unbaked(DyeColor color) {
			this(TexturedRenderLayers.createShulkerId(color), 0.0F, Direction.UP);
		}

		@Override
		public MapCodec<NetheriteShulkerBoxModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new NetheriteShulkerBoxModelRenderer(
				new NetheriteShulkerBoxBlockEntityRenderer(entityModels), this.openness, this.facing, TexturedRenderLayers.createShulkerBoxTextureId(this.texture)
			);
		}
	}
}
