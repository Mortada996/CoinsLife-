package com.coinslife

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import java.util.*
import kotlin.random.Random

object CoinsManager {
    private val balances = mutableMapOf<UUID, Int>()

    // Logic for /coinslife start command
    fun startNewSession(player: ServerPlayerEntity): Int {
        val randomValue = Random.nextFloat()
        val coins = when {
            randomValue < 0.70 -> 80 // 70% chance to get 80 coins
            else -> Random.nextInt(55, 91) // 30% chance for 55-90
        }
        balances[player.uuid] = coins
        updatePlayerDisplay(player)
        return coins
    }

    fun getBalance(player: ServerPlayerEntity): Int = balances.getOrDefault(player.uuid, 0)

    fun modifyBalance(player: ServerPlayerEntity, amount: Int) {
        val current = getBalance(player)
        val newBalance = (current + amount).coerceAtLeast(0)
        balances[player.uuid] = newBalance
        updatePlayerDisplay(player)
    }

    fun updatePlayerDisplay(player: ServerPlayerEntity) {
        val coins = getBalance(player)
        val color = when {
            coins <= 15 -> Formatting.RED
            coins <= 30 -> Formatting.YELLOW
            else -> Formatting.DARK_GREEN
        }
        
        // Update Tab List and Chat Name color
        player.setCustomName(Text.literal(player.gameProfile.name).formatted(color))
        player.isCustomNameVisible = true
    }
}

