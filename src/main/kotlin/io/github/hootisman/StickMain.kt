package io.github.hootisman

import io.github.hootisman.block.StickBlocks
import io.github.hootisman.entity.StickEntities
import io.github.hootisman.item.StickItems
import io.github.hootisman.network.StickServerPackets
import io.github.hootisman.network.ShiftDownC2SPacket
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object StickMain : ModInitializer {
	const val MOD_ID: String = "stick"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		StickItems.addItems()
		StickEntities.addEntityTypes()
		StickBlocks.addBlocks()

		StickServerPackets.registerPacket(ShiftDownC2SPacket.handler)
	}
}