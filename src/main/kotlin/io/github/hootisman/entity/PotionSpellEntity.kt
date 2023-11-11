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
import net.minecraft.particle.ParticleTypes
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.registry.Registries
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import kotlin.math.atan2

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
    val DEFAULT_COLOR: Int = PotionUtil.getColor(Potions.POISON)
    /**
     * reference to dataTracker.get(COLOR)
     */
    var colorRef: Int
        get() = this.dataTracker.get(COLOR)
        set(color) = this.dataTracker.set(COLOR,color)

    var potion: Potion

    init {
        this.potion = Potions.POISON
        this.initColor()
    }

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

    private fun spawnParticles(amt: Int){
        if (!this.isThereColor() && amt <= 0) return

        val colorX: Double = (this.colorRef shr 16 and 0xFF).toDouble() / 255.0f
        val colorY: Double = (this.colorRef shr 8 and 0xFF).toDouble() / 255.0f
        val colorZ: Double = (this.colorRef shr 0 and 0xFF).toDouble() / 255.0f
        for (j in 0 until amt) {
            world.addParticle(
                ParticleTypes.ENTITY_EFFECT,
                getParticleX(0.5), this.randomBodyY, getParticleZ(0.5),
                colorX, colorY, colorZ
            )
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult?) {
        super.onEntityHit(entityHitResult)
        val entity: Entity? = entityHitResult?.entity
        LogUtils.getLogger().info("hit something!")

        if (entity is LivingEntity && entity.isAffectedBySplashPotions){
            LogUtils.getLogger().info("hit a living entity! ${entity.name} at ${entity.pos}")
            entity.damage(damageSources?.magic(), 1.0f)
            this.potion.effects.forEach { effect ->
                LogUtils.getLogger().info("adding effect $effect")

                //must create new StatusEffectInstance, otherwise it uses current instance
                entity.addStatusEffect(StatusEffectInstance(effect.effectType,effect.duration,effect.amplifier), this.owner)
            }
            this.kill()
        }
    }

    override fun onBlockHit(blockHitResult: BlockHitResult?) {
        super.onBlockHit(blockHitResult)
        this.kill()
    }
    private fun collisionCheck(newPos: Vec3d){
        //gets EntityHitResult if collided with one, if not then gets HitResult using raycast
        this.checkBlockCollision()
    }
    override fun tick() {
        super.tick()
        if (this.world.isClient()){
            this.spawnParticles(2)
            return
        }
        this.velocityDirty= true
        var newPos: Vec3d = this.pos.add(this.velocity)

        var hitResult = ProjectileUtil.getCollision(this, this::canHit)
        var entityHitResult = ProjectileUtil.raycast(this,this.pos,newPos,this.boundingBox.stretch(this.velocity).expand(40.0),this::canHit, 40.0)
        LogUtils.getLogger().info("entity: ${entityHitResult?.entity} ${entityHitResult?.type}")
        if (entityHitResult?.entity != null) hitResult = entityHitResult
        if (hitResult.type != HitResult.Type.MISS) this.onCollision(hitResult)
        this.checkBlockCollision()

        ProjectileUtil.setRotationFromVelocity(this,0.2f)

        this.setPos(newPos.x,newPos.y,newPos.z)
    }
}