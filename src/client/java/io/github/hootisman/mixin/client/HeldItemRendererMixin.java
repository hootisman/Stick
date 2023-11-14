package io.github.hootisman.mixin.client;

import com.mojang.logging.LogUtils;
import io.github.hootisman.util.AnimationUtil;
import io.github.hootisman.util.Custom1stPersonAnim;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (item.getItem() instanceof Custom1stPersonAnim customItem){
            LogUtils.getLogger().info("ITEM CUSTOM ANIMATION GOES HERE!");
            Arm arm = (hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite());
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand){
                AnimationUtil.INSTANCE.doFirstPersonItemAnimation(
                        (HeldItemRenderer) (Object) this, matrices, tickDelta, arm, item, equipProgress
                );
            }
//            ci.cancel();
        }
    }
}
