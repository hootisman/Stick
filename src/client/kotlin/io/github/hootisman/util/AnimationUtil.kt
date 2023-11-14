package io.github.hootisman.util

import com.mojang.logging.LogUtils
import io.github.hootisman.util.Custom1stPersonAnim.AnimationKey.*
import net.minecraft.client.render.item.HeldItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Arm

object AnimationUtil {
    fun doFirstPersonItemAnimation(heldItemRenderer: HeldItemRenderer,
                                   matrices: MatrixStack,
                                   tickDelta: Float,
                                   arm: Arm,
                                   stack: ItemStack,
                                   equipProgress: Float){
        val customItem = stack.item.asItem() as Custom1stPersonAnim
        LogUtils.getLogger().info("CHECKING IF ITEM HAS ANIM KEY! KEY: ${customItem.ANIM_KEY}")
        when(customItem.ANIM_KEY){
            GEOPICK -> applyGeopickTransformation(heldItemRenderer, matrices, tickDelta, arm, stack, equipProgress)
        }
    }
    private fun applyGeopickTransformation(heldItemRenderer: HeldItemRenderer,
                                           matrices: MatrixStack,
                                           tickDelta: Float,
                                           arm: Arm,
                                           stack: ItemStack,
                                           equipProgress: Float){
        LogUtils.getLogger().info("DOING GEOPICK ANIM RIGHT NOW!!!")
    }
}