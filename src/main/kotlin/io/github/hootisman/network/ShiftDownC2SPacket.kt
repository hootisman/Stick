package io.github.hootisman.network

import io.github.hootisman.util.PotionSpellUtil
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object ShiftDownC2SPacket {
    var handler: ServerPlayNetworking.PlayChannelHandler = ServerPlayNetworking.PlayChannelHandler { server, player, handler, buf, responseSender ->
        val isShiftDown: Boolean = buf.readBoolean()
        server.execute {
            if (isShiftDown) PotionSpellUtil.castOnSelf(player) else PotionSpellUtil.castOnOther(player)
        }
    }
}