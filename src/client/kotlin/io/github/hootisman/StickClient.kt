package io.github.hootisman

import io.github.hootisman.animation.HandAnimations
import io.github.hootisman.entity.StickEntityRenderers
import io.github.hootisman.network.StickClientPackets
import io.github.hootisman.network.ShiftDownS2CPacket
import io.github.hootisman.render.StickColorProviders
import net.fabricmc.api.ClientModInitializer

object StickClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		StickEntityRenderers.addEntityRenderers()
		StickColorProviders.registerColorProviders()
		StickClientPackets.registerPacket(ShiftDownS2CPacket.handler)
		HandAnimations.registerEntries()
	}
}