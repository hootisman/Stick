package io.github.hootisman.network

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object StickServerPackets {
    fun registerPacket(handler: ServerPlayNetworking.PlayChannelHandler) = ServerPlayNetworking.registerGlobalReceiver(ServerNetworkIds.SHIFT_DOWN_ID, handler)
}