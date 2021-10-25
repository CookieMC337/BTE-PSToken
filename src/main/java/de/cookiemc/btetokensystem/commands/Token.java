package de.cookiemc.btetokensystem.commands;

import de.cookiemc.btetokensystem.BTETokenSystem;
import de.cookiemc.btetokensystem.database.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Token implements CommandExecutor {
    private BTETokenSystem plugin;
    public Token(BTETokenSystem bteTokenSystem) {
        this.plugin = bteTokenSystem;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)){
            Player p = (Player)sender;
            PreparedStatement ps = null;
            ResultSet rs = null;
            int tokens = 0;
            try {
                ps = MySQL.getConnection().prepareStatement("SELECT * FROM `pservertokens` WHERE `playeruuid` = ?");
                ps.setString(1, String.valueOf(p.getUniqueId()));
                rs = ps.executeQuery();
                tokens = rs.getInt("token");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            p.sendMessage(BTETokenSystem.getPlugin().getConfig().get("prefix").toString() + " " + "Du hast aktuell " + tokens + " Donator Token");
            return true;
        } else {
            return false;
        }
    }
}
