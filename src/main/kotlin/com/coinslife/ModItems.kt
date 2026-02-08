package com.coinslife

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModItems {
    val PLAYER_COIN = Item(Item.Settings().maxCount(128))

    fun register() {
        Registry.register(Registries.ITEM, Identifier.of("coinslife", "player_coin"), PLAYER_COIN)
    }
}
