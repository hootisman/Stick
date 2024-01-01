package hootisman.stick.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light);

    //TODO uncomment when other classes moved
//    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
//    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
//        //if item has the interface, will have a custom 1st person animation and will cancel the rest of the method at the end
//        if (item.getItem() instanceof CustomAnimatedItem customItem){
//            matrices.push();
//            HeldItemRenderer heldItemRenderer = (HeldItemRenderer) (Object) this;   //working 'this'
//            Arm arm = (hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite());
//            boolean isRightArm = (arm == Arm.RIGHT);
//
//            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand){
//                HandAnimations.INSTANCE.doHeldItemAnimation(heldItemRenderer,matrices,tickDelta,arm,item,equipProgress,swingProgress);    //player is assumed to be ClientPlayerEntity
//            } else {
//                HandAnimations.INSTANCE.doDefaultItemAnimation(heldItemRenderer,matrices,arm,equipProgress,swingProgress);
//            }
//
//            this.renderItem(player, item,
//                    isRightArm ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
//                    !isRightArm, matrices, vertexConsumers, light);
//
//            matrices.pop();
//            ci.cancel();
//        }
//    }
}
