package com.coinslife

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.IntegerArgumentType
import net.minecraft.command.argument.Vec3ArgumentType
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object CoinsCommands {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            
            // --- أمر /coins الجديد (للمنح والمسح) ---
            dispatcher.register(literal("coins")
                .requires { it.hasPermissionLevel(2) }
                .then(literal("give")
                    .then(argument("targets", EntityArgumentType.players())
                        .then(argument("owner", EntityArgumentType.player())
                            .then(argument("amount", IntegerArgumentType.integer(1))
                                .executes { context ->
                                    val targets = EntityArgumentType.getPlayers(context, "targets")
                                    val owner = EntityArgumentType.getPlayer(context, "owner")
                                    val amount = IntegerArgumentType.getInteger(context, "amount")
                                    targets.forEach { target ->
                                        val stack = ModItems.PLAYER_COIN.defaultStack
                                        stack.count = amount
                                        val nbt = stack.orCreateNbt
                                        nbt.putString("CoinOwner", owner.gameProfile.name)
                                        stack.setCustomName(Text.literal("${owner.gameProfile.name}'s Coin").formatted(Formatting.YELLOW))
                                        target.inventory.offerOrDrop(stack)
                                    }
                                    1
                                }
                            )
                        )
                    )
                )
                .then(literal("clear")
                    .then(argument("targets", EntityArgumentType.players())
                        .then(argument("owner", EntityArgumentType.player())
                            .then(argument("amount", IntegerArgumentType.integer(1))
                                .executes { context ->
                                    val targets = EntityArgumentType.getPlayers(context, "targets")
                                    val ownerName = EntityArgumentType.getPlayer(context, "owner").gameProfile.name
                                    var countToRemove = IntegerArgumentType.getInteger(context, "amount")
                                    targets.forEach { target ->
                                        for (i in 0 until target.inventory.size()) {
                                            val stack = target.inventory.getStack(i)
                                            if (stack.item == ModItems.PLAYER_COIN && stack.nbt?.getString("CoinOwner") == ownerName) {
                                                val take = minOf(stack.count, countToRemove)
                                                stack.decrement(take)
                                                countToRemove -= take
                                                if (countToRemove <= 0) break
                                            }
                                        }
                                    }
                                    1
                                }
                            )
                        )
                    )
                )
            )

            // --- أمر /coinslife القديم (للبداية، الإحياء، والسجن) ---
            dispatcher.register(literal("coinslife")
                .then(literal("start")
                    .requires { it.hasPermissionLevel(2) }
                    .executes { context ->
                        val players = context.source.server.playerManager.playerList
                        players.forEach { player ->
                            val amount = CoinsManager.startNewSession(player)
                            player.sendMessage(Text.literal("You have $amount coins").formatted(Formatting.WHITE), true)
                        }
                        1
                    }
                )
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
                .then(literal("coordinates")
                    .then(literal("death")
                        .then(literal("prison")
                            .then(argument("pos", Vec3ArgumentType.vec3())
                                .requires { it.hasPermissionLevel(2) }
                                .executes { context ->
                                    val pos = Vec3ArgumentType.getVec3(context, "pos")
                                    context.source.sendFeedback({ Text.literal("Prison coordinates set!") }, true)
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
                                }
                            )
                        )
                    )
                )

                // Command: /coins clear <Target> <OwnerName> <Amount>
                .then(literal("clear")
                    .then(argument("targets", EntityArgumentType.players())
                        .then(argument("owner", EntityArgumentType.player())
                            .then(argument("amount", IntegerArgumentType.integer(1))
                                .executes { context ->
                                    val targets = EntityArgumentType.getPlayers(context, "targets")
                                    val ownerName = EntityArgumentType.getPlayer(context, "owner").gameProfile.name
                                    val amount = IntegerArgumentType.getInteger(context, "amount")
                                    
                                    targets.forEach { target ->
                                        var countToRemove = amount
                                        for (i in 0 until target.inventory.size()) {
                                            val stack = target.inventory.getStack(i)
                                            if (stack.item == ModItems.PLAYER_COIN && stack.nbt?.getString("CoinOwner") == ownerName) {
                                                val take = minOf(stack.count, countToRemove)
                                                stack.decrement(take)
                                                countToRemove -= take
                                                if (countToRemove <= 0) break
                                            }
                                        }
                                    }
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
