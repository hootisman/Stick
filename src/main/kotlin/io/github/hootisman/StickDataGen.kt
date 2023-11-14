package io.github.hootisman

import io.github.hootisman.item.StickItems
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import java.util.*

object StickDataGen : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModelGen)
	}

	private class ModelGen(output: FabricDataOutput?) : FabricModelProvider(output) {

		override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
		}
		override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
			val handheldRodModel: Model = createTwoLayeredModel("handheld_rod")
			this.registerItemModelTwoLayers(itemModelGenerator, StickItems.STICK, handheldRodModel,
				Identifier(StickMain.MOD_ID,"stick_head").withPrefixedPath("item/"),
				TextureMap.getId(StickItems.STICK))
			itemModelGenerator?.register(StickItems.GEOPICK, Models.HANDHELD)
		}
		fun registerItemModelTwoLayers(itemModelGenerator: ItemModelGenerator?, item: Item, model: Model, layer0: Identifier,layer1: Identifier){
			model.upload(ModelIds.getItemModelId(item), TextureMap.layered(layer0, layer1), itemModelGenerator?.writer)
		}
		fun createTwoLayeredModel(parent: String): Model = Model(
			Optional.of(Identifier(parent).withPrefixedPath("item/")),
			Optional.empty(),
			TextureKey.LAYER0, TextureKey.LAYER1
		)
	}
}