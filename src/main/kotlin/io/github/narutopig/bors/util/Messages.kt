package io.github.narutopig.bors.util

import org.bukkit.ChatColor

object Messages {
    // static object for error messages and other stuff
    private val err = "${ChatColor.DARK_RED}[Error]"
    val missingPermissions = "$err Missing Permissions"
    val disabledCommand = "$err This command is disabled"
}