package io.github.hootisman.network

import io.github.hootisman.util.PotionSpellUtil
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.ItemStack

object ShiftDownC2SPacket {
    var handler: ServerPlayNetworking.PlayChannelHandler = ServerPlayNetworking.PlayChannelHandler { server, player, handler, buf, responseSender ->
        val isShiftDown: Boolean = buf.readBoolean()
        val stack: ItemStack = buf.readItemStack()
        server.execute {
            if (isShiftDown) PotionSpellUtil.castOnSelf(player, stack) else PotionSpellUtil.castOnOther(player, stack)
        }
    }
}