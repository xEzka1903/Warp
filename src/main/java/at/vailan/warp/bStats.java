package at.vailan.warp;

import org.bstats.bukkit.Metrics;

public class bStats {

    private final Warp plugin;

    public bStats(Warp plugin) {
        this.plugin = plugin;

        int pluginId = 18553;
        Metrics metrics = new Metrics(plugin, pluginId);
    }

}
