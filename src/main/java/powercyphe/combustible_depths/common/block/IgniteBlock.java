package powercyphe.combustible_depths.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import powercyphe.combustible_depths.common.entity.PrimedIgniteEntity;
import powercyphe.combustible_depths.common.registry.CDBlocks;
import powercyphe.combustible_depths.common.registry.CDParticles;
import powercyphe.combustible_depths.common.registry.CDSounds;
import powercyphe.combustible_depths.common.registry.CDTags;

public class IgniteBlock extends RotatedPillarBlock {
    public static final IntegerProperty EXPLOSION_CHAIN_INDEX = IntegerProperty.create("explosion_chain_index", 0, PrimedIgniteEntity.EXPLOSION_CHAIN_INDEX_MAX);

    public IgniteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(EXPLOSION_CHAIN_INDEX, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EXPLOSION_CHAIN_INDEX);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        this.alertAdjacentBlocks(level, state, blockPos);

        int explosionChainIndex = state.getValue(EXPLOSION_CHAIN_INDEX);
        PrimedIgniteEntity igniteEntity = new PrimedIgniteEntity(level, blockPos, state, explosionChainIndex);

        level.removeBlock(blockPos, false);
        level.addFreshEntity(igniteEntity);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (stack.is(CDTags.IGNITES_IGNITE)) {
            activate(level, state, blockPos, 0);

            level.playSound(player, blockPos, CDSounds.IGNITE_IGNITE.value(), SoundSource.BLOCKS, 0.5F, 1F);
            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, blockPos, player, hand, blockHitResult);
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        BlockPos blockPos = hitResult.getBlockPos();
        if (projectile.isOnFire()) {
            activate(level, state, blockPos, 0);
        }
        super.onProjectileHit(level, state, hitResult, projectile);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos blockPos, Player player) {
        ItemStack heldItem = player.getMainHandItem();
        Holder<Enchantment> silkTouch = level.registryAccess().getOrThrow(Enchantments.SILK_TOUCH);

        if (heldItem.getEnchantments().getLevel(silkTouch) <= 0) {
            activate(level, state, blockPos, 0);
        }
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.isSolidRender();
    }

    public static void activate(Level level, BlockState state, BlockPos blockPos, int explosionChainIndex) {
        if (state.getBlock() instanceof IgniteBlock) {
            level.setBlockAndUpdate(blockPos, state.setValue(EXPLOSION_CHAIN_INDEX, explosionChainIndex));
            level.scheduleTick(blockPos, state.getBlock(), 3);
        }
    }

    public void alertAdjacentBlocks(Level level, BlockState state, BlockPos blockPos) {
        int index = state.getValue(EXPLOSION_CHAIN_INDEX);

        for (Direction direction : Direction.values()) {
            for (Direction oDirection : Direction.values()) {
                BlockPos adjacentPos = blockPos.relative(direction)
                        .relative(oDirection, direction != oDirection ? 1 : 0);
                BlockState adjacentState = level.getBlockState(adjacentPos);

                if (adjacentState.is(this)) {
                    activate(level, adjacentState, adjacentPos, Math.min(PrimedIgniteEntity.EXPLOSION_CHAIN_INDEX_MAX, index + 1));
                }
            }
        }
    }

    public static boolean isSoulIgnite(BlockState state) {
        return state.is(CDBlocks.SOUL_IGNITE);
    }


    public static ParticleOptions getExplosionParticle(BlockState state) {
        return (ParticleOptions) (isSoulIgnite(state) ? CDParticles.SOUL_IGNITE_EXPLOSION : CDParticles.IGNITE_EXPLOSION);
    }

    public static ParticleOptions getShardParticle(BlockState state) {
        return (ParticleOptions) (isSoulIgnite(state) ? CDParticles.SOUL_IGNITE_SHARD : CDParticles.IGNITE_SHARD);
    }
}
