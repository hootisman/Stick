package hootisman.stick.animation

import com.mojang.blaze3d.vertex.PoseStack
import hootisman.stick.animation.impl.GeopickAnimation
import hootisman.stick.mixin.client.HeldItemRendererInvoker
import hootisman.stick.util.CustomAnimatedItem
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.util.Mth
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.item.ItemStack


object HandAnimations {
    /**
     * list of all hand animations
     */
    private val list: List<IHandAnimation> = mutableListOf()

    /**
     * @return animation impl corresponding to key, or null
     */
    fun get(key: CustomAnimatedItem.AnimationKey): IHandAnimation? = list.firstOrNull { value -> value.ANIM_KEY == key }

    private fun register(anim: IHandAnimation) = (list as MutableList).add(anim)

    /**
     * adds all IHandAnimation implementations to the list
     */
    fun registerEntries(){
        register(GeopickAnimation)
    }

    /**
     * Custom First Person held item animation
     */
    fun doHeldItemAnimation(heldItemRenderer: ItemInHandRenderer,
                            matrices: PoseStack,
                            tickDelta: Float,
                            arm: HumanoidArm,
                            stack: ItemStack,
                            equipProgress: Float,
                            swingProgress: Float){
        val customItem = stack.item.asItem() as CustomAnimatedItem
        val anim = this.get(customItem.ANIM_KEY)
        anim?.doAnimation(heldItemRenderer, matrices, tickDelta, arm, stack, equipProgress,swingProgress)
    }

    /**
     * Default held item animation (idle)
     */
    fun doDefaultItemAnimation(heldItemRenderer: ItemInHandRenderer, matrices: PoseStack, arm: HumanoidArm, equipProgress: Float, swingProgress: Float){
        val isRightArm = arm == HumanoidArm.RIGHT

        val n = -0.4f * Mth.sin((Mth.sqrt(swingProgress) * Math.PI.toFloat()))
        val m = 0.2f * Mth.sin((Mth.sqrt(swingProgress) * (Math.PI.toFloat() * 2)))
        val f = -0.2f * Mth.sin((swingProgress * Math.PI.toFloat()))
        val o = if (isRightArm) 1.0f else -1.0f
        matrices.translate(o * n, m, f)

        (heldItemRenderer as HeldItemRendererInvoker).invokeApplyEquipOffset(matrices, arm, equipProgress)
        (heldItemRenderer as HeldItemRendererInvoker).invokeApplySwingOffset(matrices, arm, swingProgress)
    }
}