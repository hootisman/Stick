package hootisman.stick.entity

import com.mojang.logging.LogUtils
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

class PotionSpellEntity(entityType: EntityType<out Projectile>?, world: Level?) : Projectile(entityType, world) {

    companion object {
        @JvmStatic val COLOR: EntityDataAccessor<Int> = SynchedEntityData.defineId(PotionSpellEntity::class.java,EntityDataSerializers.INT)
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
        get() = this.entityData.get(COLOR)
        set(color) = this.entityData.set(COLOR,color)

    private lateinit var colorRGB: Vec3
    private var potion: Potion = DEFAULT_POTION
        set(value) {
            field = value
            this.initColor()
            this.updateColorRGB()
        }
    private constructor(x: Double, y: Double, z: Double, world: Level?) : this(StickEntities.POTION_SPELL.get(), world) {
        this.setPos(x,y,z)
    }
    constructor(entity: LivingEntity, world: Level?, potion: Potion) : this(entity.x, entity.eyeY - 0.05f.toDouble(), entity.z,world) {
        this.owner = entity
        this.potion = potion
    }
    constructor(entity: LivingEntity, world: Level?) : this(entity,world, DEFAULT_POTION)
    override fun defineSynchedData() = this.entityData.define(COLOR,DEFAULT_COLOR)
    private fun initColor() {
        this.colorRef = if (this.potion == Potions.EMPTY)
            DEFAULT_COLOR
        else
            PotionUtils.getColor(this.potion)
    }
    override fun addAdditionalSaveData(nbt: CompoundTag?) {
        super.addAdditionalSaveData(nbt)
        if (this.potion != Potions.EMPTY) nbt?.putString("Potion", BuiltInRegistries.POTION.getId(this.potion).toString())
        if (this.isThereColor()) nbt?.putInt("Color",this.colorRef)
    }

    override fun readAdditionalSaveData(nbt: CompoundTag?) {
        super.readAdditionalSaveData(nbt)
        if (nbt?.contains("Potion") == true) this.potion = PotionUtils.getPotion(nbt)
        if (isThereColor(nbt) == true) this.colorRef = (nbt!!.getInt("Color")) else this.initColor()
    }
    private fun isThereColor(): Boolean = this.colorRef != -1
    private fun isThereColor(nbt: CompoundTag?): Boolean? = nbt?.contains("Color", Tag.TAG_ANY_NUMERIC.toInt())

    private fun updateColorRGB() {
        this.colorRGB = Vec3(
                (this.colorRef shr 16 and 0xFF).toDouble() / 255.0f,
                (this.colorRef shr 8 and 0xFF).toDouble() / 255.0f,
                (this.colorRef shr 0 and 0xFF).toDouble() / 255.0f
            )
    }

    /**
     * returns darker color of this.colorRGB
     */
    private fun getDarkerColor(): Vec3{
        val ratio = 0.3
        val diff: Vec3 = Vec3(
            ((this.colorRef) shr 16 and 0xFF) * ratio / 255.0,
            ((this.colorRef) shr 8 and 0xFF) * ratio / 255.0,
            ((this.colorRef) shr 0 and 0xFF) * ratio / 255.0
        )
        return this.colorRGB.subtract(diff)
    }
    private fun spawnTrailParticles(amt: Int){
        spawnTrailParticles(amt, ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.randomY, this.getRandomZ(0.5))
    }
    private fun spawnTrailParticles(amt: Int, type: ParticleOptions, x: Double, y: Double, z: Double){
        if (!this.isThereColor() && amt <= 0) return
        updateColorRGB()

        for (j in 0 until amt) this.level().addParticle(type, x, y, z, this.colorRGB.x, this.colorRGB.y, this.colorRGB.z)

    }
    /**
     * only ran on server :)
     */
    private fun spawnImpactParticles(amt: Int,x: Double,y: Double,z: Double){
        this.updateColorRGB()
        val darkerRGB = this.getDarkerColor()

        for (i in 0 until amt){
            (this.level() as ServerLevel).sendParticles(ParticleTypes.ENTITY_EFFECT,
                x,y,z, 0, darkerRGB.x, darkerRGB.y, darkerRGB.z, 1.0)
        }
    }

    override fun onHitEntity(entityHitResult: EntityHitResult?) {
        super.onHitEntity(entityHitResult)
        val entity: Entity? = entityHitResult?.entity

        if (entity is LivingEntity && entity.isAffectedByPotions){
            entity.hurt(damageSources()?.magic(), 0.0f)
            this.spawnImpactParticles(10, this.getRandomX(0.5), this.randomY, this.getRandomZ(0.5))
            this.potion.effects.forEach { effect ->

                //must create new StatusEffectInstance, otherwise it uses current instance
                entity.addEffect(MobEffectInstance(effect.effect,effect.duration,effect.amplifier), this.owner)
            }
            this.kill()
        }
    }

    override fun onHitBlock(blockHitResult: BlockHitResult?) {
        super.onHitBlock(blockHitResult)
        this.spawnImpactParticles(10,this.getRandomX(0.5), this.randomY, this.getRandomZ(0.5))
        this.kill()
    }
    override fun tick() {
        super.tick()
        if (this.level().isClientSide){
            this.spawnTrailParticles(6)
            return
        }
//        this.velocityDirty= true
        var newPos: Vec3 = this.position().add(this.deltaMovement)

        var hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity)
        var entityHitResult = ProjectileUtil.getEntityHitResult(this,this.position(),newPos,this.boundingBox.expandTowards(this.deltaMovement).inflate(40.0),this::canHitEntity, 40.0)

        if (entityHitResult?.entity != null) hitResult = entityHitResult
        if (hitResult.type != HitResult.Type.MISS) this.onHit(hitResult)
        this.checkInsideBlocks()

        ProjectileUtil.rotateTowardsMovement(this,0.2f)

        this.setPos(newPos.x,newPos.y,newPos.z)
    }
}