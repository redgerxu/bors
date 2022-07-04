package io.github.narutopig.bors.listeners

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent

class ConsoleCommand : Listener {
    @EventHandler
    fun commandUsed(event: ServerCommandEvent) {
        Bukkit.broadcastMessage("${ChatColor.DARK_RED}[Server]: Console used /${event.command}")
    }
}