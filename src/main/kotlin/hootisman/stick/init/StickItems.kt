package hootisman.stick.init

import hootisman.stick.StickMod
import hootisman.stick.item.GeopickItem
import hootisman.stick.item.StickItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.PotionUtils
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.registries.DeferredRegister

object StickItems {
    val ITEMS = DeferredRegister.createItems(StickMod.MODID)

    val STICK = ITEMS.register("stick") {
        _ -> StickItem(Item.Properties())
    }
    val GEOPICK = ITEMS.register("geopick") {
        _ -> GeopickItem(Item.Properties())
    }

    fun onRegisterColorHandlers(event: RegisterColorHandlersEvent.Item){
        event.register(
            {
                stack, tintIndex -> if (tintIndex == 0) PotionUtils.getColor(stack) else -1
            },
            STICK
        )
    }
}