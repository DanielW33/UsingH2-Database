package me.MuchDan.TestH2.Events;

import me.MuchDan.TestH2.H2.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class onBlockBreak implements Listener {
    @EventHandler
    public void PlayerBlockBreak(BlockBreakEvent Event) throws SQLException, ClassNotFoundException {
        long StartTime;
        StartTime = System.currentTimeMillis();
        Player player = Event.getPlayer();
        Connection connection = Database.getConnection();
        UUID uuid = player.getUniqueId();
        PreparedStatement ps;

        ps = connection.prepareStatement("SELECT * FROM BlockLog Where PlayerUUID=?");
        ps.setString(1,uuid.toString());
        ResultSet results = ps.executeQuery();


        if(results.next()) {
            ps = connection.prepareStatement("UPDATE BlockLog " +
                    "SET BlocksBroken = BlocksBroken + ? " +
                    "WHERE PlayerUUID = ?");
            ps.setString(1, "1");
            ps.setString(2, uuid.toString());
        }
        else {
            ps = connection.prepareStatement("INSERT INTO BlockLog(PlayerUUID, BlocksBroken) VALUES(?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, "1");
        }

        ps.executeUpdate();
        ps = connection.prepareStatement("SELECT BlocksBroken FROM BlockLog WHERE PlayerUUID = ?");
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            player.sendMessage("You have now broken " + rs.getInt("BlocksBroken"));
        }
        else{
            player.sendMessage("Unable to retreive data.");
        }

        rs.close();
        connection.close();
        player.sendMessage(ChatColor.GREEN + "These operations took " + (System.currentTimeMillis() - StartTime) + "ms" );
    }
    /*
    public boolean exists(Connection connection, UUID uuid) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BlockLog Where PlayerUUID=?");
        preparedStatement.setString(1,uuid.toString());
        ResultSet results = preparedStatement.executeQuery();

        return results.next();
    }
    public void Update(Connection connection, UUID uuid) throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareStatement("UPDATE BlockLog " +
                "SET BlocksBroken = BlocksBroken + ? " +
                "WHERE PlayerUUID = ?");
        ps.setString(1, "1");
        ps.setString(2, uuid.toString());
        ps.executeUpdate();
    }

    public void InitialSet(Connection connection, UUID uuid) throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareStatement("INSERT INTO BlockLog(PlayerUUID, BlocksBroken) VALUES(?,?)");
        ps.setString(1, uuid.toString());
        ps.setString(2, "1");
        ps.executeUpdate();
    }
    public void getValue(Connection connection, UUID uuid, Player player) throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareStatement("SELECT BlocksBroken FROM BlockLog WHERE PlayerUUID = ?");
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            player.sendMessage("You have now broken " + rs.getInt("BlocksBroken"));
        }
        else{
            player.sendMessage("Unable to retreive data.");
        }

        rs.close();
    } */
}
