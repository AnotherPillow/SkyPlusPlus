package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

//? if >=1.20.4 {
/*import net.minecraft.nbt.NbtSizeTracker;
*///?} else {
import net.minecraft.nbt.NbtTagSizeTracker;
 //?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.20.4 {
/*@Mixin(NbtSizeTracker.class)
*///?} else {
@Mixin(NbtTagSizeTracker.class)
 //?}
public class NbtTagSizeTrackerMixin {
    @Shadow
    private long allocatedBytes;

    private SkyPlusPlusConfig config = null;

    @Inject(method = "add(J)V", at=@At("HEAD"), cancellable = true)
    public void add(long bits, CallbackInfo ci) {
        if (this.config == null) this.config = SkyPlusPlusConfig.configInstance.getConfig();

        this.allocatedBytes += bits / 8L;
//        System.out.println("trying to add nbt, alloc bytes: " + this.allocatedBytes + " anti pc enabled: " + config.antiPCEnabled);
        if ((this.allocatedBytes > 2097152L) && !config.antiPCEnabled) { // value used in PacketByteBuf#readNbt's call to constructor.
//            System.out.println(this.allocatedBytes + " is too big and config.antiPCEnabled: " + config.antiPCEnabled);
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.allocatedBytes + " bytes where max allowed: 2097152L (AntiPC is disabled)");
        }
        ci.cancel();
    }
}
