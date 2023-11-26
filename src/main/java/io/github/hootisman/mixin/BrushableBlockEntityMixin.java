package io.github.hootisman.mixin;

import io.github.hootisman.block.StickBlocks;
import io.github.hootisman.item.StickItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin{

    @Inject(method = "brush",at = @At("HEAD"), cancellable = true)
    public void brush(long worldTime, PlayerEntity player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir) {
//        BlockState state = ((BlockEntity)(Object)this).getCachedState();
//        if (!(player.getActiveItem().isOf(Items.BRUSH) == isBrushable(state))){
//            cir.setReturnValue(false);
//        }
    }

    @Unique
    public boolean isBrushable(BlockState state){
        return state.isOf(Blocks.SUSPICIOUS_GRAVEL) || state.isOf(Blocks.SUSPICIOUS_SAND);
    }
}