package hootisman.stick.block

import hootisman.stick.StickMod
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.registries.DeferredRegister

object StickBlocks {
    val BLOCKS = DeferredRegister.createBlocks(StickMod.MODID)

    val EXAMPLE_BLOCK =
        BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE))
}