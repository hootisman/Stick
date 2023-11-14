package io.github.hootisman.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HeldItemRenderer.class)
public interface HeldItemRendererInvoker {
    @Accessor
    MinecraftClient getClient();
    @Invoker("applyEquipOffset")
    public void invokeApplyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Invoker("applySwingOffset")
    public void invokeApplySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);
}
