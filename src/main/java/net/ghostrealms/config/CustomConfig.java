package net.ghostrealms.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by James on 8/7/2015.
 */
public class CustomConfig {
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    private Plugin plugin;
    private String name;

    public CustomConfig(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        reloadConfig();
    }

    public void reloadConfig() {
        if (customConfigFile == null)
            customConfigFile = new File(plugin.getDataFolder(), name + ".yml");
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        Reader reader = null;
        try {
            reader = new InputStreamReader(plugin.getResource(name + ".yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (reader != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
            customConfig.setDefaults(config);
        }
    }

    public FileConfiguration getConfig() {
        return customConfig;
    }

    public File getConfigFile() {
        return customConfigFile;
    }

    public void saveConfig() {
        if (customConfig == null || customConfigFile == null) return;
        try {
            getConfig().save(customConfigFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (customConfigFile == null)
            customConfigFile = new File(plugin.getDataFolder(), name + ".yml");

        if (!customConfigFile.exists()) plugin.saveResource(name + ".yml", false);
    }
}
