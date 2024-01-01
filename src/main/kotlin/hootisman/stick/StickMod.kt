package hootisman.stick

import com.mojang.logging.LogUtils
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

    val BLOCKS = DeferredRegister.createBlocks(MODID)
    val ITEMS = DeferredRegister.createItems(MODID)
    val CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID)

    companion object {
        const val MODID = "stick"
    }

    init {
        modEventBus.addListener { event: FMLCommonSetupEvent? ->
            this.commonSetup(event!!)
        }
        BLOCKS.register(modEventBus)
        ITEMS.register(modEventBus)
        CREATIVE_MODE_TABS.register(modEventBus)
        NeoForge.EVENT_BUS.register(this)
        modEventBus.addListener { event: BuildCreativeModeTabContentsEvent? ->
            this.addCreative(event!!)
        }
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    private val LOGGER = LogUtils.getLogger()


    val EXAMPLE_BLOCK =
        BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE))

    val EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK)

    val EXAMPLE_ITEM = ITEMS.registerSimpleItem(
        "example_item", Item.Properties().food(
            FoodProperties.Builder()
                .alwaysEat().nutrition(1).saturationMod(2f).build()
        )
    )

    // Creates a creative tab with the id "stick:example_tab" for the hootisman item, that is placed after the combat tab
    val EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab",
        Supplier {
            CreativeModeTab.builder()
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon { EXAMPLE_ITEM.get().defaultInstance }
                .displayItems { parameters: ItemDisplayParameters?, output: CreativeModeTab.Output ->
                    output.accept(EXAMPLE_ITEM.get()) // Add the hootisman item to the tab. For your own tabs, this method is preferred over the event
                }.build()
        })


    private fun commonSetup(event: FMLCommonSetupEvent) {
        // Some common setup code
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

    // Add the hootisman block item to the building blocks tab
    private fun addCreative(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey === CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM)
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting")
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
//    object ClientModEvents {
//        @SubscribeEvent
//        fun onClientSetup(event: FMLClientSetupEvent?) {
//            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP")
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().user.name)
//        }
//    }

}