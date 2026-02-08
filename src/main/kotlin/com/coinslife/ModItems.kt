package com.coinslife

import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World

object ModItems {
    // Custom Item class to handle particles
    val PLAYER_COIN = object : Item(Item.Settings().maxCount(100)) {
        
        // This function runs every tick while the item is in the inventory
        override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
            // Only show animation if the player is holding the coin and world is client-side
            if (selected && world.isClient) {
                if (world.random.nextFloat() < 0.2f) { // Adjust frequency of sparks
                    val x = entity.x + (world.random.nextDouble() - 0.5) * 0.8
                    val y = entity.y + 1.2 + (world.random.nextDouble() - 0.5) * 0.8
                    val z = entity.z + (world.random.nextDouble() - 0.5) * 0.8
                    
                    // Using WAX_ON particles for that bright yellow "sparkle" effect
                    world.addParticle(ParticleTypes.WAX_ON, x, y, z, 0.0, 0.05, 0.0)
                }
            }
        }
    }

    fun register() {
        Registry.register(Registries.ITEM, Identifier.of("coinslife", "player_coin"), PLAYER_COIN)
    }
}
