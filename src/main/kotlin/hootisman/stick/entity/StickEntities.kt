package hootisman.stick.entity

import hootisman.stick.StickMod
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object StickEntities {

    val ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, StickMod.MODID)

    val POTION_SPELL = this.register("potion_spell",
        EntityType.Builder.of(::PotionSpellEntity, MobCategory.MISC)
            .sized(0.5f,0.5f)
            .setTrackingRange(4)
            .setUpdateInterval(20)
    )
    private fun <T : Entity> register(name: String, builder: EntityType.Builder<T>): DeferredHolder<EntityType<*>, EntityType<T>> {
        return ENTITIES.register(name) {
            _ -> builder.build(StickMod.MODID + ":" + name)
        }
    }
}