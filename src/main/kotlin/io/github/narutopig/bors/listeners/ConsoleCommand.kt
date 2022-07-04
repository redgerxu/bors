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
        val message = "${ChatColor.DARK_RED}[Server]: Console used /${event.command}"
        Bukkit.broadcastMessage(message)
        try {
            val webhook = Main.configuration.getString("options.webhook") ?: throw NullPointerException()
            Discord.sendMessage(webhook, message)
        } catch (ignored: Exception) {
        }
    }
}