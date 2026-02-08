package com.coinslife

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class CoinsLifeMod : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("CoinsLife initialized successfully.")
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger("coinslife")
    }
}
