package io.github.hootisman.util

import io.github.hootisman.entity.PotionSpellEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

object PotionSpellUtil {
    fun castOnSelf(player: PlayerEntity){
        player.world?.playSound(null, player.blockPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f)
    }
    fun castOnOther(player: PlayerEntity){
        player.world?.playSound(null, player.blockPos, SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f)

        val spellEntity = PotionSpellEntity(player, player.world)
        spellEntity.setVelocity(player, player.pitch, player.yaw, 0.0f, 2.0f, 1.0f)
        player.world?.spawnEntity(spellEntity)
    }
}