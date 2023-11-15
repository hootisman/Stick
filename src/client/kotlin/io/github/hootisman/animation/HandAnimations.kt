package io.github.hootisman.animation

import io.github.hootisman.animation.impl.GeopickAnimation
import io.github.hootisman.util.CustomAnimatedItem

object HandAnimations {
    private val list: List<IHandAnimation> = mutableListOf()
    fun get(key: CustomAnimatedItem.AnimationKey): IHandAnimation? = list.firstOrNull { value -> value.ANIM_KEY == key }

    private fun register(anim: IHandAnimation) {
        (list as MutableList).add(anim)
    }
    fun registerEntries(){
        register(GeopickAnimation)
    }
}