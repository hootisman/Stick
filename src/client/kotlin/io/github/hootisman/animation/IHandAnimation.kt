package io.github.hootisman.animation

import io.github.hootisman.util.CustomAnimatedItem
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm

interface IHandAnimation {
    val ANIM_KEY: CustomAnimatedItem.AnimationKey

    fun doAnimation(playerEntity: ClientPlayerEntity, heldItemRenderer: HeldItemRenderer, matrices: MatrixStack, tickDelta: Float,
                    arm: Arm, stack: ItemStack, equipProgress: Float = 0.0f, swingProgress: Float = 0.0f)
}