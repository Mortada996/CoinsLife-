package com.coinslife

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object CoinsLifeClient : ClientModInitializer {
    private val LOGGER = LoggerFactory.getLogger("coinslife-client")

    override fun onInitializeClient() {
        // Initialization for dynamic textures and model loading
        LOGGER.info("CoinsLife Client: Dynamic rendering systems active.")
    }
}
