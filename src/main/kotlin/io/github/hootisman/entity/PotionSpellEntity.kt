package io.github.hootisman.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.particle.ParticleTypes
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.registry.Registries
import net.minecraft.world.World

class PotionSpellEntity(entityType: EntityType<out ProjectileEntity>?, world: World?) : ProjectileEntity(entityType, world) {
    companion object {
        @JvmStatic val COLOR: TrackedData<Int> = DataTracker.registerData(PotionSpellEntity::class.java,TrackedDataHandlerRegistry.INTEGER)
    }

    /**
     * -1 == no color
     */
    val DEFAULT_COLOR: Int = PotionUtil.getColor(Potions.HEALING)
    /**
     * reference to dataTracker.get(COLOR)
     */
    var colorRef: Int
        get() = this.dataTracker.get(COLOR)
        set(color) = this.dataTracker.set(COLOR,color)

    var potion: Potion = Potions.EMPTY
    var effects: MutableSet<StatusEffectInstance> = hashSetOf()


    override fun initDataTracker() = this.dataTracker.startTracking(COLOR,DEFAULT_COLOR)
    private fun initColor() {
        colorRef = if (this.potion == Potions.EMPTY && this.effects.isEmpty())
            DEFAULT_COLOR
        else
            this.getColorFromEffects()
    }
    fun addEffect(effect: StatusEffectInstance){
        this.effects.add(effect)
        this.colorRef = this.getColorFromEffects()
    }
    override fun writeCustomDataToNbt(nbt: NbtCompound?) {
        super.writeCustomDataToNbt(nbt)
        if (this.potion != Potions.EMPTY) nbt?.putString("Potion",Registries.POTION.getId(this.potion).toString())
        if (this.isThereColor()) nbt?.putInt("Color",this.colorRef)
        if (this.effects.isNotEmpty()) {
            var list = NbtList()
            for (e: StatusEffectInstance in this.effects){
                list.add(e.writeNbt(NbtCompound()))
            }
            nbt?.put("custom_potion_effects",list)
        }
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {
        super.readCustomDataFromNbt(nbt)
        if (nbt?.contains("Potion") == true) this.potion = PotionUtil.getPotion(nbt)
        if (isThereColor(nbt) == true) this.colorRef = (nbt!!.getInt("Color")) else this.initColor()
        for (effect: StatusEffectInstance in this.effects) this.addEffect(effect)
    }
    private fun getColorFromEffects(): Int = PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion,this.effects))
    private fun isThereColor(): Boolean = this.colorRef != -1
    private fun isThereColor(nbt: NbtCompound?): Boolean? = nbt?.contains("Color", NbtElement.NUMBER_TYPE.toInt())

    private fun spawnParticles(amt: Int){
        if (this.isThereColor() && amt <= 0) return

        val velocityX: Double = (this.colorRef shr 16 and 0xFF).toDouble() / 255.0
        val velocityY: Double = (this.colorRef shr 8 and 0xFF).toDouble() / 255.0
        val velocityZ: Double = (this.colorRef shr 0 and 0xFF).toDouble() / 255.0
        for (j in 0 until amt) {
            world.addParticle(
                ParticleTypes.ENTITY_EFFECT,
                getParticleX(0.5), this.randomBodyY, getParticleZ(0.5),
                velocityX, velocityY, velocityZ
            )
        }
    }

    override fun tick() {
        super.tick()
        if (this.world.isClient()){
            this.spawnParticles(2)
        }
    }
}