package harou.netherite_shulkers.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.block.entity.ModTexturedRenderLayers;
import harou.netherite_shulkers.block.entity.NetheriteShulkerBoxRenderer;

import java.util.function.Consumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxSpecialRenderer implements NoDataSpecialModelRenderer {
	private final NetheriteShulkerBoxRenderer shulkerBoxRenderer;
	private final float openness;
	private final Direction orientation;
	private final Material material;

	public NetheriteShulkerBoxSpecialRenderer(NetheriteShulkerBoxRenderer shulkerBoxRenderer, float openness, Direction direction, Material material) {
		this.shulkerBoxRenderer = shulkerBoxRenderer;
		this.openness = openness;
		this.orientation = direction;
		this.material = material;
	}

	@Override
	public void submit(ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, int overlay, boolean glint, int i) {
		this.shulkerBoxRenderer.submit(poseStack, submitNodeCollector, light, overlay, this.orientation, this.openness, null, this.material, i);
	}

	@Override
	public void getExtents(Consumer<Vector3fc> consumer) {
		this.shulkerBoxRenderer.getExtents(this.orientation, this.openness, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(Identifier texture, float openness, Direction orientation) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<NetheriteShulkerBoxSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Identifier.CODEC.fieldOf("texture").forGetter(NetheriteShulkerBoxSpecialRenderer.Unbaked::texture),
					Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(NetheriteShulkerBoxSpecialRenderer.Unbaked::openness),
					Direction.CODEC.optionalFieldOf("orientation", Direction.UP).forGetter(NetheriteShulkerBoxSpecialRenderer.Unbaked::orientation)
				)
				.apply(instance, NetheriteShulkerBoxSpecialRenderer.Unbaked::new)
		);

		public Unbaked() {
			this(Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, "shulker"), 0.0F, Direction.UP);
		}

		public Unbaked(DyeColor color) {
			this(Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, "shulker_" + color.getName()), 0.0F, Direction.UP);
		}

		@Override
		public MapCodec<NetheriteShulkerBoxSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
			return new NetheriteShulkerBoxSpecialRenderer(
				new NetheriteShulkerBoxRenderer(context), this.openness, this.orientation, ModTexturedRenderLayers.NETHERITE_SHULKER_SPRITE_MAPPER.apply(this.texture)
			);
		}
	}
}
