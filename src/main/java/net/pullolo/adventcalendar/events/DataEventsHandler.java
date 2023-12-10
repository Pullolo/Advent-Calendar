package net.pullolo.adventcalendar.events;

import net.pullolo.adventcalendar.data.DbManager;
import net.pullolo.adventcalendar.dates.DateUtils;
import net.pullolo.adventcalendar.gui.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.pullolo.adventcalendar.data.PlayerData.*;

public class DataEventsHandler implements Listener {

    private final DbManager dbManager;
    private final GuiManager gui;
    private final JavaPlugin plugin;

    public DataEventsHandler(DbManager dbManager, GuiManager guiManager, JavaPlugin plugin) {
        this.dbManager = dbManager;
        gui=guiManager;
        this.plugin=plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        setPlayerDataFromDb(event.getPlayer(), dbManager);
        if (DateUtils.canOpenCalendar(event.getPlayer())){
            new BukkitRunnable() {
                @Override
                public void run() {
                    gui.createAdventGui(event.getPlayer()).show(event.getPlayer());
                }
            }.runTaskLater(plugin, 1);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        savePlayerDataToDb(event.getPlayer(), dbManager);
        removePlayerData(event.getPlayer());
    }
}
