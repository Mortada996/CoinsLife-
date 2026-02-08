package com.coinslife

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.Vec3ArgumentType
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object CoinsCommands {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(literal("coinslife")
                // Command: /coinslife start
                .then(literal("start")
                    .requires { it.hasPermissionLevel(2) }
                    .executes { context ->
                        val players = context.source.server.playerManager.playerList
                        players.forEach { player ->
                            val amount = CoinsManager.startNewSession(player)
                            player.sendMessage(Text.literal("You have $amount coins").formatted(Formatting.WHITE), true)
                        }
                        context.source.sendFeedback({ Text.literal("Session started for all players!") }, true)
                        1
                    }
                )
                // Command: /coinslife revive [player]
                .then(literal("revive")
                    .then(argument("player", EntityArgumentType.player())
                        .executes { context ->
                            val target = EntityArgumentType.getPlayer(context, "player")
                            val source = context.source.player ?: return@executes 0
                            
                            if (CoinsManager.getBalance(source) >= 15) {
                                CoinsManager.modifyBalance(source, -15)
                                CoinsManager.modifyBalance(target, 5)
                                source.sendMessage(Text.literal("I have revived ${target.gameProfile.name}").formatted(Formatting.GREEN), false)
                                1
                            } else {
                                source.sendMessage(Text.literal("You need 15 coins to revive someone!").formatted(Formatting.RED), false)
                                0
                            }
                        }
                    )
                )
                // Command: /coinslife coordinates death prison [x y z]
                .then(literal("coordinates")
                    .then(literal("death")
                        .then(literal("prison")
                            .then(argument("pos", Vec3ArgumentType.vec3())
                                .requires { it.hasPermissionLevel(2) }
                                .executes { context ->
                                    val pos = Vec3ArgumentType.getVec3(context, "pos")
                                    // Logic for saving coordinates will be handled by CoinsManager
                                    context.source.sendFeedback({ Text.literal("Prison coordinates set to: ${pos.x}, ${pos.y}, ${pos.z}") }, true)
                                    1
                                }
                            )
                        )
                    )
                )
            )
        }
    }
}
