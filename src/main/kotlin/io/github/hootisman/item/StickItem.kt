package io.github.hootisman.item

import io.github.hootisman.network.ServerNetworkIds
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class StickItem(settings: Settings?) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        var stickstack = user?.getStackInHand(hand)
        user?.itemCooldownManager?.set(this, 20);
        if (world?.isClient() == true) return TypedActionResult.consume(stickstack)


        user?.let {
            ServerPlayNetworking.send(it as ServerPlayerEntity?,ServerNetworkIds.SHIFT_DOWN_ID,PacketByteBufs.empty())
        }

        return TypedActionResult.success(stickstack);
    }
}