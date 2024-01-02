package hootisman.stick.init

import hootisman.stick.StickMod
import hootisman.stick.item.GeopickItem
import hootisman.stick.item.StickItem
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