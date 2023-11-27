package io.github.hootisman.animation

import io.github.hootisman.animation.impl.GeopickAnimation
import net.minecraft.util.math.MathHelper
import kotlin.math.pow

/**
 * Various easing functions for animation
 *
 * call ease() function to use
 *
 * ex: EasingFunction.MIRROR.ease(...)
 */
enum class EasingFunction {
    NONE {
        override fun ease(delta: Float): Float = delta
    },
    /**
     * Mirror ease function (ease in, then ease out)
     *
     * @param delta 0 to 1 value representing animation time, 0 = beginning of anim. 1 = end of anim.
     * @see GeopickAnimation
     */
    MIRROR {
        override fun ease(delta: Float): Float {
            return if (delta > 0.5){
                ((MathHelper.cos((2.0f * MathHelper.PI * delta) - MathHelper.PI) / 2.0f ) + 0.5f).pow(6)
            } else {
                ((MathHelper.cos((2.0f * MathHelper.PI * delta - 0.451f) - MathHelper.PI) / 1.8f ) + 0.5f).pow(6)
            }
        }
    };
    abstract fun ease(delta: Float): Float
}