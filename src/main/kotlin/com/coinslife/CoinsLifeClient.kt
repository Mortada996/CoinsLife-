package com.coinslife

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier

object CoinsLifeClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This registers the client-side logic to handle custom item textures
        // In Fabric, dynamic item textures from external files (config) 
        // usually require a Dynamic Texture Registration.
        
        LOGGER_CLIENT.info("CoinsLife Client-Side initialized.")
    }

    private val LOGGER_CLIENT = org.slf4j.LoggerFactory.getLogger("coinslife-client")
}
