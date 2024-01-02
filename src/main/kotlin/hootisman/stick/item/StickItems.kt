package hootisman.stick.item

import hootisman.stick.StickMod
import hootisman.stick.block.StickBlocks
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister

object StickItems {
    val ITEMS = DeferredRegister.createItems(StickMod.MODID)

    val STICK = ITEMS.register("stick") {
        _ -> StickItem(Item.Properties())
    }
    val GEOPICK = ITEMS.register("geopick") {
        _ -> GeopickItem(Item.Properties())
    }
}