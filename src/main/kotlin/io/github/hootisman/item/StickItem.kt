package io.github.hootisman.item

import io.github.hootisman.entity.PotionSpellEntity
import io.github.hootisman.network.ServerNetIds
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class StickItem(settings: Settings?) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        var stickstack = user?.getStackInHand(hand)
        user?.itemCooldownManager?.set(this, 20);
        if (world?.isClient() == true) return TypedActionResult.consume(stickstack)


        user?.let {
            ServerPlayNetworking.send(it as ServerPlayerEntity?,ServerNetIds.IS_SHIFT_DOWN_ID,PacketByteBufs.empty())
            ServerPlayNetworking.registerGlobalReceiver(ServerNetIds.IS_SHIFT_DOWN_ID
            ) { server, player, handler, buf, responseSender ->
                val isShiftDown: Boolean = buf.readBoolean()
                server.execute {
                    if (isShiftDown){
                        world?.playSound(null, it.blockPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f)
                    } else {
                        world?.playSound(null, it.blockPos, SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f)

                        val spellEntity = PotionSpellEntity(it, world)
                        spellEntity.setVelocity(it, it.pitch, it.yaw, 0.0f, 2.0f, 1.0f)
                        world?.spawnEntity(spellEntity)
                    }
                }
            }
        }

        return TypedActionResult.success(stickstack);
    }
}