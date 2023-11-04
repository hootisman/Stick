package io.github.hootisman

import io.github.hootisman.item.HootItemRegistry
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Stick : ModInitializer {
	const val MOD_ID: String = "stick"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		HootItemRegistry.addModItems()
	}
}