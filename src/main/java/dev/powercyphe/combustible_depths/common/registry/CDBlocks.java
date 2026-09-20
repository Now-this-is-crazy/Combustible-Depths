package dev.powercyphe.combustible_depths.common.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;
import dev.powercyphe.combustible_depths.common.block.IgniteBlock;

import java.util.function.Function;

public interface CDBlocks {

    BlockItemId IGNITE_ID = id("ignite");
    Block IGNITE = register(IGNITE_ID, IgniteBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(21F, 1200F)
                    .lightLevel(state -> 3)
                    .mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.BASALT)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .forceSolidOn()
    );

    BlockItemId SOUL_IGNITE_ID = id("soul_ignite");
    Block SOUL_IGNITE = register(SOUL_IGNITE_ID, IgniteBlock::new,
            BlockBehaviour.Properties.ofFullCopy(IGNITE)
    );

    static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register(entries ->
                        entries.insertAfter(Blocks.SMOOTH_BASALT,
                                IGNITE,
                                SOUL_IGNITE
                ));
    }

    static BlockItemId id(String name) {
        Identifier id = CombustibleDepths.id(name);
        return BlockItemId.create(id, id);
    }

    static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFunction, BlockBehaviour.Properties properties) {
        Block block = Registry.register(BuiltInRegistries.BLOCK, id.block(),
                blockFunction.apply(properties.setId(id.block())));

        Registry.register(BuiltInRegistries.ITEM, id.item(), new BlockItem(block,
                new Item.Properties().useBlockDescriptionPrefix().setId(id.item())));
        return block;
    }
}
