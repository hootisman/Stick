package hootisman.stick.datagen

import hootisman.stick.StickMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent

object StickDataGen {
    fun onGatherDataEvent(event: GatherDataEvent){
        val pack: PackOutput = event.generator.packOutput
        val fileHelper: ExistingFileHelper = event.existingFileHelper
        event.generator.addProvider(event.includeClient(), StickItemModelProvider(pack, StickMod.MODID, fileHelper))
    }
}