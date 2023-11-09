package io.github.hootisman.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.gui.screen.Screen
import net.minecraft.network.PacketByteBuf

object ShiftKeyDownPacket {
    fun registerPacket() = ClientPlayNetworking.registerGlobalReceiver(ServerNetIds.IS_SHIFT_DOWN_ID) {
        client, handler, buf, responseSender ->
        client.execute(){
            var toSend: PacketByteBuf = PacketByteBufs.create()
            toSend.writeBoolean(Screen.hasShiftDown())
            ClientPlayNetworking.send(ServerNetIds.IS_SHIFT_DOWN_ID, toSend)
        }
    }
}