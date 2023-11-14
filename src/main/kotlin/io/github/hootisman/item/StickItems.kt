package io.github.hootisman.item

import io.github.hootisman.StickMain
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object StickItems {
    val STICK = registerItem("stick", StickItem(FabricItemSettings()))
    val GEOPICK = registerItem("geopick", GeopickItem(FabricItemSettings()))
    private fun registerItem(name: String, item: Item): Item = Registry.register(Registries.ITEM, Identifier(StickMain.MOD_ID, name), item)

    fun addItems() = println("Adding Stick Mod items... ")
}