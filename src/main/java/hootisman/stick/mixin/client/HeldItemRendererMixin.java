package hootisman.stick.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import hootisman.stick.animation.HandAnimations;
import hootisman.stick.util.CustomAnimatedItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
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

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void onRenderFirstPersonItem(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        //if item has the interface, will have a custom 1st person animation and will cancel the rest of the method at the end
        if (item.getItem() instanceof CustomAnimatedItem customItem){
            matrices.pushPose();
            ItemInHandRenderer heldItemRenderer = (ItemInHandRenderer) (Object) this;   //working 'this'
            HumanoidArm arm = (hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite());
            boolean isRightArm = (arm == HumanoidArm.RIGHT);

            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand){
                HandAnimations.INSTANCE.doHeldItemAnimation(heldItemRenderer,matrices,tickDelta,arm,item,equipProgress,swingProgress);    //player is assumed to be ClientPlayerEntity
            } else {
                HandAnimations.INSTANCE.doDefaultItemAnimation(heldItemRenderer,matrices,arm,equipProgress,swingProgress);
            }

            this.renderItem(player, item,
                    isRightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    !isRightArm, matrices, vertexConsumers, light);

            matrices.popPose();
            ci.cancel();
        }
    }
}
