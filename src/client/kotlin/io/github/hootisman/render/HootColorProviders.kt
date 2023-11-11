package io.github.hootisman.render

import io.github.hootisman.item.HootItemRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl
import net.minecraft.potion.PotionUtil

object HootColorProviders {
    fun registerColorProviders(){
        ColorProviderRegistry.ITEM.register({
            stack, tintIndex -> if (tintIndex == 0) PotionUtil.getColor(stack) else -1
        }, HootItemRegistry.STICK)
    }
}