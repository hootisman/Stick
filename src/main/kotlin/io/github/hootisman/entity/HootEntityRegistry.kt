package io.github.hootisman.entity

import io.github.hootisman.Stick
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object HootEntityRegistry {
    val POTION_SPELL: EntityType<PotionSpellEntity>? = registerEntityType("potion_spell", EntityType.Builder.create(::PotionSpellEntity,SpawnGroup.MISC)
        .setDimensions(0.5f,0.5f)
        .maxTrackingRange(4)
        .trackingTickInterval(20)
    )

    fun <T : Entity?> registerEntityType(name: String, type: EntityType.Builder<T>) = Registry.register(Registries.ENTITY_TYPE, Identifier(Stick.MOD_ID, name), type.build(name))

    fun addEntityTypes() = println("Adding Stick mod entity types...")
}