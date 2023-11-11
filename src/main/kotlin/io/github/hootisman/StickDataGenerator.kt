package io.github.hootisman

import io.github.hootisman.item.HootItemRegistry
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.item.Item
import net.minecraft.util.Identifier

object StickDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModelGen)
	}

	private class ModelGen(output: FabricDataOutput?) : FabricModelProvider(output) {

		override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
		}
		override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
			this.registerItemModelTwoLayers(itemModelGenerator, HootItemRegistry.STICK, Models.HANDHELD_ROD,
				Identifier("tipped_arrow_head").withPrefixedPath("item/"),
				TextureMap.getId(HootItemRegistry.STICK))
		}
		fun registerItemModelTwoLayers(itemModelGenerator: ItemModelGenerator?, item: Item, model: Model, layer0: Identifier,layer1: Identifier){
			model.upload(ModelIds.getItemModelId(item), TextureMap.layered(layer0, layer1), itemModelGenerator?.writer)
		}
	}
}