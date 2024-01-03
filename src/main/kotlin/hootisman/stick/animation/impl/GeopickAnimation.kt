package hootisman.stick.animation.impl

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import hootisman.stick.animation.CustomAnimHelper
import hootisman.stick.animation.EasingFunction
import hootisman.stick.animation.IHandAnimation
import hootisman.stick.mixin.client.HeldItemRendererInvoker
import hootisman.stick.util.CustomAnimatedItem
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.item.ItemStack


object GeopickAnimation : IHandAnimation {
    override val ANIM_KEY = CustomAnimatedItem.AnimationKey.GEOPICK

    override fun doAnimation(heldItemRenderer: ItemInHandRenderer, matrices: PoseStack,
                             tickDelta: Float, arm: HumanoidArm, stack: ItemStack, equipProgress: Float, swingProgress: Float) {

        val heldInvoker = heldItemRenderer as HeldItemRendererInvoker
        val itemUseTimeLeft = heldInvoker.minecraft.player!!.useItemRemainingTicks //ticks till item finishes use
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
            val rot: Float = if (arm == HumanoidArm.RIGHT) 10.0f else -10.0f
            matrices.mulPose(Axis.ZP.rotationDegrees(rot))
            matrices.mulPose(Axis.YP.rotationDegrees(rot))
        }
        val n: Float = CustomAnimHelper.lerpRotDegree(EasingFunction.MIRROR, currentAnimTime, rotDeg, offset)

        matrices.mulPose(Axis.XP.rotationDegrees(n))
    }


}