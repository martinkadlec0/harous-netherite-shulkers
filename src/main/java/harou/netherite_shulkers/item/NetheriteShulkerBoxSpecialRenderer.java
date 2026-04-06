package harou.netherite_shulkers.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3fc;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.entity.ModTexturedRenderLayers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxRenderer;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxSpecialRenderer implements NoDataSpecialModelRenderer {
	private final NetheriteShulkerBoxRenderer shulkerBoxRenderer;
	private final float openness;
	private final SpriteId sprite;

	public NetheriteShulkerBoxSpecialRenderer(
		final NetheriteShulkerBoxRenderer shulkerBoxRenderer,
		final float openness,
		final SpriteId sprite
	) {
		this.shulkerBoxRenderer = shulkerBoxRenderer;
		this.openness = openness;
		this.sprite = sprite;
	}

	@Override
	public void submit(
		final PoseStack poseStack,
		final SubmitNodeCollector submitNodeCollector,
		final int lightCoords,
		final int overlayCoords,
		final boolean hasFoil,
		final int outlineColor
	) {
		this.shulkerBoxRenderer.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, this.openness, null, this.sprite, outlineColor);
	}

	@Override
	public void getExtents(final Consumer<Vector3fc> output) {
		this.shulkerBoxRenderer.getExtents(this.openness, output);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(Identifier texture, float openness) implements NoDataSpecialModelRenderer.Unbaked {
		public static final MapCodec<NetheriteShulkerBoxSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> i.group(
					Identifier.CODEC.fieldOf("texture").forGetter(NetheriteShulkerBoxSpecialRenderer.Unbaked::texture),
					Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(NetheriteShulkerBoxSpecialRenderer.Unbaked::openness)
				)
				.apply(i, NetheriteShulkerBoxSpecialRenderer.Unbaked::new)
		);

		public Unbaked() {
			this(
				Identifier.fromNamespaceAndPath(
					HarousNetheriteShulkers.MOD_ID,
					"shulker"
				),
				0.0F
			);
		}

		public Unbaked(DyeColor color) {
			this(
				Identifier.fromNamespaceAndPath(
					HarousNetheriteShulkers.MOD_ID,
					String.format("shulker_%s", color.getName())
				),
				0.0F
			);
		}

		@Override
		public MapCodec<NetheriteShulkerBoxSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public NetheriteShulkerBoxSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
			return new NetheriteShulkerBoxSpecialRenderer(
				new NetheriteShulkerBoxRenderer(context),
				this.openness,
				ModTexturedRenderLayers.NETHERITE_SHULKER_SPRITE_MAPPER.apply(this.texture)
			);
		}
	}
}
