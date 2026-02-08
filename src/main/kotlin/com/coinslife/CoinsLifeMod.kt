package com.coinslife

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class CoinsLifeMod : ModInitializer {
    override fun onInitialize() {
        // This line is VERY important to activate your coin
        ModItems.register() 
        
        DeathListener.register()
        CoinsCommands.register()
        
        LOGGER.info("CoinsLife: Mod items and systems are now active!")
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger("coinslife")
    }
}
