package hootisman.stick.mixin.server;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {


    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;)Lnet/minecraft/world/level/block/entity/BlockEntityType;", at = @At("HEAD"), cancellable = true)
    private static <T extends BlockEntity> void createBrushableBlockEntity(String id, BlockEntityType.Builder<T> builder, CallbackInfoReturnable<BlockEntityType<T>> cir){
        if (id.equals("brushable_block")){
//            Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
//
//            BlockEntityType.Builder<BrushableBlockEntity> newBuilder = BlockEntityType.Builder.create(BrushableBlockEntity::new,
//                    Blocks.SUSPICIOUS_GRAVEL,
//                    Blocks.SUSPICIOUS_SAND,
//                    StickBlocks.INSTANCE.getSUSPICIOUS_STONE());
//
//            cir.setReturnValue((BlockEntityType<T>) Registry.register(Registries.BLOCK_ENTITY_TYPE, id, newBuilder.build(type)));
        }
    }
}
