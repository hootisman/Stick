package hootisman.stick.datagen

import hootisman.stick.init.StickItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.client.model.generators.ModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class StickItemModelProvider(output: PackOutput?, modid: String?, existingFileHelper: ExistingFileHelper?) :
    ItemModelProvider(output, modid, existingFileHelper) {
    override fun registerModels() {
        //Geopick
        this.getBuilder(StickItems.GEOPICK.id.toString())
            .parent(ModelFile.UncheckedModelFile("item/handheld"))
            .texture("layer0",this.modLoc("item/geopick"))

        //Stick
        this.getBuilder(StickItems.STICK.id.toString())
            .parent(ModelFile.UncheckedModelFile("item/handheld_rod"))
            .texture("layer0",this.modLoc("item/stick_head"))
            .texture("layer1",this.modLoc("item/stick"))
    }
}