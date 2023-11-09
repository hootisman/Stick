package io.github.hootisman

import io.github.hootisman.entity.HootEntityRendererRegistry
import io.github.hootisman.network.ShiftKeyDownPacket
import net.fabricmc.api.ClientModInitializer

object StickClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HootEntityRendererRegistry.addEntityRenderers()

		ShiftKeyDownPacket.registerPacket()
	}
}