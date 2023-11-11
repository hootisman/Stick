package io.github.hootisman.entity

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType

object StickEntityRenderers {
    val POTION_SPELL_RENDERER = registerEntityRenderer(StickEntities.POTION_SPELL) { context -> PotionSpellEntityRenderer(context)}

    fun <T: Entity?> registerEntityRenderer(type: EntityType<T>?, factory: EntityRendererFactory<T>) = EntityRendererRegistry.register(type, factory)

    fun addEntityRenderers() = println("Adding Stick Mod entity renderers ...")
}