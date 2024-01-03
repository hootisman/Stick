package hootisman.stick.init

import hootisman.stick.StickMod
import hootisman.stick.entity.render.PotionSpellEntityRenderer
import net.minecraft.core.registries.Registries
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.registries.DeferredRegister

object StickEntityRenderers {
    @SubscribeEvent
    fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers){
        event.registerEntityRenderer(StickEntities.POTION_SPELL.get(), ::PotionSpellEntityRenderer)
    }
}