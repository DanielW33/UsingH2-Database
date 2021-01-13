package me.MuchDan.TestH2;

import me.MuchDan.TestH2.Events.onBlockBreak;
import me.MuchDan.TestH2.H2.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class TestH2 extends JavaPlugin {
    private static String ConnectionURL;

    @Override
    public void onEnable(){
        ConnectionURL = "jdbc:h2:" + getDataFolder().getAbsolutePath() + "/data/Database";
        try {
            Database.initializeDatabase();
        } catch (SQLException | ClassNotFoundException throwables) {
            this.getLogger().log(Level.SEVERE, "Failed to initalize Databse.");
            System.out.println(throwables);
        }
        this.getServer().getPluginManager().registerEvents(new onBlockBreak(), this);
    }

    @Override
    public void onDisable(){

    }
    public static String getConnectionURL(){
        return ConnectionURL;
    }
}
