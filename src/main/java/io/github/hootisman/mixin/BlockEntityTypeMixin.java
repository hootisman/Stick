package io.github.hootisman.mixin;

import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import io.github.hootisman.block.StickBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Util;
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


    @Inject(method = "create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$Builder;)Lnet/minecraft/block/entity/BlockEntityType;", at = @At("HEAD"), cancellable = true)
    private static <T extends BlockEntity> void createBrushableBlockEntity(String id, BlockEntityType.Builder<T> builder, CallbackInfoReturnable<BlockEntityType<T>> cir){
        if (id.equals("brushable_block")){
            Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
            LogUtils.getLogger().info("did we register?");

            BlockEntityType.Builder<BrushableBlockEntity> newBuilder = BlockEntityType.Builder.create(BrushableBlockEntity::new,
                    Blocks.SUSPICIOUS_GRAVEL,
                    Blocks.SUSPICIOUS_SAND,
                    StickBlocks.INSTANCE.getSUSPICIOUS_STONE());

            cir.setReturnValue((BlockEntityType<T>) Registry.register(Registries.BLOCK_ENTITY_TYPE, id, newBuilder.build(type)));
        }
    }
}
