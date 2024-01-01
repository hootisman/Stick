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
import net.minecraft.world.World

class StickItem(settings: Settings?) : Item(settings) {
    override fun getDefaultStack(): ItemStack = PotionUtil.setPotion(ItemStack(this), Potions.POISON)
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stickStack = user?.getStackInHand(hand)
        user?.itemCooldownManager?.set(this, 20);
        if (world?.isClient() == true) return TypedActionResult.consume(stickStack)


        user?.let {
            val pack = PacketByteBufs.create().writeItemStack(stickStack)
            ServerPlayNetworking.send(it as ServerPlayerEntity?,ServerNetworkIds.SHIFT_DOWN_ID,pack)
        }

        return TypedActionResult.success(stickStack);
    }
}