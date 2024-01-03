package hootisman.stick.item

import hootisman.stick.util.PotionSpellUtil
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.Level

//TODO Correct sound for shooting at other, figure out velocityDirty
class StickItem(settings: Properties?) : Item(settings) {
    override fun getDefaultInstance(): ItemStack = PotionUtils.setPotion(ItemStack(this), Potions.POISON)
    override fun use(world: Level?, user: Player?, hand: InteractionHand?): InteractionResultHolder<ItemStack> {
        val stickStack = user?.getItemInHand(hand)
        user?.cooldowns?.addCooldown(this, 20);
        if (world?.isClientSide == true) return InteractionResultHolder.consume(stickStack)

        user?.let {
            if (it.isShiftKeyDown) PotionSpellUtil.castOnSelf(user, stickStack) else PotionSpellUtil.castOnOther(user, stickStack)
        }
        return InteractionResultHolder.success(stickStack);
    }
}