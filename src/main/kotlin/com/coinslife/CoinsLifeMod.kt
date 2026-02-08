package com.coinslife

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class CoinsLifeMod : ModInitializer {
    override fun onInitialize() {
        DeathListener.register()
        CoinsCommands.register() 
        
        LOGGER.info("CoinsLife: All systems and commands are live!")
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger("coinslife")
    }
}
