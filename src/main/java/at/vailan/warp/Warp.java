package at.vailan.warp;

import at.vailan.warp.commands.CommandRegistration;
import at.vailan.warp.manager.WarpManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Warp extends JavaPlugin {

    private static Warp plugin;

    public static Warp getInstance() { return plugin; }

    private WarpManager warpManager;

    public WarpManager getWarpManager() { return warpManager; }

    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();
        warpManager = new WarpManager(this);
        warpManager.loadWarps();

        new CommandRegistration(this);

        new bStats(this);

        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        warpManager.saveWarps();

        getLogger().info("Plugin disabled.");
    }

    public String getPrefix() {
        return getConfig().getString("prefix", "§8[§6Warp§8] ");
    }

    public String getMessage(String key, String... placeholders) {
        String msg = getConfig().getString("messages." + key, "");

        if (placeholders.length % 2 == 0) {
            for (int i = 0; i < placeholders.length; i += 2) {
                String ph = placeholders[i];
                String value = placeholders[i + 1];
                msg = msg.replace("%" + ph + "%", value);
            }
        }

        return getPrefix() + msg;
    }

}