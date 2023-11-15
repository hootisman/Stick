package io.github.hootisman.animation.impl

import io.github.hootisman.animation.IHandAnimation
import io.github.hootisman.animation.HandAnimations
import io.github.hootisman.mixin.client.HeldItemRendererInvoker
import io.github.hootisman.util.CustomAnimatedItem
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RotationAxis
import kotlin.math.pow

object GeopickAnimation : IHandAnimation {
    override val ANIM_KEY = CustomAnimatedItem.AnimationKey.GEOPICK

    override fun doAnimation(heldItemRenderer: HeldItemRenderer, matrices: MatrixStack, tickDelta: Float,
                             arm: Arm, stack: ItemStack, equipProgress: Float, swingProgress: Float) {

        val heldInvoker = heldItemRenderer as HeldItemRendererInvoker
        heldInvoker.invokeApplyEquipOffset(matrices, arm, equipProgress)

        val itemUseTime = heldInvoker.client.player!!.itemUseTimeLeft       //ticks till item finishes use
        var offset = -30.0f         //offset
        var range = -60.0f          //how far will be rotated
        val frameSpeed = 10.0f      //200 must be divisible by frameSpeed ex: 200 % 10 = 0, MUST BE 0

        if (itemUseTime >= (200 - (frameSpeed / 2.0f))){
            offset = 0.0f
            range += offset
        } else {
            val rot: Float = if (arm == Arm.RIGHT) 10.0f else -10.0f
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rot))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rot))
        }
        val n : Float = currentAnimDegree(itemUseTime % frameSpeed, frameSpeed, offset, range, tickDelta)
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n))
    }

    /**
     * calculates the current time of animation (frameTime), then returns the degree at that time
     *
     *
     * @param frame current animation frame
     * @param speed length of animation in frames
     * @param offset starting degree of animation
     * @param range 'length' of lerp. range + offset = ending degree
     * @param tickDelta 'micro' ticks
     */
    private fun currentAnimDegree(frame: Float, speed: Float, offset: Float, range: Float, tickDelta: Float): Float {
        val frameTime = 1.0f - (frame + 1.0f - tickDelta)/ speed // 0 to 1
        return MathHelper.lerpAngleDegrees(easeMirrorPick(frameTime), offset, offset + range)
    }

    /**
     * Mirror ease function (ease in, then ease out)
     *
     * Place in lerp as the delta param!!!
     * @param delta 0 to 1 value representing animation time, 0 = beginning of anim. 1 = end of anim.
     * @see GeopickAnimation
     */
    private fun easeMirrorPick(delta: Float): Float {
        val x: Float = MathHelper.clamp(delta, 0.0f, 1f)

        val result: Float = if (x > 0.5){
            ((MathHelper.cos((2.0f * MathHelper.PI * x) - MathHelper.PI) / 2.0f ) + 0.5f).pow(6)
        } else {
            ((MathHelper.cos((2.0f * MathHelper.PI * x - 0.451f) - MathHelper.PI) / 1.8f ) + 0.5f).pow(6)
        }

        return result
    }
}