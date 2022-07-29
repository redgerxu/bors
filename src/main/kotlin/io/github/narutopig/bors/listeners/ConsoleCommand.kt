package io.github.narutopig.bors.listeners

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.util.Discord
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent

class ConsoleCommand : Listener {
    @EventHandler
    fun commandUsed(event: ServerCommandEvent) {
        if (Main.configuration.getString(("commandlog")) == "false" || event.command.lowercase() == "list") return
        // server automatically sends /help (at least for apex) hosting
        val message = "[Server]: Console used /${event.command}"
        Bukkit.broadcastMessage("${ChatColor.DARK_RED}${message}")
        val webhook = Main.configuration.getString("webhook") ?: throw NullPointerException()
        try {
            Discord.sendMessage(webhook, message)
        } catch (e: Exception) {
            if (webhook.trim() == "") return
            println("Webhook failed")
            e.printStackTrace()
        }
    }
}