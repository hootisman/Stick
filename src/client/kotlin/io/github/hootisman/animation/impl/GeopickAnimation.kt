package io.github.hootisman.animation.impl

import io.github.hootisman.animation.CustomAnimHelper
import io.github.hootisman.animation.EasingFunction
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
        val itemUseTimeLeft = heldInvoker.client.player!!.itemUseTimeLeft  //ticks till item finishes use
        val rotDeg = -60.0f          //degree to rotate
        var offset = 0.0f            //offset
        val totalFrames = 10.0f      //animation time in frames
        val currentFrame = itemUseTimeLeft % totalFrames
        val currentAnimTime = CustomAnimHelper.calcAnimTime(currentFrame, totalFrames, tickDelta)
        heldInvoker.invokeApplyEquipOffset(matrices, arm, equipProgress)

        val isStartingUse: Boolean = itemUseTimeLeft >= (200 - (totalFrames / 2.0f))
        if (!isStartingUse){
            //when using long enough
            offset = -30.0f
            val rot: Float = if (arm == Arm.RIGHT) 10.0f else -10.0f
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rot))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rot))
        }
        val n: Float = CustomAnimHelper.lerpRotDegree(EasingFunction.MIRROR, currentAnimTime, rotDeg, offset)

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n))
    }


}