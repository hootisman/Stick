package io.github.hootisman.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.gui.screen.Screen
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf

object ShiftDownS2CPacket {
    val handler: ClientPlayNetworking.PlayChannelHandler = ClientPlayNetworking.PlayChannelHandler { client, handler, buf, responseSender ->
        val stack: ItemStack = buf.readItemStack()
        client.execute(){
            val toSend: PacketByteBuf = PacketByteBufs.create()
                .writeBoolean(Screen.hasShiftDown())
                .writeItemStack(stack)
            ClientPlayNetworking.send(ServerNetworkIds.SHIFT_DOWN_ID, toSend)
        }
    }
}