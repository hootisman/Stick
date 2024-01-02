package hootisman.stick.init

import hootisman.stick.StickMod
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BrushableBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.neoforged.neoforge.registries.DeferredRegister

object StickBlocks {
    val BLOCKS = DeferredRegister.createBlocks(StickMod.MODID)

//    val EXAMPLE_BLOCK =
//        BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE))
    val SUSPICIOUS_STONE = BLOCKS.register("suspicious_stone") {
        _ -> BrushableBlock(Blocks.STONE,
        SoundEvents.BRUSH_GENERIC,
        SoundEvents.STONE_BREAK,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(1.5F, 6.0F)
            .sound(SoundType.STONE)
            .pushReaction(PushReaction.DESTROY)
)
    }
}