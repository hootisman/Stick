package io.github.hootisman.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.gui.screen.Screen
import net.minecraft.network.PacketByteBuf

object ShiftDownS2CPacket {
    val handler: ClientPlayNetworking.PlayChannelHandler = ClientPlayNetworking.PlayChannelHandler { client, handler, buf, responseSender ->
        client.execute(){
            var toSend: PacketByteBuf = PacketByteBufs.create()
            toSend.writeBoolean(Screen.hasShiftDown())
            ClientPlayNetworking.send(ServerNetworkIds.SHIFT_DOWN_ID, toSend)
        }
    }
}