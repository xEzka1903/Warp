package at.vailan.warp.commands;

import at.vailan.warp.Permissions;
import at.vailan.warp.Warp;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetWarpCommand implements BasicCommand {

    private final Warp plugin;

    public SetWarpCommand(Warp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        var sender = source.getSender();

        if (!(sender instanceof Player player)) {
            return;
        }

        if (!player.hasPermission(Permissions.SET)) {
            player.sendMessage(plugin.getMessage("no-permission"));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(args.length == 0
                    ? plugin.getMessage("not-enough-arguments")
                    : plugin.getMessage("too-many-arguments"));
            return;
        }

        String warpName = args[0].toLowerCase();

        if (plugin.getWarpManager().warpExists(warpName)) {
            player.sendMessage(plugin.getMessage("warp-already-exists", "warp", warpName));
            return;
        }

        Location location = player.getLocation();

        plugin.getWarpManager().addWarp(warpName, location);
        player.sendMessage(plugin.getMessage("warp-successfully-created", "warp", warpName));
    }

}