package io.github.hootisman.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object HootClientPackets {
    fun registerPacket(handler: ClientPlayNetworking.PlayChannelHandler) = ClientPlayNetworking.registerGlobalReceiver(ServerNetworkIds.SHIFT_DOWN_ID, handler)
}