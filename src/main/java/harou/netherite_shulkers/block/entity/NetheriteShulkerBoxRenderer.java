package harou.netherite_shulkers.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import harou.netherite_shulkers.block.NetheriteShulkerBoxBlock;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxRenderer implements BlockEntityRenderer<NetheriteShulkerBoxBlockEntity, ShulkerBoxRenderState> {
	private final MaterialSet materials;
	private final NetheriteShulkerBoxModel model;

	public NetheriteShulkerBoxRenderer(BlockEntityRendererProvider.Context context) {
		this(context.entityModelSet(), context.materials());
	}

	public NetheriteShulkerBoxRenderer(SpecialModelRenderer.BakingContext bakingContext) {
		this(bakingContext.entityModelSet(), bakingContext.materials());
	 }

	public NetheriteShulkerBoxRenderer(EntityModelSet entityModelSet, MaterialSet materialSet) {
		this.materials = materialSet;
		this.model = new NetheriteShulkerBoxModel(entityModelSet.bakeLayer(ModelLayers.SHULKER_BOX));
	}

	public ShulkerBoxRenderState createRenderState() {
		return new ShulkerBoxRenderState();
	}

	public void extractRenderState(
		NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity,
		ShulkerBoxRenderState shulkerBoxRenderState,
		float f,
		Vec3 vec3,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
	) {
		BlockEntityRenderer.super.extractRenderState(shulkerBoxBlockEntity, shulkerBoxRenderState, f, vec3, crumblingOverlay);
		shulkerBoxRenderState.direction = shulkerBoxBlockEntity.getBlockState().getValueOrElse(NetheriteShulkerBoxBlock.FACING, Direction.UP);
		shulkerBoxRenderState.color = shulkerBoxBlockEntity.getColor();
		shulkerBoxRenderState.progress = shulkerBoxBlockEntity.getProgress(f);
	}

	public void submit(
		ShulkerBoxRenderState shulkerBoxRenderState,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState cameraRenderState
	) {
		DyeColor dyeColor = shulkerBoxRenderState.color;
		Material spriteIdentifier;
		if (dyeColor == null) {
			spriteIdentifier = ModTexturedRenderLayers.NETHERITE_SHULKER_TEXTURE_ID;
		} else {
			spriteIdentifier = ModTexturedRenderLayers.COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.get(dyeColor);
		}

		this.submit(
			poseStack,
			submitNodeCollector,
			shulkerBoxRenderState.lightCoords,
			OverlayTexture.NO_OVERLAY,
			shulkerBoxRenderState.direction,
			shulkerBoxRenderState.progress,
			shulkerBoxRenderState.breakProgress,
			spriteIdentifier,
			0
		);
	}

	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int light,
		int overlay,
		Direction direction,
		float openness,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
		Material material,
		int k
	) {
		poseStack.pushPose();
		this.prepareModel(poseStack, direction, openness);
		submitNodeCollector.submitModel(
			this.model, openness, poseStack, material.renderType(this.model::renderType), light, overlay, -1, this.materials.get(material), k, crumblingOverlay
		);
		poseStack.popPose();
	}

	private void prepareModel(PoseStack poseStack, Direction direction, float openness) {
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.scale(0.9995F, 0.9995F, 0.9995F);
		poseStack.mulPose(direction.getRotation());
		poseStack.scale(1.0F, -1.0F, -1.0F);
		poseStack.translate(0.0F, -1.0F, 0.0F);
		this.model.setupAnim(openness);
	}

	public void getExtents(Direction direction, float openness, Consumer<Vector3fc> consumer) {
		PoseStack poseStack = new PoseStack();
		this.prepareModel(poseStack, direction, openness);
		this.model.root().getExtentsForGui(poseStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	static class NetheriteShulkerBoxModel extends Model<Float> {
		private final ModelPart lid;

		public NetheriteShulkerBoxModel(ModelPart modelPart) {
			super(modelPart, RenderTypes::entityCutoutNoCull);
			this.lid = modelPart.getChild("lid");
		}

		public void setupAnim(Float openness) {
			super.setupAnim(openness);
			this.lid.setPos(0.0F, 24.0F - openness * 0.5F * 16.0F, 0.0F);
			this.lid.yRot = 270.0F * openness * (float) (Math.PI / 180.0);
		}
	}
}
