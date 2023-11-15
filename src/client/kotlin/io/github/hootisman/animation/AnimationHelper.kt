package io.github.hootisman.animation

import io.github.hootisman.mixin.client.HeldItemRendererInvoker
import io.github.hootisman.util.CustomAnimatedItem
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper

object AnimationHelper {

    /**
     * Custom First Person held item animation
     */
    fun doHeldItemAnimFP(heldItemRenderer: HeldItemRenderer,
                         matrices: MatrixStack,
                         tickDelta: Float,
                         arm: Arm,
                         stack: ItemStack,
                         equipProgress: Float){
        val customItem = stack.item.asItem() as CustomAnimatedItem
        val anim = HandAnimations.get(customItem.ANIM_KEY)
        anim?.doAnimation(heldItemRenderer, matrices, tickDelta, arm, stack, equipProgress)
    }

    /**
     * Default held item animation (idle)
     */
    fun doDefaultItemAnimation(heldItemRenderer: HeldItemRenderer, matrices: MatrixStack, arm: Arm, equipProgress: Float, swingProgress: Float){
        val isRightArm = arm == Arm.RIGHT

        val n = -0.4f * MathHelper.sin((MathHelper.sqrt(swingProgress) * Math.PI.toFloat()))
        val m = 0.2f * MathHelper.sin((MathHelper.sqrt(swingProgress) * (Math.PI.toFloat() * 2)))
        val f = -0.2f * MathHelper.sin((swingProgress * Math.PI.toFloat()))
        val o = if (isRightArm) 1 else -1
        matrices.translate(o.toFloat() * n, m, f)

        (heldItemRenderer as HeldItemRendererInvoker).invokeApplyEquipOffset(matrices, arm, equipProgress)
        (heldItemRenderer as HeldItemRendererInvoker).invokeApplySwingOffset(matrices, arm, swingProgress)
    }

}