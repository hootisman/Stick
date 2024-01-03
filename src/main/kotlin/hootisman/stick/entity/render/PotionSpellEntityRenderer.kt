package hootisman.stick.entity.render

import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

class PotionSpellEntityRenderer<T: Entity?>(ctx: EntityRendererProvider.Context?) : EntityRenderer<T>(ctx) {
    val itemRend: ItemRenderer? = ctx?.itemRenderer

    override fun getTextureLocation(p_114482_: T): ResourceLocation {
        return TextureAtlas.LOCATION_BLOCKS
    }

//    override fun render(
//        entity: T,
//        yaw: Float,
//        tickDelta: Float,
//        matrices: MatrixStack?,
//        vertexConsumers: VertexConsumerProvider?,
//        light: Int
//    ) {
//        if ((entity?.age ?: 0) < 2 && dispatcher.camera.focusedEntity.squaredDistanceTo(entity) < 12.25) return
//
//        matrices?.push()
//        matrices?.scale(1.0f,1.0f,1.0f)
//        matrices?.multiply(dispatcher.rotation)
//        matrices?.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f))
//
//        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
//    }
}