package net.pullolo.adventcalendar.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData {
    private static final HashMap<Player, PlayerData> playerData = new HashMap<>();
    private final String name;
    private final ArrayList<Integer> collected;
    private final ArrayList<Integer> newCollected = new ArrayList<>();

    public PlayerData(String name, ArrayList<Integer> collected) {
        this.name=name;
        this.collected=collected;
    }

    public static PlayerData getPlayerData(Player p){
        return playerData.get(p);
    }

    public static void setPlayerDataFromDb(Player p, DbManager db){
        String playerName = p.getName();
        playerData.put(p, db.getPlayerData(playerName));
    }

    public static void savePlayerDataToDb(Player p, DbManager db){
        String playerName = p.getName();
        db.insertData(playerName, playerData.get(p).getNewCollected());
    }

    public static void removePlayerData(Player p) {
        playerData.remove(p);
    }

    public void addCollectedDay(int day){
        if (newCollected.contains(day)) throw new IllegalArgumentException("Player has already collected this present!");
        newCollected.add(day);
        collected.add(day);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getCollected() {
        return collected;
    }
    public ArrayList<Integer> getNewCollected() {
        return newCollected;
    }
}
