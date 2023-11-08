package io.github.hootisman.item

import io.github.hootisman.entity.PotionSpellEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.TippedArrowItem
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class StickItem(settings: Settings?) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        var stickstack = user?.getStackInHand(hand)
        user?.itemCooldownManager?.set(this, 20);

        if (world?.isClient() == true) return TypedActionResult.consume(stickstack);

        val arrowStack: ItemStack = ItemStack(Items.TIPPED_ARROW).also { PotionUtil.setPotion(it, Potions.HARMING) }
        var arrowEntity: PersistentProjectileEntity = ProjectileUtil.createArrowProjectile(user, arrowStack, 0f)
//
//        arrowEntity.setVelocity(user, user?.pitch ?: 1f, user?.yaw ?: 1f, 0f, 3.0f, 1.0f)
//        arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY

//        world?.spawnEntity(arrowEntity)

        var entity: PotionSpellEntity

        return TypedActionResult.success(stickstack);
    }
}