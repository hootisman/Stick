package hootisman.stick.init

import hootisman.stick.StickMod
import hootisman.stick.entity.PotionSpellEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object StickEntities {

    val ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, StickMod.MODID)

    val POTION_SPELL = register("potion_spell",
        EntityType.Builder.of(::PotionSpellEntity, MobCategory.MISC)
            .sized(0.5f,0.5f)
            .clientTrackingRange(4)
            .updateInterval(10)
    )
    private fun <T : Entity> register(name: String, builder: EntityType.Builder<T>): DeferredHolder<EntityType<*>, EntityType<T>> {
        return ENTITIES.register(name) {
            _ -> builder.build(StickMod.MODID + ":" + name)
        }
    }
}