package hootisman.stick.init

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.logging.LogUtils
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.MultiBufferSource.BufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.BlockRenderDispatcher
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RenderLevelStageEvent
import net.neoforged.neoforge.client.model.data.ModelData
import net.neoforged.neoforge.client.model.data.ModelDataManager
import net.neoforged.neoforge.client.model.lighting.LightPipelineAwareModelBlockRenderer

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object StickRenderEvents {
    @SubscribeEvent
    fun onRenderAfterSolidBlocks(event: RenderLevelStageEvent){
        if (event.stage != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) return

        val buffer: BufferSource = Minecraft.getInstance().renderBuffers().bufferSource()
        val blockRenderer: BlockRenderDispatcher = Minecraft.getInstance().blockRenderer
        val matrixStack: PoseStack = event.poseStack
        val camera: Camera = event.camera
        val block: BlockState = Blocks.MELON.defaultBlockState()
        val blockPos: BlockPos = BlockPos(0, 70, 0)

        matrixStack.pushPose()
        matrixStack.translate(-camera.position.x + blockPos.x, -camera.position.y + blockPos.y, -camera.position.z + blockPos.z)

        blockRenderer.renderSingleBlock(block, matrixStack, buffer, 0, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid())

        matrixStack.popPose()

    }
}