package io.github.hootisman.block

import com.mojang.logging.LogUtils
import io.github.hootisman.StickMain
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.BrushableBlock
import net.minecraft.block.MapColor
import net.minecraft.block.enums.Instrument
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier

object StickBlocks {
//    val SUSPICIOUS_STONE = registerBlock("suspicious_stone", BrushableBlock(Blocks.STONE,
//        FabricBlockSettings.copyOf(Blocks.STONE).pistonBehavior(PistonBehavior.DESTROY),
//        SoundEvents.ITEM_BRUSH_BRUSHING_GENERIC,    //fixes brush sound bug
//        SoundEvents.BLOCK_STONE_BREAK))

    private fun registerBlock(name: String, block: Block): Block = Registry.register(Registries.BLOCK, Identifier(StickMain.MOD_ID, name), block)
    fun addBlocks() = LogUtils.getLogger().info("Adding stick blocks..")
}