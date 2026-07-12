package at.vailan.warp.commands;

import at.vailan.warp.Permissions;
import at.vailan.warp.Warp;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WarpCommand implements BasicCommand {

    private final Warp plugin;

    public WarpCommand(Warp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        var sender = source.getSender();

        if (!sender.hasPermission(Permissions.USE)) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("not-enough-arguments"));
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(plugin.getMessage("too-many-arguments"));
            return;
        }

        String warpName = args[0].toLowerCase();

        if (!plugin.getWarpManager().warpExists(warpName)) {
            sender.sendMessage(plugin.getMessage("warp-not-existing", "warp", warpName));
            return;
        }

        if (args.length == 2) {
            if (!sender.hasPermission(Permissions.EXECUTE)) {
                sender.sendMessage(plugin.getMessage("no-permission"));
                return;
            }

            Player target = Bukkit.getPlayerExact(args[1]);

            if (target == null || !target.isOnline()) {
                sender.sendMessage(plugin.getMessage("player-not-existing", "player", args[1]));
                return;
            }

            target.teleport(plugin.getWarpManager().getWarp(warpName));
            return;
        }

        if (sender instanceof Player player) {
            player.teleport(plugin.getWarpManager().getWarp(warpName));
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        var sender = source.getSender();
        List<String> completions = new ArrayList<>();

        if (args.length == 0) {
            if (!sender.hasPermission(Permissions.USE)) {
                return completions;
            }

            completions.addAll(plugin.getWarpManager().getAllWarpNames());
        }

        else if (args.length == 2) {
            if (!sender.hasPermission(Permissions.EXECUTE)) {
                return completions;
            }

            for (Player online : Bukkit.getOnlinePlayers()) {
                completions.add(online.getName());
            }
        }

        return completions;
    }

}