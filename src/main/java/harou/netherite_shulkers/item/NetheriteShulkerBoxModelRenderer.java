package harou.netherite_shulkers.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.entity.ModTexturedRenderLayers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxBlockEntityRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxModelRenderer implements SimpleSpecialModelRenderer {
	private final NetheriteShulkerBoxBlockEntityRenderer blockEntityRenderer;
	private final float openness;
	private final Direction facing;
	private final SpriteIdentifier textureId;

	public NetheriteShulkerBoxModelRenderer(NetheriteShulkerBoxBlockEntityRenderer blockEntityRenderer, float openness, Direction facing, SpriteIdentifier textureId) {
		this.blockEntityRenderer = blockEntityRenderer;
		this.openness = openness;
		this.facing = facing;
		this.textureId = textureId;
	}

	@Override
	public void render(ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
		this.blockEntityRenderer.render(matrices, vertexConsumers, light, overlay, this.facing, this.openness, this.textureId);
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
			this(Identifier.of(HarousNetheriteShulkers.MOD_ID, "shulker"), 0.0F, Direction.UP);
		}

		public Unbaked(DyeColor color) {
			this(Identifier.of(HarousNetheriteShulkers.MOD_ID, "shulker_" + color.getId()), 0.0F, Direction.UP);
		}

		@Override
		public MapCodec<NetheriteShulkerBoxModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new NetheriteShulkerBoxModelRenderer(
				new NetheriteShulkerBoxBlockEntityRenderer(entityModels), this.openness, this.facing, ModTexturedRenderLayers.NETHERITE_SHULKER_SPRITE_MAPPER.map(this.texture)
			);
		}
	}
}
