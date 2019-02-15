package fi.matiaspaavilainen.masuitebasics.commands;

import fi.matiaspaavilainen.masuitecore.bukkit.chat.Formator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BukkitConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Aljosha on 13.02.2019
 */
public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Formator formator = new Formator();
        BukkitConfiguration config = new BukkitConfiguration();
        if (args.length < 1 || args.length > 2) {
            formator.sendMessage(commandSender, config.load("basics", "syntax.yml").getString("gm.change"));
            return true;
        }
        if (args.length == 1) {
            if (commandSender.hasPermission("masuitebasics.gm")) {
                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;
                    if (setGamemode(player, args[0])) {
                        String message = config.load("basics", "messages.yml").getString("gm.changed-own");
                        System.out.println("gm-msg: " + message);
                        message = message.replaceAll("%gamemode%", args[0]);
                        formator.sendMessage(player, message);
                    } else {
                        formator.sendMessage(commandSender, config.load("basics", "syntax.yml").getString("gm.unknown-gamemode"));
                    }
                } else {
                    formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("only-for-players"));
                }
            } else {
                formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("no-permission"));
            }
            return true;
        }
        if (commandSender.hasPermission("masuitebasics.gm.others")) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                if (setGamemode(target, args[0])) {
                    String message = config.load("basics", "messages.yml").getString("gm.changed-other");
                    message = message.replaceAll("%gamemode%", args[0]).replaceAll("%target%", target.getDisplayName());
                    formator.sendMessage(commandSender, message);
                    message = config.load("basics", "messages.yml").getString("gm.changed-own");
                    message = message.replaceAll("%gamemode%", args[0]);
                    formator.sendMessage(target, message);
                } else {
                    formator.sendMessage(commandSender, config.load("basics", "syntax.yml").getString("gm.unknown-gamemode"));
                }
            } else {
                formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("player-not-online"));
            }
        } else {
            formator.sendMessage(commandSender, config.load("basics", "messages.yml").getString("no-permission"));
        }
        return true;
    }

    private boolean setGamemode(Player player, String gamemode) {
        switch (gamemode.toLowerCase()) {
            case "1":
            case "creative":
                player.setGameMode(GameMode.CREATIVE);
                break;
            case "0":
            case "survival":
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case "2":
            case "adventure":
                player.setGameMode(GameMode.ADVENTURE);
                break;
            case "3":
            case "spectator":
                player.setGameMode(GameMode.SPECTATOR);
                break;
            default:
                return false;
        }
        return true;
    }
}
