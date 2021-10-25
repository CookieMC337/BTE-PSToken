package de.cookiemc.btetokensystem.commands;

import de.cookiemc.btetokensystem.BTETokenSystem;
import de.cookiemc.btetokensystem.database.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddToken implements CommandExecutor {
    private BTETokenSystem plugin;
    public AddToken(BTETokenSystem bteTokenSystem) {
        this.plugin = bteTokenSystem;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (p.hasPermission("btetoken.add")) {

                if (args[0] == null || args[1] == null){
                    p.sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Bitte benutzte /addtoken {Username} {Tokens}");
                    return true;
                }else {

                Player target = Bukkit.getPlayer(args[0]);
                PreparedStatement ps = null;
                ResultSet rs = null;
                int addtokens = Integer.parseInt(args[1]);

                int tokens = 0;
                try {
                    ps = MySQL.getConnection().prepareStatement("SELECT * FROM `pservertokens` WHERE `playeruuid` = ?");
                    ps.setString(1, String.valueOf(p.getUniqueId()));
                    rs = ps.executeQuery();
                    tokens = rs.getInt("token");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                tokens = (int)(tokens + addtokens);

                try {
                    ps = MySQL.getConnection().prepareStatement("UPDATE `pservertokens` SET `token` = ? WHERE playeruuid = ?");
                    ps.setString(1, String.valueOf(tokens));
                    ps.setString(2, String.valueOf(target.getUniqueId()));
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                p.sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Du hast " + target.getName() + " " + tokens + " Donator Token gegeben");
                target.sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Du hast von " + p.getName() + " " + tokens + " Donator Token erhalten");
                return true;
                }
            } else {
                p.sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Du darfst diesen Befehl NICHT auführern!");
                return false;
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Dieser befehl kann nur von einem Spieler ausgeführt werden!");
            return false;
        }
    }
}