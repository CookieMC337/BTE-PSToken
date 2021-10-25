package de.cookiemc.btetokensystem.database;

import de.cookiemc.btetokensystem.BTETokenSystem;
import org.bukkit.Bukkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static Connection con;

    // connect
    public static void connect() {
        if (!isConnected()) {
            try {
                System.out.println(BTETokenSystem.getPlugin());

                con = DriverManager.getConnection("jdbc:mysql://" + BTETokenSystem.getPlugin().getConfig().get("host").toString() + ":" + BTETokenSystem.getPlugin().getConfig().get("port").toString() + "/" + BTETokenSystem.getPlugin().getConfig().get("username").toString(), BTETokenSystem.getPlugin().getConfig().get("username").toString(), BTETokenSystem.getPlugin().getConfig().get("password").toString());
                Bukkit.getConsoleSender().sendMessage("§eBTE-PS-Token:§r Datenbank verbunden");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("§eBTE-PS-Token:§r Datenbank getrennt");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }
}