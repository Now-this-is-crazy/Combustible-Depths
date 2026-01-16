package powercyphe.combustible_depths.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import powercyphe.combustible_depths.common.entity.PrimedIgniteEntity;

public class PrimedIgniteEntityRenderer extends EntityRenderer<PrimedIgniteEntity> {
    private final BlockRenderDispatcher dispatcher;

    public PrimedIgniteEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public ResourceLocation getTextureLocation(PrimedIgniteEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(PrimedIgniteEntity entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        BlockState blockState = entity.getBlockState();
        float fuseProgress = Mth.lerp(tickDelta, entity.getFuseTime(), entity.getFuseTime()+1) / (float) PrimedIgniteEntity.FUSE_TIME_MAX;

        Vec3 shakiness = Vec3.ZERO;
        if (fuseProgress - 0.3F > 0) {
            float progress = (fuseProgress - 0.3F) / 0.7F;
            double sin = Math.sin(progress * Math.PI * 360) * 0.1F;

            shakiness = new Vec3(
                    entity.shakiness.x() * sin,
                    entity.shakiness.y() * sin,
                    entity.shakiness.z() * sin
            );
        }

        poseStack.pushPose();

        float size = 1 + Math.max(0F, fuseProgress - 0.9F) * 4F;
        poseStack.translate(-0.5 - (size - 1) / 2, -(size - 1) / 2, -0.5 - (size - 1) / 2);
        poseStack.scale(size, size, size);

        poseStack.translate(shakiness.x(), shakiness.y(), shakiness.z());

        float overlayU = Math.max(0, fuseProgress - 0.5F) * 2;
        this.dispatcher.renderSingleBlock(blockState, poseStack, multiBufferSource, light, overlayU > 0
                ? OverlayTexture.pack(OverlayTexture.u(overlayU), 10) : OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        
        super.render(entity, yaw, tickDelta, poseStack, multiBufferSource, light);
    }
}
