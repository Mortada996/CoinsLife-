package com.coinslife

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.network.ServerPlayerEntity
import java.io.File
import java.net.URL
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.geom.Ellipse2D

object ImageLoader {
    private val configPath = FabricLoader.getInstance().configDir.resolve("coinslife/players").toFile()

    fun setupPlayerImage(player: ServerPlayerEntity) {
        if (!configPath.exists()) configPath.mkdirs()

        val playerFile = File(configPath, "${player.gameProfile.name}.png")
        
        if (!playerFile.exists()) {
            try {
                // 1. Download player face
                val uuid = player.uuid.toString().replace("-", "")
                val headUrl = URL("https://minotar.net/helm/$uuid/64.png")
                val headImg = ImageIO.read(headUrl)

                // 2. Create the final image (64x64 for high quality pixels)
                val combined = BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)
                val g = combined.createGraphics()
                
                // Enable smooth rendering
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                // 3. Draw the Golden Frame (The image you chose)
                // Note: You should place your chosen coin image in: 
                // src/main/resources/assets/coinslife/textures/item/base_coin.png
                val baseCoinStream = ImageLoader::class.java.getResourceAsStream("/assets/coinslife/textures/item/base_coin.png")
                if (baseCoinStream != null) {
                    val baseCoin = ImageIO.read(baseCoinStream)
                    g.drawImage(baseCoin, 0, 0, 64, 64, null)
                } else {
                    // Fallback if base image is missing: Draw a simple gold circle
                    g.color = java.awt.Color(255, 215, 0)
                    g.fillOval(0, 0, 64, 64)
                }

                // 4. Create a Circular Clip for the player's face
                val circleClip = Ellipse2D.Float(12f, 12f, 40f, 40f)
                g.setClip(circleClip)
                
                // 5. Draw the face inside the circle (no gaps)
                g.drawImage(headImg, 12, 12, 40, 40, null)
                
                g.dispose()
                ImageIO.write(combined, "png", playerFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
