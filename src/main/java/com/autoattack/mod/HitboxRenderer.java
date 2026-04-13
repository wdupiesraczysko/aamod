package com.autoattack.mod;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Box;

@Mixin(EntityRenderer.class)
public class HitboxRenderer {

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"))
    private void onRender(CallbackInfo ci) {}
}
