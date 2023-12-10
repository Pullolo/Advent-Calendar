package net.pullolo.adventcalendar.data;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    private Connection conn;
    private final JavaPlugin plugin;

    public DbManager(JavaPlugin plugin){
        this.plugin=plugin;
    }

    public void init(){
        File file = new File(plugin.getDataFolder(), "data.db");
        if (!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource("data.db", false);
        }
    }

    public boolean connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:plugins/"+plugin.getDataFolder().getName()+"/data.db");
            Statement stmt = conn.createStatement();
            String sql = "create table if not exists calendar_data (name TEXT NOT NULL, day INT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isDbEnabled(){
        return conn!=null;
    }

    public void disconnect(){
        try {
            conn.close();
            conn=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(String playerName) {
        ArrayList<Integer> collected = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:plugins/"+plugin.getDataFolder().getName()+"/data.db");
            PreparedStatement stmt = conn.prepareStatement("select * from calendar_data where name=?");
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                collected.add(rs.getInt("day"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PlayerData(playerName, collected);
    }

    public void insertData(String playerName, ArrayList<Integer> collected) {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:plugins/"+plugin.getDataFolder().getName()+"/data.db");
            for (int i : collected){
                PreparedStatement stmt = conn.prepareStatement("insert into calendar_data values (?, ?)");
                stmt.setString(1, playerName);
                stmt.setInt(2, i);
                stmt.execute();
                stmt.close();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
