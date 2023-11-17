package io.github.hootisman.item

import com.mojang.logging.LogUtils
import io.github.hootisman.util.CustomAnimatedItem
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BrushableBlock
import net.minecraft.block.entity.BrushableBlockEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.item.BrushItem
import net.minecraft.item.BrushItem.DustParticlesOffset
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Arm
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class GeopickItem(settings: Settings?) : Item(settings), CustomAnimatedItem{
    override val ANIM_KEY = CustomAnimatedItem.AnimationKey.GEOPICK
    /**
     * reused brush code
     * @see BrushItem
     */
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val player: PlayerEntity? = context?.player
        if (this.getHitResult(player).type == HitResult.Type.BLOCK) player?.setCurrentHand(context.hand)
        return ActionResult.CONSUME
    }
    override fun getMaxUseTime(stack: ItemStack?): Int = 200
    /**
     * reused brush code
     * @see BrushItem
     */
    private fun getHitResult(user: LivingEntity?): HitResult {
        return ProjectileUtil.getCollision(user,
            { entity: Entity -> !entity.isSpectator && entity.canHit() }, Math.sqrt(ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) - 1.0
        )
    }
    override fun usageTick(world: World?, user: LivingEntity?, stack: ItemStack?, remainingUseTicks: Int) {
        val hitResult: HitResult = this.getHitResult(user)
        if (remainingUseTicks < 0 || user !is PlayerEntity || hitResult !is BlockHitResult || hitResult.type != HitResult.Type.BLOCK) {
            user?.stopUsingItem()
            return
        }
        val playerEntity: PlayerEntity = user
        val blockHitResult: BlockHitResult = hitResult
        val currentTick: Int = this.getMaxUseTime(stack) - remainingUseTicks + 1
        if (currentTick % 10 != 5) return

        val blockState = world?.getBlockState(blockHitResult.blockPos)

        if (blockState?.hasBlockBreakParticles() == true && blockState.renderType != BlockRenderType.INVISIBLE){
            val arm: Arm = if(user.activeHand == Hand.MAIN_HAND) playerEntity.mainArm else playerEntity.mainArm.opposite
            addDustParticles(world, hitResult, blockState, user.getRotationVec(0.0f), arm)
        }

        val blockEntity = world?.getBlockEntity(blockHitResult.blockPos)
        val soundEvent = blockState?.soundGroup?.hitSound
        world?.playSound(playerEntity, blockHitResult.blockPos, soundEvent, SoundCategory.BLOCKS, 0.8f, 1.7f)
        if (world?.isClient == false && blockEntity is BrushableBlockEntity && blockEntity.brush(world.time, playerEntity, blockHitResult.side)){
            LogUtils.getLogger().info("Finished pickign with geopick!")
        }

    }
    fun addDustParticles(world: World, hitResult: BlockHitResult, state: BlockState?, userRotation: Vec3d?, arm: Arm) {
        val i = if (arm == Arm.RIGHT) 1 else -1
        val j = world.getRandom().nextBetweenExclusive(7, 12)
        val blockStateParticleEffect = BlockStateParticleEffect(ParticleTypes.BLOCK, state)
        val direction = hitResult.side
        val dustParticlesOffset = DustParticlesOffset.fromSide(userRotation, direction)
        val vec3d = hitResult.pos
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