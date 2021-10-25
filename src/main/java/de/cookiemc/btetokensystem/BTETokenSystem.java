package de.cookiemc.btetokensystem;

import de.cookiemc.btetokensystem.commands.AddShopToken;
import de.cookiemc.btetokensystem.commands.AddToken;
import de.cookiemc.btetokensystem.commands.Token;
import de.cookiemc.btetokensystem.commands.RemoveToken;
import de.cookiemc.btetokensystem.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BTETokenSystem extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        FileConfiguration config = this.getConfig();
        try {
            if (!config.getBoolean("config")) {

                config.addDefault("host", "localhost");
                config.addDefault("port", "3306");
                config.addDefault("database", "supa");
                config.addDefault("username", "dolle");
                config.addDefault("password", "datenbank");
                config.addDefault("prefix", "§eBTE-PS-Token:§r");
                config.addDefault("multiplicator", "2");
                config.addDefault("config", true);
                config.options().copyDefaults(true);
                saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage(this.getConfig().get("prefix").toString() + " " + "Plugin wird Gestartet...");
        MySQL.connect();
        try {
            Bukkit.getConsoleSender().sendMessage(this.getConfig().get("prefix").toString() + " " + "Commands werden Geladen...");
            this.getCommand("addshoptoken").setExecutor(new AddShopToken(this));
            this.getCommand("addtoken").setExecutor(new AddToken(this));
            this.getCommand("removetoken").setExecutor(new RemoveToken(this));
            this.getCommand("token").setExecutor(new Token(this));
            Bukkit.getConsoleSender().sendMessage(this.getConfig().get("prefix").toString() + " " + "Commands Geladen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        Bukkit.getConsoleSender().sendMessage(this.getConfig().get("prefix").toString() + " " + "Plugin Heruntergefahren");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
