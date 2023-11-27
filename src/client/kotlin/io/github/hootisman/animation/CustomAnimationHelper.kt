package io.github.hootisman.animation

import io.github.hootisman.animation.impl.GeopickAnimation
import net.minecraft.util.math.MathHelper
import kotlin.math.pow

object CustomAnimationHelper {

    /*
        Ease Functions
     */

    /**
     * Mirror ease function (ease in, then ease out)
     *
     * Place in lerp as the delta param!!!
     * @param delta 0 to 1 value representing animation time, 0 = beginning of anim. 1 = end of anim.
     * @see GeopickAnimation
     */
    private fun easeMirrorPick(delta: Float): Float {
        val x: Float = MathHelper.clamp(delta, 0.0f, 1f)

        val result: Float = if (x > 0.5){
            ((MathHelper.cos((2.0f * MathHelper.PI * x) - MathHelper.PI) / 2.0f ) + 0.5f).pow(6)
        } else {
            ((MathHelper.cos((2.0f * MathHelper.PI * x - 0.451f) - MathHelper.PI) / 1.8f ) + 0.5f).pow(6)
        }

        return result
    }
    /**
     * calculates the current time of animation (frameTime), then returns the degree at that time
     *
     *
     * @param frame current animation frame
     * @param speed length of animation in frames
     * @param offset starting degree of animation
     * @param range 'length' of lerp. range + offset = ending degree
     * @param tickDelta 'micro' ticks
     */
    fun currentAnimDegree(frame: Float, speed: Float, offset: Float, range: Float, tickDelta: Float): Float {
        val frameTime = 1.0f - (frame + 1.0f - tickDelta)/ speed // 0 to 1
        return MathHelper.lerpAngleDegrees(easeMirrorPick(frameTime), offset, offset + range)
    }
}