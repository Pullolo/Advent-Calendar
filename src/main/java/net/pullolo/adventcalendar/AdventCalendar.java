package net.pullolo.adventcalendar;

import net.pullolo.adventcalendar.commands.Gui;
import net.pullolo.adventcalendar.data.DbManager;
import net.pullolo.adventcalendar.data.PlayerData;
import net.pullolo.adventcalendar.events.DataEventsHandler;
import net.pullolo.adventcalendar.gui.GuiManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static net.pullolo.adventcalendar.data.PlayerData.getPlayerData;

public final class AdventCalendar extends JavaPlugin {

    public static DbManager dbManager;
    private static FileConfiguration config;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = getConfig();
        saveDefaultConfig();
        dbManager = new DbManager(this);
        dbManager.init();
        checkDb();
        setPlayerData();
        final GuiManager gui = new GuiManager(this);
        Gui guiCmd = new Gui(gui);
        getServer().getPluginManager().registerEvents(new DataEventsHandler(dbManager, gui, this), this);
        getCommand("gui").setExecutor(guiCmd);
        getCommand("gui").setTabCompleter(guiCmd);
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
