package io.github.hootisman.entity

import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.util.Identifier
import net.minecraft.util.math.RotationAxis

class PotionSpellEntityRenderer<T: Entity?>(ctx: EntityRendererFactory.Context?) : EntityRenderer<T>(ctx) {
    val itemRend: ItemRenderer? = ctx?.itemRenderer

    override fun getTexture(entity: T): Identifier {
//        return Identifier("textures/item/egg.png")
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE
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