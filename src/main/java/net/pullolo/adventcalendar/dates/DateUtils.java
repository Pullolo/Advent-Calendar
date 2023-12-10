package net.pullolo.adventcalendar.dates;

import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

import static net.pullolo.adventcalendar.data.PlayerData.getPlayerData;

public class DateUtils {
    public static int getDaysInDecember(){
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH)!=Calendar.DECEMBER){
            return 0;
        }
        else return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isDecember(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH)==Calendar.DECEMBER;
    }

    public static boolean canOpenCalendar(Player p){
        if (!isDecember()) return false;
        for (int i = 1; i<=getDaysInDecember(); i++){
            if (!getPlayerData(p).getCollected().contains(i)){
                return true;
            }
        }

        return false;
    }
}
