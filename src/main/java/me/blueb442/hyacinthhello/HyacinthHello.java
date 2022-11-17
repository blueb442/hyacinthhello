package me.blueb442.hyacinthhello;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class HyacinthHello extends JavaPlugin implements Listener, CommandExecutor {
    FileConfiguration config = this.getConfig();

    public HyacinthHello() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("joinmsg").setExecutor(new CommandJoinmsg());
        this.getCommand("leavemsg").setExecutor(new CommandLeavemsg());
        this.getCommand("deathmsg").setExecutor(new CommandDeathmsg());
        this.config.addDefault("enabled", true);
        this.config.addDefault("prefix", "§dHyacinthHello §8»§r");
        this.config.addDefault("hello-wrapper-left", "§6»§e ");
        this.config.addDefault("hello-wrapper-right", " §6«");
        this.config.addDefault("maximum-message-length", 60);
        this.config.addDefault("enable-points", true);
        this.config.addDefault("point-cost-join", 10);
        this.config.addDefault("point-cost-leave", 10);
        this.config.addDefault("point-cost-death", 15);
        this.config.options().copyDefaults(true);
        this.saveConfig();
        if (!this.config.getBoolean("enabled")) {
            Bukkit.getLogger().info("Disabled by config.yml! Set enabled to true in config.yml to enable HyacinthHello.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable() {
        Bukkit.getLogger().info("Saving data and shutting down. Cya, lmao!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("join");
                playerData.set("join.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("join.msg");
        if (Objects.equals(playerData.getString("join.msg"), "")) {
            Bukkit.getLogger().info("No hello found for " + joinerUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("join.msg"), (Object)null)) {
            Bukkit.getLogger().info("No hello found for " + joinerUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.joinmessage")) {
                    Bukkit.broadcastMessage(hwl + message + hwr);
                    Bukkit.getLogger().info("Death sent for " + joinerUUID + " (" + p.getName() + ")");
                }
            }, 2L);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        UUID quitterUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + quitterUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("leave");
                playerData.set("leave.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + quitterUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("leave.msg");
        if (Objects.equals(playerData.getString("leave.msg"), "")) {
            Bukkit.getLogger().info("No goodbye found for " + quitterUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object)null)) {
            Bukkit.getLogger().info("No goodbye found for " + quitterUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.leavemessage")) {
                    Bukkit.broadcastMessage(hwl + message + hwr);
                    Bukkit.getLogger().info("Goodbye sent for " + quitterUUID + " (" + p.getName() + ")");
                }
            }, 2L);
        }

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player p = event.getEntity().getPlayer();
        UUID deadUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + deadUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("death");
                playerData.set("death.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + deadUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("death.msg");
        if (Objects.equals(playerData.getString("death.msg"), "")) {
            Bukkit.getLogger().info("No death found for " + deadUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("death.msg"), (Object)null)) {
            Bukkit.getLogger().info("No death found for " + deadUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.deathmessage")) {
                    Bukkit.broadcastMessage(hwl + message + hwr);
                    Bukkit.getLogger().info("Death sent for " + deadUUID + " (" + p.getName() + ")");
                }
            }, 2L);
        }

    }
}
