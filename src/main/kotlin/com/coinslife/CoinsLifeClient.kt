package com.coinslife

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformationMode

object CoinsLifeClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This is where the magic happens to render the config image on the coin
        // We will add the dynamic rendering logic here in the next step
    }
}
