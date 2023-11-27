package io.github.hootisman.animation.impl

import io.github.hootisman.animation.CustomAnimationHelper
import io.github.hootisman.animation.IHandAnimation
import io.github.hootisman.mixin.client.HeldItemRendererInvoker
import io.github.hootisman.util.CustomAnimatedItem
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm
import net.minecraft.util.math.RotationAxis

object GeopickAnimation : IHandAnimation {
    override val ANIM_KEY = CustomAnimatedItem.AnimationKey.GEOPICK

    override fun doAnimation(heldItemRenderer: HeldItemRenderer, matrices: MatrixStack,
                             tickDelta: Float, arm: Arm, stack: ItemStack, equipProgress: Float, swingProgress: Float) {

        val heldInvoker = heldItemRenderer as HeldItemRendererInvoker
        heldInvoker.invokeApplyEquipOffset(matrices, arm, equipProgress)

        val itemUseTimeLeft = heldInvoker.client.player!!.itemUseTimeLeft       //ticks till item finishes use
        var offset = -30.0f         //offset
        var range = -60.0f          //how far will be rotated
        val frameSpeed = 10.0f      //use time must be divisible by frameSpeed ex: time=200,speed=10,200 % 10 = 0, MUST BE 0 !!!

        if (itemUseTimeLeft >= (200 - (frameSpeed / 2.0f))){
            offset = 0.0f
            range += offset
        } else {
            //animation for when item is held down
            val rot: Float = if (arm == Arm.RIGHT) 10.0f else -10.0f
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rot))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rot))
        }
        val n : Float = CustomAnimationHelper.currentAnimDegree(itemUseTimeLeft % frameSpeed, frameSpeed, offset, range, tickDelta)
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n))
    }


}