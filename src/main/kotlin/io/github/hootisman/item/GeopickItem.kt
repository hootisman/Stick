package io.github.hootisman.item

import io.github.hootisman.util.Custom1stPersonAnim
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.item.BrushItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.HitResult

class GeopickItem(settings: Settings?) : Item(settings), Custom1stPersonAnim{
    override val ANIM_KEY = Custom1stPersonAnim.AnimationKey.GEOPICK

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val player: PlayerEntity? = context?.player
        if (this.getHitResult(player).type == HitResult.Type.BLOCK) player?.setCurrentHand(context.hand)
        return ActionResult.CONSUME
    }

    override fun getMaxUseTime(stack: ItemStack?): Int = 200

    private fun getHitResult(user: LivingEntity?): HitResult {
        return ProjectileUtil.getCollision(user,
            { entity: Entity -> !entity.isSpectator && entity.canHit() }, Math.sqrt(ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) - 1.0
        )
    }
}