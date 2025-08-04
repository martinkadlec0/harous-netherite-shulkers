package harou.netherite_shulkers.block.entity;

import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<NetheriteShulkerBoxBlockEntity> {
	private final NetheriteShulkerBoxBlockEntityRenderer.NetheriteShulkerBoxBlockModel model;

	public NetheriteShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this(ctx.getLoadedEntityModels());
	}

	public NetheriteShulkerBoxBlockEntityRenderer(LoadedEntityModels models) {
		this.model = new NetheriteShulkerBoxBlockEntityRenderer.NetheriteShulkerBoxBlockModel(models.getModelPart(EntityModelLayers.SHULKER_BOX));
	}

	public void render(
		NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, Vec3d vec3d
	) {
		Direction direction = shulkerBoxBlockEntity.getCachedState().get(NetheriteShulkerBoxBlock.FACING, Direction.UP);
		DyeColor dyeColor = shulkerBoxBlockEntity.getColor();
		SpriteIdentifier spriteIdentifier;
		if (dyeColor == null) {
			spriteIdentifier = ModTexturedRenderLayers.NETHERITE_SHULKER_TEXTURE_ID;
		} else {
			spriteIdentifier = ModTexturedRenderLayers.COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.get(dyeColor);
		}

		float g = shulkerBoxBlockEntity.getAnimationProgress(f);
		this.render(matrixStack, vertexConsumerProvider, i, j, direction, g, spriteIdentifier);
	}

	public void render(
		MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Direction facing, float openness, SpriteIdentifier textureId
	) {
		matrices.push();
		this.setTransforms(matrices, facing, openness);
		VertexConsumer vertexConsumer = textureId.getVertexConsumer(vertexConsumers, this.model::getLayer);
		this.model.render(matrices, vertexConsumer, light, overlay);
		matrices.pop();
	}

	private void setTransforms(MatrixStack matrices, Direction facing, float openness) {
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.scale(0.9995F, 0.9995F, 0.9995F);
		matrices.multiply(facing.getRotationQuaternion());
		matrices.scale(1.0F, -1.0F, -1.0F);
		matrices.translate(0.0F, -1.0F, 0.0F);
		this.model.animateLid(openness);
	}

	public void collectVertices(Direction facing, float openness, Set<Vector3f> vertices) {
		MatrixStack matrixStack = new MatrixStack();
		this.setTransforms(matrixStack, facing, openness);
		this.model.getRootPart().collectVertices(matrixStack, vertices);
	}

	@Environment(EnvType.CLIENT)
	static class NetheriteShulkerBoxBlockModel extends Model {
		private final ModelPart lid;

		public NetheriteShulkerBoxBlockModel(ModelPart root) {
			super(root, RenderLayer::getEntityCutoutNoCull);
			this.lid = root.getChild("lid");
		}

		public void animateLid(float openness) {
			this.lid.setOrigin(0.0F, 24.0F - openness * 0.5F * 16.0F, 0.0F);
			this.lid.yaw = 270.0F * openness * (float) (Math.PI / 180.0);
		}
	}
}
