package io.github.hootisman

import io.github.hootisman.block.StickBlocks
import io.github.hootisman.item.StickItems
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.client.*
import net.minecraft.data.server.loottable.vanilla.VanillaBlockLootTableGenerator
import net.minecraft.item.Item
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.Identifier
import java.util.*
import java.util.concurrent.CompletableFuture

object StickDataGen : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModelGen)
		pack.addProvider(::TagGen)
		pack.addProvider(::LootTableGen)
	}
	private class LootTableGen(dataOutput: FabricDataOutput?) : FabricBlockLootTableProvider(dataOutput) {
		override fun generate() {
//			addDrop(StickBlocks.SUSPICIOUS_STONE, VanillaBlockLootTableGenerator.dropsNothing())
		}

	}
	private class TagGen(output: FabricDataOutput?,
						 registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
	) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
		override fun configure(arg: RegistryWrapper.WrapperLookup?) {
//			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(StickBlocks.SUSPICIOUS_STONE)
		}

	}
	private class ModelGen(output: FabricDataOutput?) : FabricModelProvider(output) {

		override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
//			blockStateModelGenerator?.registerBrushableBlock(StickBlocks.SUSPICIOUS_STONE)
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