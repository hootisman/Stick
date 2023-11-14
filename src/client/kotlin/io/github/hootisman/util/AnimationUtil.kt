package io.github.hootisman.util

import com.mojang.logging.LogUtils
import io.github.hootisman.mixin.client.HeldItemRendererInvoker
import io.github.hootisman.util.Custom1stPersonAnim.AnimationKey.GEOPICK
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RotationAxis
import kotlin.math.pow

object AnimationUtil {
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

    /**
     * Custom First Person held item animation
     */
    fun doHeldItemAnimFP(heldItemRenderer: HeldItemRenderer,
                         matrices: MatrixStack,
                         tickDelta: Float,
                         arm: Arm,
                         stack: ItemStack,
                         equipProgress: Float){
        val customItem = stack.item.asItem() as Custom1stPersonAnim

        when(customItem.ANIM_KEY){
            GEOPICK -> applyGeopickTransformation(heldItemRenderer, matrices, tickDelta, arm, stack, equipProgress)
        }
    }

    /**
     * Geopick first person Animation
     */
    private fun applyGeopickTransformation(heldItemRenderer: HeldItemRenderer,
                                           matrices: MatrixStack,
                                           tickDelta: Float,
                                           arm: Arm,
                                           stack: ItemStack,
                                           equipProgress: Float){

        val heldInvoker = heldItemRenderer as HeldItemRendererInvoker
        heldInvoker.invokeApplyEquipOffset(matrices, arm, equipProgress)

        val itemUseTime = heldInvoker.client.player!!.itemUseTimeLeft       //ticks till item finishes use
        val offset = -30.0f         //offset
        val range = -60.0f          //how far will be rotated
        val frameSpeed = 10.0f      //200 must be divisible by frameSpeed ex: 200 % 10 = 0, MUST BE 0
        var n: Float

        if (itemUseTime >= (200 - (frameSpeed / 2.0f))){
            n = smoothDegree(itemUseTime % frameSpeed, frameSpeed, 0.0f, range + offset, tickDelta)
        } else {
            val rot: Float = if (arm == Arm.RIGHT) 10.0f else -10.0f
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rot))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rot))

            n = smoothDegree(itemUseTime % frameSpeed, frameSpeed, offset,  range, tickDelta)
        }

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n))
    }
    fun smoothDegree(frame: Float, speed: Float, offset: Float, range: Float, tickDelta: Float): Float {
        val frameDelta = 1.0f - (frame + 1.0f - tickDelta)/ speed // 0 to 1
        val n = MathHelper.lerpAngleDegrees(easeSpike(frameDelta), offset, offset + range)
//        LogUtils.getLogger().info("frame: $frame frameDelta: $frameDelta offset: $offset range: $range n: $n")
        return n.toFloat()
    }
    fun easeSpike(delta: Float): Float {
        //ease function for degree calculation, to be placed in lerp
        val x: Float = MathHelper.clamp(delta, 0.0f, 1f)

        val result: Float = if (x > 0.5){
            ((MathHelper.cos((2.0f * MathHelper.PI * x) - MathHelper.PI) / 2.0f ) + 0.5f).pow(6)
        } else {
            ((MathHelper.cos((2.0f * MathHelper.PI * x - 0.451f) - MathHelper.PI) / 1.8f ) + 0.5f).pow(6)
        }

        return result
    }
}