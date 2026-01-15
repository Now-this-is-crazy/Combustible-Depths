package powercyphe.combustible_depths.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.combustible_depths.client.particle.IgniteShardParticle;

@Mixin(Particle.class)
public abstract class ParticleMixin {

    @Definition(id = "stoppedByCollision", field = "Lnet/minecraft/client/particle/Particle;stoppedByCollision:Z")
    @Expression("this.stoppedByCollision = @(?)")
    @ModifyExpressionValue(method = "move", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean combustible_depths$fixParticleCollision(boolean original) {
        Particle particle = (Particle) (Object) this;

        if (particle instanceof IgniteShardParticle) {
            return false;
        }
        return original;
    }
}
