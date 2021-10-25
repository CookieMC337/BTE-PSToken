package de.cookiemc.btetokensystem.commands;

import de.cookiemc.btetokensystem.BTETokenSystem;
import de.cookiemc.btetokensystem.database.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddShopToken implements CommandExecutor {
    private BTETokenSystem plugin;
    public AddShopToken(BTETokenSystem bteTokenSystem) {
        this.plugin = bteTokenSystem;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ((sender instanceof ConsoleCommandSender)) {

            if (args[0] == null || args[1] == null) {
                Bukkit.getConsoleSender().sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Bitte benutzte /addshoptoken {Username} {Tokens}");
                return true;
            } else {

                Player target = Bukkit.getPlayer(args[0]);
                PreparedStatement ps = null;
                ResultSet rs = null;
                double addtokens = Math.floor(Double.parseDouble(args[1]));

                int tokens = 0;
                try {
                    ps = MySQL.getConnection().prepareStatement("SELECT * FROM `pservertokens` WHERE `playeruuid` = ?");
                    ps.setString(1, String.valueOf(target.getUniqueId()));
                    rs = ps.executeQuery();
                    tokens = rs.getInt("token");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                tokens = (int)(tokens + addtokens);
                tokens = tokens * Integer.getInteger(BTETokenSystem.getPlugin().getConfig().get("multiplicator").toString());


                try {
                    ps = MySQL.getConnection().prepareStatement("UPDATE `pservertokens` SET `token` = ? WHERE playeruuid = ?");
                    ps.setString(1, String.valueOf(tokens));
                    ps.setString(2, String.valueOf(target.getUniqueId()));
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Bukkit.getConsoleSender().sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Du hast " + target.getName() + " " + tokens + " Donator Token gegeben");
                return true;
            }
        } else {
            return false;
        }

    }
}