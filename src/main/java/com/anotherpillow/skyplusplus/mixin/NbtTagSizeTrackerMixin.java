package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.nbt.NbtTagSizeTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NbtTagSizeTracker.class)
public class NbtTagSizeTrackerMixin {
    @Shadow
    private long allocatedBytes;

    private SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

    @Inject(method = "Lnet/minecraft/nbt/NbtTagSizeTracker;add(J)V", at=@At("HEAD"), cancellable = true)
    public void add(long bits, CallbackInfo ci) {
        this.allocatedBytes += bits / 8L;
        if (this.allocatedBytes > 2097152L && !config.antiPCEnabled) { // value used in PacketByteBuf#readNbt's call to constructor.
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.allocatedBytes + "bytes where max allowed: 2097152L");
        }
        ci.cancel();
    }
}
