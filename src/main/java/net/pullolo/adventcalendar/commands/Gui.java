package net.pullolo.adventcalendar.commands;

import net.pullolo.adventcalendar.gui.GuiManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gui extends net.pullolo.adventcalendar.commands.Command implements CommandExecutor, TabCompleter {

    private final GuiManager guiManager;
    public Gui(GuiManager guiManager){
        this.guiManager = guiManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!command.getName().equalsIgnoreCase("gui")){
            return false;
        }
        if(!(commandSender instanceof Player)){
            return false;
        }
        if (strings.length<1 || strings.length>2){
            return false;
        }
        Player p = (Player) commandSender;
        switch (strings[0]){
            case "calendar":
                guiManager.createAdventGui(p).show(p);
                return true;
            default:
                p.sendMessage(ChatColor.RED + "This gui was not found!");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!command.getName().equalsIgnoreCase("gui")){
            return null;
        }
        if (strings.length==1){
            ArrayList<String> completion = new ArrayList<>();
            addToCompletion("calendar", strings[0], completion);
            return completion;
        }
        return new ArrayList<>();
    }
}
