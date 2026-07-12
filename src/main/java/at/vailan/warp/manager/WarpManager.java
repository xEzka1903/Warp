package at.vailan.warp.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {

    private final JavaPlugin plugin;
    private final File warpFile;
    private FileConfiguration warpConfig;

    private final Map<String, Location> warps = new HashMap<>();

    public WarpManager(JavaPlugin plugin) {
        this.plugin = plugin;
        warpFile = new File(plugin.getDataFolder(), "warps.yml");

        if (!warpFile.exists()) {
            plugin.saveResource("warps.yml", false);
        }

        warpConfig = YamlConfiguration.loadConfiguration(warpFile);
        loadWarps();
    }

    public void loadWarps() {
        warps.clear();
        if (warpConfig.contains("warps")) {
            for (String name : warpConfig.getConfigurationSection("warps").getKeys(false)) {
                String path = "warps." + name + ".";
                World world = Bukkit.getWorld(warpConfig.getString(path + "world"));
                double x = warpConfig.getDouble(path + "x");
                double y = warpConfig.getDouble(path + "y");
                double z = warpConfig.getDouble(path + "z");
                float yaw = (float) warpConfig.getDouble(path + "yaw");
                float pitch = (float) warpConfig.getDouble(path + "pitch");

                if (world != null) {
                    warps.put(name.toLowerCase(), new Location(world, x, y, z, yaw, pitch));
                }
            }
        }
    }

    public void saveWarps() {
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            String name = entry.getKey();
            Location loc = entry.getValue();
            String path = "warps." + name + ".";

            warpConfig.set(path + "world", loc.getWorld().getName());
            warpConfig.set(path + "x", loc.getX());
            warpConfig.set(path + "y", loc.getY());
            warpConfig.set(path + "z", loc.getZ());
            warpConfig.set(path + "yaw", loc.getYaw());
            warpConfig.set(path + "pitch", loc.getPitch());
        }

        try {
            warpConfig.save(warpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWarp(String name, Location loc) {
        warps.put(name.toLowerCase(), loc);
        saveWarps();
    }

    public void removeWarp(String name) {
        warps.remove(name.toLowerCase());
        warpConfig.set("warps." + name.toLowerCase(), null);
        saveWarps();
    }

    public Location getWarp(String name) {
        return warps.get(name.toLowerCase());
    }

    public boolean warpExists(String name) {
        return warps.containsKey(name.toLowerCase());
    }

    public List<String> getAllWarpNames() {
        return warps.keySet().stream().toList();
    }

}