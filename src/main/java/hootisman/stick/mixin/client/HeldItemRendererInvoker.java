package hootisman.stick.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

//TODO rename HeldItemRendererInvoker
@Mixin(ItemInHandRenderer.class)
public interface HeldItemRendererInvoker {
    @Accessor
    Minecraft getMinecraft();
    @Invoker("applyItemArmTransform")
    public void invokeApplyEquipOffset(PoseStack matrices, HumanoidArm arm, float equipProgress);

    @Invoker("applyItemArmAttackTransform")
    public void invokeApplySwingOffset(PoseStack matrices, HumanoidArm arm, float swingProgress);
}
