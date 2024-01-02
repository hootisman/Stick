package hootisman.stick.item

import io.github.hootisman.network.ServerNetworkIds
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.World
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.Level

class StickItem(settings: Properties?) : Item(settings) {
    override fun getDefaultInstance(): ItemStack = PotionUtils.setPotion(ItemStack(this), Potions.POISON)
    override fun use(world: Level?, user: Player?, hand: InteractionHand?): InteractionResultHolder<ItemStack> {
        val stickStack = user?.getItemInHand(hand)
        user?.cooldowns?.addCooldown(this, 20);
        if (world?.isClientSide == true) return InteractionResultHolder.consume(stickStack)


        user?.let {
            val pack = PacketByteBufs.create().writeItemStack(stickStack)
            ServerPlayNetworking.send(it as ServerPlayerEntity?,ServerNetworkIds.SHIFT_DOWN_ID,pack)
        }

        return InteractionResultHolder.success(stickStack);
    }
}