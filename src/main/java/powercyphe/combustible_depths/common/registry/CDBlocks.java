package powercyphe.combustible_depths.common.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import powercyphe.combustible_depths.common.CombustibleDepths;
import powercyphe.combustible_depths.common.block.IgniteBlock;

import java.util.function.Function;

public class CDBlocks {

    public static final Block IGNITE = register("ignite", IgniteBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(21F, 1200F)
                    .lightLevel(state -> 3)
                    .mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.BASALT)
                    .instrument(NoteBlockInstrument.BASEDRUM)
    );

    public static final Block SOUL_IGNITE = register("soul_ignite", IgniteBlock::new,
            BlockBehaviour.Properties.ofFullCopy(IGNITE)
    );

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register(entries -> {
                    entries.addAfter(Blocks.SMOOTH_BASALT,
                            IGNITE,
                            SOUL_IGNITE
                    );
                });
    }

    public static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFunction, BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, CombustibleDepths.id(id));
        Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, blockFunction.apply(properties.setId(blockKey)));

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, CombustibleDepths.id(id));
        Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
        return block;
    }
}
