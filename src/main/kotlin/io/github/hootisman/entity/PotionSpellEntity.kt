package io.github.hootisman.entity

import com.mojang.logging.LogUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSources
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffect
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
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.world.World

class PotionSpellEntity(entityType: EntityType<out ProjectileEntity>?, world: World?) : ProjectileEntity(entityType, world) {
    constructor(x: Double, y: Double, z: Double, world: World?) : this(HootEntityRegistry.POTION_SPELL, world) {
        this.setPosition(x,y,z)
    }
    constructor(entity: LivingEntity, world: World?) : this(entity.x, entity.eyeY - 0.1f.toDouble(), entity.z,world) {
        this.owner = entity
    }
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

    var potion: Potion = Potions.HARMING

    override fun initDataTracker() = this.dataTracker.startTracking(COLOR,DEFAULT_COLOR)
    private fun initColor() {
        colorRef = if (this.potion == Potions.EMPTY)
            DEFAULT_COLOR
        else
            PotionUtil.getColor(this.potion)
    }
    override fun writeCustomDataToNbt(nbt: NbtCompound?) {
        super.writeCustomDataToNbt(nbt)
        if (this.potion != Potions.EMPTY) nbt?.putString("Potion",Registries.POTION.getId(this.potion).toString())
        if (this.isThereColor()) nbt?.putInt("Color",this.colorRef)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {
        super.readCustomDataFromNbt(nbt)
        if (nbt?.contains("Potion") == true) this.potion = PotionUtil.getPotion(nbt)
        if (isThereColor(nbt) == true) this.colorRef = (nbt!!.getInt("Color")) else this.initColor()
    }
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

    override fun onEntityHit(entityHitResult: EntityHitResult?) {
        super.onEntityHit(entityHitResult)
        val entity: Entity? = entityHitResult?.entity
        LogUtils.getLogger().info("hit something!")

        if (entity is LivingEntity && entity.isAffectedBySplashPotions){
            LogUtils.getLogger().info("hit a living entity!")
            entity.damage(entity.damageSources?.magic(), 0.0f)
//            for (effect: StatusEffectInstance in this.potion.effects){
            this.potion.effects.forEach { effect -> entity.addStatusEffect(effect) }
            this.discard()
        }
    }

    override fun onBlockHit(blockHitResult: BlockHitResult?) {
        super.onBlockHit(blockHitResult)
        this.discard()
    }
    override fun tick() {
        super.tick()
        if (this.world.isClient()){
            this.spawnParticles(2)
        }
    }
}