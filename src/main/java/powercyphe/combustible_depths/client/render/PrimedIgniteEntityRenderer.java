package powercyphe.combustible_depths.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import powercyphe.combustible_depths.client.render.state.PrimedIgniteEntityRenderState;
import powercyphe.combustible_depths.common.entity.PrimedIgniteEntity;

public class PrimedIgniteEntityRenderer extends EntityRenderer<PrimedIgniteEntity, PrimedIgniteEntityRenderState> {
    public PrimedIgniteEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PrimedIgniteEntityRenderState createRenderState() {
        return new PrimedIgniteEntityRenderState();
    }

    @Override
    public void extractRenderState(PrimedIgniteEntity entity, PrimedIgniteEntityRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.blockState = entity.getBlockState();
        state.fuseProgress = Mth.lerp(tickProgress, entity.getFuseTime(), entity.getFuseTime()+1) / (float) PrimedIgniteEntity.FUSE_TIME_MAX;

        if (state.fuseProgress - 0.3F > 0) {
            float progress = (state.fuseProgress - 0.3F) / 0.7F;
            double sin = Math.sin(progress * Math.PI * 360) * 0.1F;

            state.shakiness = new Vec3(
                    entity.shakiness.x() * sin,
                    entity.shakiness.y() * sin,
                    entity.shakiness.z() * sin
            );
        } else {
            state.shakiness = Vec3.ZERO;
        }
    }

    @Override
    public void submit(PrimedIgniteEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        float size = 1 + Math.max(0F, state.fuseProgress - 0.9F) * 4F;
        poseStack.translate(-0.5 - (size - 1) / 2, -(size - 1) / 2, -0.5 - (size - 1) / 2);
        poseStack.scale(size, size, size);

        poseStack.translate(state.shakiness.x(), state.shakiness.y(), state.shakiness.z());

        float overlayU = Math.max(0, state.fuseProgress - 0.5F) * 2;
        submitNodeCollector.submitBlock(poseStack, state.blockState, state.lightCoords, overlayU > 0
                ? OverlayTexture.pack(OverlayTexture.u(overlayU), 10) : OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
    }
}
