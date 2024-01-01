package hootisman.stick.mixin.server;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin{

    @Inject(method = "brush",at = @At("HEAD"), cancellable = true)
    public void brush(long worldTime, Player player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir) {
//        BlockState state = ((BlockEntity)(Object)this).getCachedState();
//        if (!(player.getActiveItem().isOf(Items.BRUSH) == isBrushable(state))){
//            cir.setReturnValue(false);
//        }
    }

    @Unique
    public boolean isBrushable(BlockState state){
        return state.is(Blocks.SUSPICIOUS_GRAVEL) || state.is(Blocks.SUSPICIOUS_SAND);
    }
}
