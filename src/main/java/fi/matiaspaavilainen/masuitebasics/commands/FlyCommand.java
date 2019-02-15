package fi.matiaspaavilainen.masuitebasics.commands;

import fi.matiaspaavilainen.masuitecore.bukkit.chat.Formator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BukkitConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Aljosha on 13.02.2019
 */
public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Formator formator = new Formator();
        BukkitConfiguration config = new BukkitConfiguration();
        if (args.length > 1) {
            formator.sendMessage(commandSender, config.load("basics", "syntax.yml").getString("fly.change"));
            return true;
        }
        if (args.length == 0) {
            if (commandSender.hasPermission("masuitebasics.fly")) { //TODO perm
                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("fly.disabled-own"));
                    } else {
                        player.setAllowFlight(true);
                        formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("fly.enabled-own"));
                    }
                } else {
                    formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("only-for-players"));
                }
            } else {
                formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("no-permission"));
            }
            return true;
        }
        if (commandSender.hasPermission("masuitebasics.fly.others")) { //TODO perm
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                if (target.getAllowFlight()) {
                    target.setAllowFlight(false);
                    String message = config.load("basics", "messages.yml").getString("fly.disabled-other");
                    message = message.replaceAll("%target%", target.getDisplayName());
                    formator.sendMessage(commandSender, message);
                    formator.sendMessage(target, config.load("basics", "messages.yml").getString("fly.disabled-own"));
                } else {
                    target.setAllowFlight(true);
                    String message = config.load("basics", "messages.yml").getString("fly.enabled-other");
                    message = message.replaceAll("%target%", target.getDisplayName());
                    formator.sendMessage(commandSender, message);
                    formator.sendMessage(target, config.load("basics", "messages.yml").getString("fly.enabled-own"));
                }
            } else {
                formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("player-not-online"));
            }
        } else {
            formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("no-permission"));
        }
        return true;
    }
}
