package harou.netherite_shulkers.block.entity;

import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;

import java.util.Set;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.ShulkerBoxBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<NetheriteShulkerBoxBlockEntity, ShulkerBoxBlockEntityRenderState> {
	private final SpriteHolder materials;
	private final NetheriteShulkerBoxBlockModel model;

	public NetheriteShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this(ctx.loadedEntityModels(), ctx.spriteHolder());
	}

	public NetheriteShulkerBoxBlockEntityRenderer(SpecialModelRenderer.BakeContext ctx) {
		this(ctx.entityModelSet(), ctx.spriteHolder());
	 }

	public NetheriteShulkerBoxBlockEntityRenderer(LoadedEntityModels models, SpriteHolder materials) {
		this.materials = materials;
		this.model = new NetheriteShulkerBoxBlockModel(models.getModelPart(EntityModelLayers.SHULKER_BOX));
	}

	public ShulkerBoxBlockEntityRenderState createRenderState() {
		return new ShulkerBoxBlockEntityRenderState();
	}

	public void updateRenderState(
		NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity,
		ShulkerBoxBlockEntityRenderState shulkerBoxBlockEntityRenderState,
		float f,
		Vec3d vec3d,
		@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.updateRenderState(shulkerBoxBlockEntity, shulkerBoxBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		shulkerBoxBlockEntityRenderState.facing = shulkerBoxBlockEntity.getCachedState().get(NetheriteShulkerBoxBlock.FACING, Direction.UP);
		shulkerBoxBlockEntityRenderState.dyeColor = shulkerBoxBlockEntity.getColor();
		shulkerBoxBlockEntityRenderState.animationProgress = shulkerBoxBlockEntity.getAnimationProgress(f);
	}

	public void render(
		ShulkerBoxBlockEntityRenderState shulkerBoxBlockEntityRenderState,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		DyeColor dyeColor = shulkerBoxBlockEntityRenderState.dyeColor;
		SpriteIdentifier spriteIdentifier;
		if (dyeColor == null) {
			spriteIdentifier = ModTexturedRenderLayers.NETHERITE_SHULKER_TEXTURE_ID;
		} else {
			spriteIdentifier = ModTexturedRenderLayers.COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.get(dyeColor);
		}

		this.render(
			matrixStack,
			orderedRenderCommandQueue,
			shulkerBoxBlockEntityRenderState.lightmapCoordinates,
			OverlayTexture.DEFAULT_UV,
			shulkerBoxBlockEntityRenderState.facing,
			shulkerBoxBlockEntityRenderState.animationProgress,
			shulkerBoxBlockEntityRenderState.crumblingOverlay,
			spriteIdentifier,
			0
		);
	}

	public void render(
		MatrixStack matrices,
		OrderedRenderCommandQueue queue,
		int light,
		int overlay,
		Direction facing,
		float openness,
		@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay,
		SpriteIdentifier spriteId,
		int i
	) {
		matrices.push();
		this.setTransforms(matrices, facing, openness);
		queue.submitModel(
			this.model, openness, matrices, spriteId.getRenderLayer(this.model::getLayer), light, overlay, -1, this.materials.getSprite(spriteId), i, crumblingOverlay
		);
		matrices.pop();
	}

	private void setTransforms(MatrixStack matrices, Direction facing, float openness) {
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.scale(0.9995F, 0.9995F, 0.9995F);
		matrices.multiply(facing.getRotationQuaternion());
		matrices.scale(1.0F, -1.0F, -1.0F);
		matrices.translate(0.0F, -1.0F, 0.0F);
		this.model.setAngles(openness);
	}

	public void collectVertices(Direction facing, float openness, Set<Vector3f> vertices) {
		MatrixStack matrixStack = new MatrixStack();
		this.setTransforms(matrixStack, facing, openness);
		this.model.getRootPart().collectVertices(matrixStack, vertices);
	}

	@Environment(EnvType.CLIENT)
	static class NetheriteShulkerBoxBlockModel extends Model<Float> {
		private final ModelPart lid;

		public NetheriteShulkerBoxBlockModel(ModelPart root) {
			super(root, RenderLayer::getEntityCutoutNoCull);
			this.lid = root.getChild("lid");
		}

		public void setAngles(Float openness) {
			super.setAngles(openness);
			this.lid.setOrigin(0.0F, 24.0F - openness * 0.5F * 16.0F, 0.0F);
			this.lid.yaw = 270.0F * openness * (float) (Math.PI / 180.0);
		}
	}
}
