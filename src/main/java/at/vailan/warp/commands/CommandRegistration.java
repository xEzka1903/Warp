package at.vailan.warp.commands;

import at.vailan.warp.Warp;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import java.util.List;

public record CommandRegistration(Warp plugin) {

    public CommandRegistration {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(
                    "warp",
                    List.of("w"),
                    new WarpCommand(plugin)
            );

            event.registrar().register(
                    "setwarp",
                    new SetWarpCommand(plugin)
            );

            event.registrar().register(
                    "deletewarp",
                    List.of("delwarp"),
                    new DeleteWarpCommand(plugin)
            );
        });
    }

}