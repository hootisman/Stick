package hootisman.stick

import hootisman.stick.item.StickItems
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object StickCreativeTabs {
    val CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StickMod.MODID)

//    val EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab",
//        Supplier {
//            CreativeModeTab.builder()
//                .withTabsBefore(CreativeModeTabs.COMBAT)
//                .icon { StickItems.EXAMPLE_ITEM.get().defaultInstance }
//                .displayItems { parameters: CreativeModeTab.ItemDisplayParameters?, output: CreativeModeTab.Output ->
//                    output.accept(StickItems.EXAMPLE_ITEM.get()) // Add the hootisman item to the tab. For your own tabs, this method is preferred over the event
//                }.build()
//        })
}