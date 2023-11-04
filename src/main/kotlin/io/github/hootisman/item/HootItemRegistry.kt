package io.github.hootisman.item

import io.github.hootisman.Stick
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object HootItemRegistry {
    val STICK = registerItem("stick", StickItem(FabricItemSettings()))

    private fun registerItem(name: String, item: Item): Item = Registry.register(Registries.ITEM, Identifier(Stick.MOD_ID, name), item)

    fun addModItems() = println("Adding Stick items!!!!")
}