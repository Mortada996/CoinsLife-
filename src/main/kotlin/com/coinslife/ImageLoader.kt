package com.coinslife

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.network.ServerPlayerEntity
import java.io.File
import java.net.URL
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.BufferedImage

object ImageLoader {
    private val configPath = FabricLoader.getInstance().configDir.resolve("coinslife/players").toFile()

    fun setupPlayerImage(player: ServerPlayerEntity) {
        if (!configPath.exists()) configPath.mkdirs()

        val playerFile = File(configPath, "${player.gameProfile.name}.png")
        
        if (!playerFile.exists()) {
            try {
                // 1. Download player skin head
                val uuid = player.uuid.toString().replace("-", "")
                val headUrl = URL("https://minotar.net/helm/$uuid/64.png")
                val headImg = ImageIO.read(headUrl)

                // 2. Create a new blank image (the final coin)
                val combined = BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)
                val g = combined.createGraphics()

                // 3. Draw a gold circle background (The "Yellow Glow")
                g.color = java.awt.Color(255, 215, 0) // Gold Color
                g.fillOval(0, 0, 64, 64)
                
                // 4. Draw the player face in the center
                g.drawImage(headImg, 8, 8, 48, 48, null)
                
                g.dispose()
                ImageIO.write(combined, "png", playerFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

