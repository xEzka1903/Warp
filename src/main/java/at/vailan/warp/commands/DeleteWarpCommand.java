package at.vailan.warp.commands;

import at.vailan.warp.Permissions;
import at.vailan.warp.Warp;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteWarpCommand implements BasicCommand {

    private final Warp plugin;

    public DeleteWarpCommand(Warp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        var sender = source.getSender();

        if (!(sender instanceof Player player)) {
            return;
        }

        if (!player.hasPermission(Permissions.DELETE)) {
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

        if (!plugin.getWarpManager().warpExists(warpName)) {
            player.sendMessage(plugin.getMessage("warp-not-existing", "warp", warpName));
            return;
        }

        plugin.getWarpManager().removeWarp(warpName);
        player.sendMessage(plugin.getMessage("warp-successfully-deleted", "warp", warpName));
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {

        List<String> warps = new ArrayList<>(plugin.getWarpManager().getAllWarpNames());

        if (args.length == 0) {
            return warps;
        }

        if (args.length == 1) {
            String partial = args[0].toLowerCase();

            return warps.stream()
                    .filter(w -> w.toLowerCase().startsWith(partial))
                    .toList();
        }

        return List.of();
    }

}