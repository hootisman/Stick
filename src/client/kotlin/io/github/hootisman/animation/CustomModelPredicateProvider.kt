package io.github.hootisman.animation

import io.github.hootisman.StickMain
import io.github.hootisman.item.StickItems
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.util.Identifier

object CustomModelPredicateProvider {
    /**
     * for registering predicates of item models
     */
    init {
        ModelPredicateProviderRegistry.register(StickItems.GEOPICK, Identifier(StickMain.MOD_ID, "picking"))
        { stack, world, entity, seed ->
            if (entity == null || entity.activeItem != stack)
                0.0f
            else
                (entity.itemUseTimeLeft % 10) / 10.0f
        }
    }
}