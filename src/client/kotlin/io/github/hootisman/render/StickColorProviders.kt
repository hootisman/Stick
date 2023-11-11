package io.github.hootisman.render

import io.github.hootisman.item.StickItems
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.potion.PotionUtil

object StickColorProviders {
    fun registerColorProviders(){
        ColorProviderRegistry.ITEM.register({
            stack, tintIndex -> if (tintIndex == 0) PotionUtil.getColor(stack) else -1
        }, StickItems.STICK)
    }
}