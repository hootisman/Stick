package hootisman.stick.item

import com.mojang.logging.LogUtils
import hootisman.stick.util.CustomAnimatedItem
import net.minecraft.core.Direction
import net.minecraft.core.particles.BlockParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.item.BrushItem.DustParticlesDelta
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

class GeopickItem(settings: Properties?) : Item(settings), CustomAnimatedItem {
    override val ANIM_KEY = CustomAnimatedItem.AnimationKey.GEOPICK
    /**
     * reused brush code
     * @see BrushItem
     */
    override fun useOn(context: UseOnContext?): InteractionResult{
        val player: Player? = context?.player
        if (this.getHitResult(player).type == HitResult.Type.BLOCK) player?.startUsingItem(context.hand)
        return InteractionResult.CONSUME
    }
    override fun getUseDuration(stack: ItemStack?): Int = 200
    /**
     * reused brush code
     * @see BrushItem
     */
    private fun getHitResult(user: LivingEntity?): HitResult {
        return ProjectileUtil.getHitResultOnViewVector(user,
            { entity: Entity? -> !entity!!.isSpectator && entity.canBeHitByProjectile() }, 5.0
        )
    }
    override fun onUseTick(world: Level?, user: LivingEntity?, stack: ItemStack?, remainingUseTicks: Int) {
        val hitResult: HitResult = this.getHitResult(user)
        if (remainingUseTicks < 0 || user !is Player || hitResult !is BlockHitResult || hitResult.type != HitResult.Type.BLOCK) {
            user?.stopUsingItem()
            return
        }
        val playerEntity: Player = user
        val blockHitResult: BlockHitResult = hitResult
        val currentTick: Int = this.getUseDuration(stack) - remainingUseTicks + 1

        if (currentTick % 10 == 4) user.swing(InteractionHand.MAIN_HAND)
        if (currentTick % 10 != 5) return

        val blockState = world?.getBlockState(blockHitResult.blockPos)

        if (blockState?.shouldSpawnTerrainParticles() == true && blockState.renderShape != RenderShape.INVISIBLE){
            val arm: HumanoidArm = if(user.usedItemHand == InteractionHand.MAIN_HAND) playerEntity.mainArm else playerEntity.mainArm.opposite
            addDustParticles(world, hitResult, blockState, user.getViewVector(0.0f), arm)
        }

        val blockEntity = world?.getBlockEntity(blockHitResult.blockPos)
        val soundEvent = blockState?.soundType?.hitSound
        world?.playSound(playerEntity, blockHitResult.blockPos, soundEvent, SoundSource.BLOCKS, 0.8f, 3.0f)
//        if (world?.isClient == false && blockEntity is BrushableBlockEntity && blockEntity.brush(world.time, playerEntity, blockHitResult.side)){
//            LogUtils.getLogger().info("Finished pickign with geopick!")
//        }

    }
    fun addDustParticles(world: Level, hitResult: BlockHitResult, state: BlockState?, userRotation: Vec3?, arm: HumanoidArm) {
        val i = if (arm == HumanoidArm.RIGHT) 1 else -1
        val j = world.getRandom().nextInt(7, 12)
        val blockStateParticleEffect = BlockParticleOption(ParticleTypes.BLOCK, state)
        val direction = hitResult.direction
        val dustParticlesOffset = DustParticlesDelta.fromDirection(userRotation, direction)
        val vec3d = hitResult.location
        for (k in 0 until j) {
            world.addParticle(
                blockStateParticleEffect,
                vec3d.x - (if (direction == Direction.WEST) 1.0E-6f else 0.0f).toDouble(),
                vec3d.y,
                vec3d.z - (if (direction == Direction.NORTH) 1.0E-6f else 0.0f).toDouble(),
                dustParticlesOffset.xd * i.toDouble() * 3.0 * world.getRandom().nextDouble(),
                0.0,
                dustParticlesOffset.zd * i.toDouble() * 3.0 * world.getRandom().nextDouble()
            )
        }
    }
}