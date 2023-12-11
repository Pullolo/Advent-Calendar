package net.pullolo.adventcalendar.gui;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import dev.dbassett.skullcreator.SkullCreator;
import net.pullolo.adventcalendar.dates.DateUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static net.pullolo.adventcalendar.data.PlayerData.getPlayerData;

public class GuiManager {
    private final ArrayList<String> presents = new ArrayList<>();
    private final ArrayList<String> openedpresents = new ArrayList<>();
    private final JavaPlugin plugin;

    public GuiManager(JavaPlugin plugin){
        this.plugin=plugin;

        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzZTBmNzNkOGY0NDdhNTczZjMzMjI2ZmU0Zjk2ODNiNjRkZGE0MmU3MTQyYzEzMGI1YjMzYzI5ZjE2MDE4MyJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTllMzlkOTFjMDRjMzBjYzFmNTMwZmVlNzk4ZWVkMjc5ZGRlNjBmOTVjMmUxZDE1NWMwZmRkMzYxZDA5NjJlZCJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRjM2Y1OTVhZTJhZDVkN2U5MzFlNDcxOWNmNjQwNTgzMjY5OGI5ZTQ0M2FkZWY4ODc1ZDI0ZmJlZGE3YmZjNCJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIyZjAzNTdjODc0NWI4YWI0ZDZlZGY3OTc0MGQ5MmMyZTJlY2U4YTUzOWFlOWRkNjU5MmRkOGU5NmUyZTM5NCJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQxYzRmMDEwZmNlN2EwZTY2MDgzZmFjZTc4Yzk0NGM0ZjAxZDZiOTU2YTU0ZGFlZjdkZjUwNzNjOWU5NDZiZiJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzEyYjZkNWYwNzFiOWEzYzI3ZmY0ZmJmODFhZjBiOGI0NjE4ZTA4OGZiZTVmOWQ5ZDY4ZmEwY2E5MzhkMWY4ZCJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE3N2MwZDdmZjFiOGFlM2MwNTljN2UyMzA3NzMwODA1Y2IzMTg4NjI2MjUxYjhjYjYyZDU2NmM1MTIzMjM2ZiJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTllMzlkOTFjMDRjMzBjYzFmNTMwZmVlNzk4ZWVkMjc5ZGRlNjBmOTVjMmUxZDE1NWMwZmRkMzYxZDA5NjJlZCJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmFmYmE5YzUxZmU4NmI4NTNkN2QzY2M3ODkxOWRhMjkxNzliNWI1YWY1NzMzZjhiY2E2OWQwOTNhZjFiNjE0MSJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3MTJiMTk3MWM1ZjQyZWVmZjgwNTUxMTc5MjIwYzA4YjgyMTNlYWNiZTZiYzE5ZDIzOGMxM2Y4NmUyYzAifX19");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwYmFkMWFjNDIwZGVhN2E3ZjBlYzE1ZTdiNDc3ZTc1ZDM2OTU0ZDQ5M2EzMjg4OTVhNmM1YjIyZGQxZmUzNyJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBlNDZkYzA3MmNiNDkwODA2Y2E4YWZhZWQ4MWIxMTdmNzgyZjJhM2I3ZTY4ZTgwOGU0OWNiNmQ3ZGMwZjZlOSJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVjNjk0NDU5MzgyMGQxM2Q3ZDQ3ZGIyYWJjZmNiZjY4M2JiNzRhMDdlMWE5ODJkYjlmMzJlMGE4YjVkYzQ2NiJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNlZDg3OTlkMDdiOGUyZGEyNTU3YzNmMDU5OGZkYWVkOTQ0Mzc2ODI2ZjFkMmNlYjY3MGZkNjUxYjJjZDE2NiJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTcyNmQ5ZDA2MzJlNDBiZGE1YmNmNjU4MzliYTJjYzk4YTg3YmQ2MTljNTNhZGYwMDMxMGQ2ZmM3MWYwNDJiNSJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0Y2RlMTZhNDAxNGRlMGE3NjUxZjYwNjdmMTI2OTViYjVmZWQ2ZmVhZWMxZTk0MTNjYTQyNzFlN2M4MTkifX19");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYyZDE4OTVmZmY0YjFiYjkxMTZjOGE5ZTIyOTU5N2Y2OWYzZWVlODgxMjI3NzZlNWY5NzMzNTdlNmIifX19");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY1ZjA4OGFiYmVmYzI0NGUzNTkyNGM4MDAyM2MyNTA1NWFmMTAyNTc2NTZiYWM0ZTg0ZTc0ODU1ZWMxMmYyYiJ9fX0=");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRhYzlhNTE4MTNhYmNiNjY2ZWUyYzJjYTg0Y2VmY2ZiMjFlNThiN2IwMWFlMGVhN2U5OWQzNWYzMjNhIn19fQ==");
        presents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk5NGE5MmY5ZGRmMjdlMzNiNDM3YjkxMWFjMWU2MmZlN2FmNTYyMzVhYzE1MjkzNGJmNjFjMGYwZmEwNjQyMiJ9fX0=");

        openedpresents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=");
        openedpresents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIyYTFjNjYyNjc0MGExMTkxZDA1MjA5NWE4ZTZhZWI1NmY5OGIzODZkMmZmZTg4N2NmOTMzNmU2YjE5YzljYiJ9fX0=");
        openedpresents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk0MjViZDM1ZmQyMzRlMTI0NjEyN2Y0YmExNGMzYWY0ZGQ0Y2E4MDY1ZThiOWEzNDE0NjllM2U0MTg0OTQwYyJ9fX0=");
        openedpresents.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmZlNzMyYjNlY2IyZmFiYzAzOGZiMDZkYjhjNTNhN2ZmYjAzMGRiOTI1NDRlMWIyMjU2ZjAxY2IyZWI4MjJiNyJ9fX0=");
    }

    public InventoryGui createAdventGui(Player p){
        final Random random = new Random(stringToSeed(p.getDisplayName()));
        String[] guiSetup = {
                "    h    ",
                " ppppppp ",
                " ppppppp ",
                " ppppppp ",
                " pppp    ",
                "         "
        };
        InventoryGui gui = new InventoryGui(plugin, p, "Advent Calendar", guiSetup);
        gui.setCloseAction(close -> {
            if (DateUtils.canOpenCalendar(p)){
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        close.getGui().show(close.getPlayer());
                    }
                }.runTaskLater(plugin, 1);
            }
            return false;
        });
        gui.setTitle("Advent Calendar");
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        gui.addElement(new DynamicGuiElement('h', (viewer)->{
            return new StaticGuiElement('h', getPlayerSkull(p),
                    ChatColor.translateAlternateColorCodes('&', "&fName: &a" + p.getName() + "\n&fDay: &c" + DateUtils.getDaysInDecember()));
        }));
        GuiElementGroup group = new GuiElementGroup('p');

        for (int i = 1; i<=25 ; i++) {
            final int j = i;
            boolean opened = getPlayerData(p).getCollected().contains(i);
            if (i>DateUtils.getDaysInDecember()){
                group.addElement(new DynamicGuiElement('p', (viewer)->{
                    return new StaticGuiElement('p', new ItemStack(Material.COAL_BLOCK),
                            ChatColor.translateAlternateColorCodes('&', "&cYou can't open this present yet!"));
                }));
            } else {
                if (opened) {
                    group.addElement(new DynamicGuiElement('p', (viewer)->{
                        return new StaticGuiElement('p', getRandomOpenedPresent(random),
                                ChatColor.translateAlternateColorCodes('&', "&fDay &c" + j + " &e(Claimed)"));
                    }));
                } else {
                    group.addElement((new DynamicGuiElement('p',(viewer)->{
                        return new StaticGuiElement('p', getRandomPresent(random), click -> {
                            Player player = (Player) click.getWhoClicked();
                            getPlayerData(player).addCollectedDay(j);
                            click.getGui().close();
                            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2f);
                            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation().clone().add(0, 1, 0), 80, 1, 1, 1, 0.1);
                            getLoot(player, j);
                            if (DateUtils.canOpenCalendar(player)){
                                createAdventGui(player).show(player);
                            }
                            return true;
                        }, ChatColor.translateAlternateColorCodes('&', "&fDay &c" + j));
                    }
                    )));
                }
            }
        }
        gui.addElement(group);
        return gui;
    }

    private void getLoot(Player p, int day) {
        ArrayList<ItemStack> items = new ArrayList<>();
        double mul = 1;
        Random r = new Random();
        int reps = r.nextInt(2)+2;
        for (int i = 0; i < reps; i++){
            switch (r.nextInt(11)+1){
                case 1:
                    items.add(new ItemStack(Material.DIAMOND, (int) (2 * mul)));
                    break;
                case 2:
                    items.add(new ItemStack(Material.GOLD_INGOT, (int) (4 * mul)));
                    break;
                case 3:
                    items.add(new ItemStack(Material.GOLDEN_APPLE, (int) (3 * mul)));
                    break;
                case 4:
                    items.add(new ItemStack(Material.ANCIENT_DEBRIS, (int) (3 * mul)));
                    break;
                case 5:
                    items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
                    break;
                case 6:
                    items.add(new ItemStack(Material.EMERALD, (int) (4 * mul)));
                    break;
                case 7:
                    items.add(new ItemStack(Material.PUMPKIN_PIE, (int) (2 * mul)));
                    break;
                case 8:
                    items.add(new ItemStack(Material.TNT, (int) (3 * mul)));
                    break;
                case 9:
                    ItemStack men = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta menMeta = men.getItemMeta();
                    menMeta.addEnchant(Enchantment.MENDING, 1, false);
                    men.setItemMeta(menMeta);
                    items.add(men);
                    break;
                case 10:
                    items.add(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                    break;
                default:
                    items.add(new ItemStack(Material.COAL, (int) (10 * mul)));
                    break;
            }
        }

        if (day==24){
            items.add(new ItemStack(Material.DIAMOND_BLOCK, 2));

            ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
            bookMeta.setTitle("Christmas wishes!");
            bookMeta.setAuthor("Santa Claus");
            List<String> pages = new ArrayList<>();
            pages.add("Ho Ho Ho, Merry Christmas " + p.getDisplayName() + ", Santa Claus is taking a moment away from the hustle and bustle of the North Pole to send you warm and jolly wishes. I hope this letter finds you filled with joy and excitement as we approach the most magical time of the year.");
            pages.add("I've been checking my list, and you have been a wonderful person throughout the year. Your kindness, good deeds, and the love you've shared with others have not gone unnoticed. The elves and I are truly impressed!");
            pages.add("As the snow blankets the world in a soft, sparkling white, may your heart be filled with the warmth of the holiday spirit. May you find joy in the company of loved ones, and may laughter and happiness echo through your home.");
            pages.add("This Christmas, I hope you unwrap the gift of love, experience the magic of giving, and create precious memories that will last a lifetime. Remember, it's not just about the presents under the tree but the presence of those who care about you.");
            pages.add("Wishing you a Christmas filled with wonder, laughter, and the joy of believing in the magic of the season. Keep spreading kindness and love, not only during the holidays but every day. Merry Christmas, "+p.getDisplayName()+"!");
            pages.add("I'll be watching for your cookies and milk on Christmas Eve! With festive cheer,\n\n\nSanta Claus");
            bookMeta.setPages(pages);
            writtenBook.setItemMeta(bookMeta);
            items.add(writtenBook);
        }

        for (ItemStack item : items){
            if (Arrays.asList(p.getInventory().getStorageContents()).contains(null)){
                p.getInventory().addItem(item);
            }else {
                p.getWorld().dropItem(p.getLocation(), item);
            }
        }
    }

    private long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L*hash + c;
        }
        return hash;
    }

    public ItemStack getRandomPresent(Random r){
        return SkullCreator.itemFromBase64(presents.get(r.nextInt(presents.size())));
    }

    public ItemStack getRandomOpenedPresent(Random r){
        return SkullCreator.itemFromBase64(openedpresents.get(r.nextInt(openedpresents.size())));
    }


    private ItemStack getPlayerSkull(Player p){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwningPlayer(p);
        skull.setItemMeta(sm);
        return skull;
    }
}
