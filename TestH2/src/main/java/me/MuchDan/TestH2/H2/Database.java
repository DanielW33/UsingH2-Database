package me.MuchDan.TestH2.H2;

import me.MuchDan.TestH2.TestH2;

import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName ("org.h2.Driver");
        return DriverManager.getConnection(TestH2.getConnectionURL());
    }

    public static void initializeDatabase() throws SQLException, ClassNotFoundException {
        PreparedStatement ps;                                           //PlayerUUID is the intended key, BlocksBroken is the number of blocks broken
        System.out.println("Before getConnection is called");
        Connection connection = getConnection();
        System.out.println("Made it past getConnection");
        ps = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS BlockLog(" +
                        "PlayerUUID varchar(100)," +
                        " BlocksBroken long," +
                        " PRIMARY KEY(PlayerUUID));");
        ps.execute();
        connection.close();
    }

}
