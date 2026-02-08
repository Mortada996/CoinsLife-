package com.coinslife

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class CoinsLifeMod : ModInitializer {
    override fun onInitialize() {
        // Register the death system (Penalty -5, -10 and Reward +5)
        DeathListener.register()
        
        LOGGER.info("CoinsLife: Systems activated and death listener registered.")
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger("coinslife")
    }
}
