/*
 * Copyright (c) 2021, 2022, 2023 darkerbit
 * Copyright (c) 2021, 2022, 2023 triphora
 * Copyright (c) 2024 hibi
 *
 * Quilt Loading Screen is under the MIT License. See LICENSE for details.
 */

package com.emmacypress.quilt_loading_screen.client.mixin;

import com.emmacypress.quilt_loading_screen.client.QuiltLoadingScreenClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin extends Overlay {
	@Final @Shadow private Minecraft minecraft;

	@Unique
    private QuiltLoadingScreenClient quiltLoadingScreen$loadingScreen;

	@Inject(
			method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/server/packs/resources/ReloadInstance;Ljava/util/function/Consumer;Z)V",
			at = @At("TAIL")
	)
	private void constructor(Minecraft client, ReloadInstance reload, Consumer<Optional<Throwable>> onFinish, boolean fadeIn, CallbackInfo ci) {
		quiltLoadingScreen$loadingScreen = new QuiltLoadingScreenClient(this.minecraft);
	}

	// Render before logo
	@Inject(
		method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
		at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blit (Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0),
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void renderPatches(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci,
	                           int width, int height, long timestamp, float fadeOutTime, float fadeInTime, float alpha) {
		quiltLoadingScreen$loadingScreen.renderPatches(graphics, delta, fadeOutTime >= 1.0f, alpha);
	}
}
