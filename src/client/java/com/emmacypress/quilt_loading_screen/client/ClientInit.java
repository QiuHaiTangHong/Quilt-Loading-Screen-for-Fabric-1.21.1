/*
 * Copyright (c) 2021, 2022, 2023 darkerbit
 * Copyright (c) 2021, 2022, 2023 triphora
 * Copyright (c) 2024 hibi
 *
 * Quilt Loading Screen is under the MIT License. See LICENSE for details.
 */

package com.emmacypress.quilt_loading_screen.client;

import com.emmacypress.quilt_loading_screen.client.mixin.LoadingOverlayAccessor;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

import static com.emmacypress.quilt_loading_screen.client.QuiltLoadingScreenClient.MODID;

/**
 * This should be the only class in the entire mod that interacts with anything loader-specific.
 */
public class ClientInit implements ClientModInitializer {
	ModContainer container = FabricLoader.getInstance()
			.getModContainer(MODID)
			.orElseThrow(() -> new RuntimeException("Could not find ModContainer for " + MODID));
	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MODID, Config.class);
		ResourceLoader.registerBuiltinPack(
				QuiltLoadingScreenClient.id("stronger_quilt_branding"),
				container,
				Component.translatable("quilt_loading_screen.resource_pack.name"),
				PackActivationType.NORMAL
		);
		if (Config.modifyBackgroundColor){
			LoadingOverlayAccessor.setMojangRed(ARGB.color(0, 35, 22, 56));
		}
	}
}
