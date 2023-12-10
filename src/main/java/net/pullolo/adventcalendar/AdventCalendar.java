package net.pullolo.adventcalendar;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdventCalendar extends JavaPlugin {

    public static DbManager dbManager;
    private static FileConfiguration config;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        savePlayers();
        dbManager.disconnect();
        // Plugin shutdown logic
    }

    public static void checkDb(){
        dbManager.connect();
        if (dbManager.isDbEnabled()){
            log.info("Database is operational");
        } else log.warning("Database is offline!");
    }
    private void setPlayerData() {
        for (Player p : getServer().getOnlinePlayers()){
            PlayerData.setPlayerDataFromDb(p, dbManager);
        }
    }
    private void savePlayers(){
        for (Player p : getServer().getOnlinePlayers()){
            PlayerData.savePlayerDataToDb(p, dbManager);
            PlayerData.removePlayerData(p);
        }
    }
}
