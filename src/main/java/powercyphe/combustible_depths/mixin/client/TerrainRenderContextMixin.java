package powercyphe.combustible_depths.mixin.client;

import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.combustible_depths.client.CombustibleDepthsClient;
import powercyphe.combustible_depths.common.block.IgniteBlock;

@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {

    @ModifyArgs(method = "bufferModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockStateModel;emitQuads(Lnet/fabricmc/fabric/api/renderer/v1/mesh/QuadEmitter;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;Ljava/util/function/Predicate;)V"))
    private void guh(Args args, BlockStateModel model, BlockState state, BlockPos blockPos) {
        if (state.getBlock() instanceof IgniteBlock) {
            QuadEmitter emitter = args.get(0);
            args.set(0, CombustibleDepthsClient.modify(emitter));
        }
    }
}
