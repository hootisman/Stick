package hootisman.stick

import com.mojang.logging.LogUtils
import hootisman.stick.init.StickBlocks
import hootisman.stick.init.StickEntities
import hootisman.stick.init.StickCreativeTabs
import hootisman.stick.init.StickItems
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
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

        StickBlocks.BLOCKS.register(modEventBus)
        StickItems.ITEMS.register(modEventBus)
        StickEntities.ENTITIES.register(modEventBus)
        StickCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus)
//        NeoForge.EVENT_BUS.register(this)

        modEventBus.addListener { event: BuildCreativeModeTabContentsEvent? ->
            this.addCreative(event!!)
        }
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