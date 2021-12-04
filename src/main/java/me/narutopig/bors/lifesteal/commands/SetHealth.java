package me.narutopig.bors.lifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.parseInt;

public class SetHealth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player && !commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You need operator permissions to use this command.");
            return false;
        }

        Player target;
        int index = 0;

        if (commandSender instanceof Player player) target = player;
        else {
            try {
                target = Bukkit.getPlayer(args[0]);
                if (target == null) throw new NullPointerException();
                index++;
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                commandSender.sendMessage(ChatColor.RED + "Please provide a valid player name.");
                return false;
            }
        }

        double amount;

        try {
            amount = parseInt(args[index]);
            if (amount < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Please provide a valid number.");
            return false;
        }

        try {
            AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) throw new NullPointerException();
            attribute.setBaseValue(amount);
        } catch (NullPointerException e) {
            e.printStackTrace();
            commandSender.sendMessage(ChatColor.RED + "Something went wrong, please make an issue at https://github.com/narutopig/bors");
            return false;
        }

        commandSender.sendMessage(ChatColor.GREEN + "Set player " + target.getName() + "'s health to " + amount);
        return true;
    }
}
