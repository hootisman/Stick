package io.github.hootisman.util

/**
 * Item classes that implement this will use custom 1st person animations
 */
interface CustomAnimatedItem {
    val ANIM_KEY: AnimationKey
    /**
     * key used in client AnimationUtil object to perform item animation
     */
    enum class AnimationKey {
        GEOPICK
    }
}