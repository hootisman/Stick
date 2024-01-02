package hootisman.stick.util

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.PotionUtils

object PotionSpellUtil {
    fun castOnSelf(player: Player,stickStack: ItemStack){
        player.level()?.playSound(null, player.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0f, 1.0f)
        PotionUtils.getPotion(stickStack).effects.forEach {
            effect -> player.addEffect(MobEffectInstance(effect.effect, effect.duration, effect.amplifier), player)
        }
    }
    fun castOnOther(player: Player,stickStack: ItemStack){
        player.level()?.playSound(null, player.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0f, 1.0f)

        val spellEntity = PotionSpellEntity(player, player.level(),PotionUtils.getPotion(stickStack))
        spellEntity.setVelocity(player, player.lerpTargetXRot(), player.lerpTargetYRot(), 0.0f, 2.0f, 1.0f)
        player.level()?.addFreshEntity(spellEntity)
    }
}