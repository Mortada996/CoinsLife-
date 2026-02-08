package com.coinslife

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity

object DeathListener {
    fun register() {
        ServerLivingEntityEvents.AFTER_DEATH.register { entity, damageSource ->
            if (entity is ServerPlayerEntity) {
                val killer = damageSource.attacker
                
                if (killer is ServerPlayerEntity && killer != entity) {
                    // PVP Death: Victim -10, Killer +5
                    CoinsManager.modifyBalance(entity, -10)
                    CoinsManager.modifyBalance(killer, 5)
                } else {
                    // Natural Death: Victim -5
                    CoinsManager.modifyBalance(entity, -5)
                }
            }
        }
    }
}
