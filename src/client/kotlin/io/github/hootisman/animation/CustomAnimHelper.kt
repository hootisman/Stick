package io.github.hootisman.animation

import net.minecraft.util.math.MathHelper

object CustomAnimHelper {
    /**
     * calculates the current time in an animation as a float between 0 and 1
     *
     * @param frame current frame the animation is at
     * @param totalFrames total number of frames in the animation
     * @param tickDelta tick delta for animation smoothing
     * @return float between 0 and 1; 0 = beginning of animation, 1 = end of animation
     */
    fun calcAnimTime(frame: Float, totalFrames: Float, tickDelta: Float): Float = 1.0f - (frame + 1.0f - tickDelta) / totalFrames
    /**
     * returns a linear interpolation of an offset degree and offset + rotation degree.
     *
     * Uses an easing function for smooth animation
     *
     * @param easeFunc easing function to use for animation
     * @param delta current animation time; float between 0 and 1
     * @param rotDeg how many degrees to move; offset + rotDeg = final degree of anim
     * @param offset the offset degree (where to start at)
     * @return the degree at current animation time
     * @see calcAnimTime
     */
    fun lerpRotDegree(easeFunc: EasingFunction, delta: Float, rotDeg: Float, offset: Float): Float {
        return MathHelper.lerpAngleDegrees(easeFunc.ease(delta), offset, offset + rotDeg)
    }

}