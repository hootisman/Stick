package io.github.hootisman.mixin.client;


import io.github.hootisman.animation.AnimationUtil;
import io.github.hootisman.util.CustomAnimatedItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        //if item has the interface, will have a custom 1st person animation and will cancel the rest of the method at the end
        if (item.getItem() instanceof CustomAnimatedItem customItem){
            matrices.push();
            HeldItemRenderer heldItemRenderer = (HeldItemRenderer) (Object) this;   //working 'this'
            Arm arm = (hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite());
            boolean isRightArm = (arm == Arm.RIGHT);

            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand){
                AnimationUtil.INSTANCE.doHeldItemAnimation(heldItemRenderer,matrices,tickDelta,arm,item,equipProgress);
            } else {
                AnimationUtil.INSTANCE.doDefaultItemAnimation(heldItemRenderer,matrices,arm,equipProgress,swingProgress);
            }

            this.renderItem(player, item,
                    isRightArm ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                    !isRightArm, matrices, vertexConsumers, light);

            matrices.pop();
            ci.cancel();
        }
    }
}
