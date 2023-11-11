package io.github.hootisman.entity

import com.mojang.logging.LogUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class PotionSpellEntity(entityType: EntityType<out ProjectileEntity>?, world: World?) : ProjectileEntity(entityType, world) {

    companion object {
        @JvmStatic val COLOR: TrackedData<Int> = DataTracker.registerData(PotionSpellEntity::class.java,TrackedDataHandlerRegistry.INTEGER)
        private val DEFAULT_POTION: Potion = Potions.EMPTY
        /**
         * -1 == no color
         */
        private val DEFAULT_COLOR: Int = -1
    }


    /**
     * reference to dataTracker.get(COLOR)
     */
    private var colorRef: Int
        get() = this.dataTracker.get(COLOR)
        set(color) = this.dataTracker.set(COLOR,color)

    private lateinit var colorRGB: Vec3d
    private var potion: Potion = DEFAULT_POTION
        set(value) {
            field = value
            this.initColor()
            this.updateColorRGB()
        }
    private constructor(x: Double, y: Double, z: Double, world: World?) : this(StickEntities.POTION_SPELL, world) {
        this.setPosition(x,y,z)
    }
    constructor(entity: LivingEntity, world: World?, potion: Potion) : this(entity.x, entity.eyeY - 0.1f.toDouble(), entity.z,world) {
        this.owner = entity
        this.potion = potion
    }
    constructor(entity: LivingEntity, world: World?) : this(entity,world, DEFAULT_POTION)
    override fun initDataTracker() = this.dataTracker.startTracking(COLOR,DEFAULT_COLOR)
    private fun initColor() {
        this.colorRef = if (this.potion == Potions.EMPTY)
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

    private fun updateColorRGB() {
        this.colorRGB = Vec3d(
                (this.colorRef shr 16 and 0xFF).toDouble() / 255.0f,
                (this.colorRef shr 8 and 0xFF).toDouble() / 255.0f,
                (this.colorRef shr 0 and 0xFF).toDouble() / 255.0f
            )
    }

    /**
     * returns darker color of this.colorRGB
     */
    private fun getDarkerColor(): Vec3d{
        val ratio = 0.3
        val diff: Vec3d = Vec3d(
            ((this.colorRef) shr 16 and 0xFF) * ratio / 255.0,
            ((this.colorRef) shr 8 and 0xFF) * ratio / 255.0,
            ((this.colorRef) shr 0 and 0xFF) * ratio / 255.0
        )
        return this.colorRGB.subtract(diff)
    }
    private fun spawnTrailParticles(amt: Int){
        spawnTrailParticles(amt, ParticleTypes.ENTITY_EFFECT, getParticleX(0.5), this.randomBodyY - 0.5, getParticleZ(0.5))
    }
    private fun spawnTrailParticles(amt: Int, type: ParticleEffect, x: Double, y: Double, z: Double){
        if (!this.isThereColor() && amt <= 0) return
        updateColorRGB()

        for (j in 0 until amt) world.addParticle(type, x, y, z, this.colorRGB.x, this.colorRGB.y, this.colorRGB.z)

    }
    /**
     * only ran on server :)
     */
    private fun spawnImpactParticles(amt: Int,x: Double,y: Double,z: Double){
        this.updateColorRGB()
        val darkerRGB = this.getDarkerColor()

        for (i in 0 until amt){
            (this.world as ServerWorld).spawnParticles(ParticleTypes.ENTITY_EFFECT,
                x,y,z, 0, darkerRGB.x, darkerRGB.y, darkerRGB.z, 1.0)
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult?) {
        super.onEntityHit(entityHitResult)
        val entity: Entity? = entityHitResult?.entity

        if (entity is LivingEntity && entity.isAffectedBySplashPotions){
            entity.damage(damageSources?.magic(), 0.0f)
            this.spawnImpactParticles(10, getParticleX(0.5), this.randomBodyY - 0.5, getParticleZ(0.5))
            this.potion.effects.forEach { effect ->

                //must create new StatusEffectInstance, otherwise it uses current instance
                entity.addStatusEffect(StatusEffectInstance(effect.effectType,effect.duration,effect.amplifier), this.owner)
            }
            this.kill()
        }
    }

    override fun onBlockHit(blockHitResult: BlockHitResult?) {
        super.onBlockHit(blockHitResult)
        LogUtils.getLogger().info("side: ${blockHitResult!!.side} offset: ${blockHitResult.side.vector}")
        this.spawnImpactParticles(10,getParticleX(0.5), this.randomBodyY - 0.5, getParticleZ(0.5))
        this.kill()
    }
    override fun tick() {
        super.tick()
        if (this.world.isClient()){
            this.spawnTrailParticles(6)
            return
        }
        this.velocityDirty= true
        var newPos: Vec3d = this.pos.add(this.velocity)

        var hitResult = ProjectileUtil.getCollision(this, this::canHit)
        var entityHitResult = ProjectileUtil.raycast(this,this.pos,newPos,this.boundingBox.stretch(this.velocity).expand(40.0),this::canHit, 40.0)

        if (entityHitResult?.entity != null) hitResult = entityHitResult
        if (hitResult.type != HitResult.Type.MISS) this.onCollision(hitResult)
        this.checkBlockCollision()

        ProjectileUtil.setRotationFromVelocity(this,0.2f)

        this.setPos(newPos.x,newPos.y,newPos.z)
    }
}