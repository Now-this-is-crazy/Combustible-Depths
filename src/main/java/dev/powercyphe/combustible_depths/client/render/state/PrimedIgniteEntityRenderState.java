package dev.powercyphe.combustible_depths.client.render.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;

public class PrimedIgniteEntityRenderState extends EntityRenderState {
    public BlockModelRenderState blockState = new BlockModelRenderState();
    // public BlockState blockState = CDBlocks.IGNITE.defaultBlockState();

    public float fuseProgress = 0F;
    public Vec3 shakiness = Vec3.ZERO;
}
