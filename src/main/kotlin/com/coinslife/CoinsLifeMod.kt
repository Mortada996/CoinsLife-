package com.coinslife

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class CoinsLifeMod : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("CoinsLife initialized! Balance is your lifeline.")
        // هنا سنضيف لاحقاً نظام الكوينز والـ Boogeyman
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger("coinslife")
    }
}
