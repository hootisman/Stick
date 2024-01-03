package hootisman.stick.animation

import com.mojang.blaze3d.vertex.PoseStack
import hootisman.stick.util.CustomAnimatedItem
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.item.ItemStack

interface IHandAnimation {
    val ANIM_KEY: CustomAnimatedItem.AnimationKey

    fun doAnimation(heldItemRenderer: ItemInHandRenderer, matrices: PoseStack, tickDelta: Float,
                    arm: HumanoidArm, stack: ItemStack, equipProgress: Float = 0.0f, swingProgress: Float = 0.0f)
}