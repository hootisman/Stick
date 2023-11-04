package io.github.hootisman

import io.github.hootisman.item.HootItemRegistry
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

object StickDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModelGen)
	}

	private class ModelGen(output: FabricDataOutput?) : FabricModelProvider(output) {

		override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
		}

		override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
			itemModelGenerator?.register(HootItemRegistry.STICK, Models.HANDHELD_ROD)
		}

	}
}