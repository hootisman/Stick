package hootisman.stick

import com.mojang.logging.LogUtils
import hootisman.stick.block.StickBlocks
import hootisman.stick.item.StickItems
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.Mod
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.event.server.ServerStartingEvent
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Consumer
import java.util.function.Supplier

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
        StickCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus)
        NeoForge.EVENT_BUS.register(this)

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
        if (event.tabKey === CreativeModeTabs.BUILDING_BLOCKS) event.accept(StickItems.EXAMPLE_BLOCK_ITEM)
    }
}