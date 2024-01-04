package hootisman.stick

import com.mojang.logging.LogUtils
import hootisman.stick.animation.HandAnimations
import hootisman.stick.datagen.StickDataGen
import hootisman.stick.init.*
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent

@Mod(StickMod.MODID)
class StickMod(modEventBus: IEventBus) {
    companion object {
        const val MODID = "stick"
    }

    init {
        modEventBus.addListener { event: FMLCommonSetupEvent? ->
            this.commonSetup(event!!)
        }

        //server
        StickBlocks.BLOCKS.register(modEventBus)
        StickItems.ITEMS.register(modEventBus)
        StickEntities.ENTITIES.register(modEventBus)
        StickCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus)

        modEventBus.addListener { event: BuildCreativeModeTabContentsEvent? ->
            this.addCreative(event!!)
        }

        //client

        modEventBus.addListener(StickDataGen::onGatherDataEvent)
        modEventBus.addListener(StickItems::onRegisterColorHandlers)
//        modEventBus.register(StickRenderEvents)
        modEventBus.register(StickEntityRenderers)
        HandAnimations.registerEntries()


//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }
    val LOGGER = LogUtils.getLogger()

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("HELLO FROM COMMON SETUP")

//        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT))
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber)
//        Config.items.forEach(Consumer { item: Item ->
//            LOGGER.info(
//                "ITEM >> {}",
//                item.toString()
//            )
//        })
    }

    private fun addCreative(event: BuildCreativeModeTabContentsEvent) {
//        if (event.tabKey === CreativeModeTabs.BUILDING_BLOCKS) event.accept(StickItems.EXAMPLE_BLOCK_ITEM)
    }
}