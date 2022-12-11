package me.blueb442.hyacinthhello;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class CommandLeavemsg implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        String sentence1 = String.join(" ", args);
        String sentence2 = sentence1.replaceAll("\\[", " ");
        String sentence = sentence2.replaceAll("\\]", " ");

        UUID joinerUUID = p.getUniqueId();


        String pfx = getServer().getPluginManager().getPlugin("HyacinthHello").getConfig().getString("prefix");
        p.sendMessage(pfx + " Set your leave message to §7" + sentence + "§f.");
        File userdata = new File(Objects.requireNonNull(getServer().getPluginManager().getPlugin("HyacinthHello")).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (p.hasPermission("hyacinthhello.leavemessage")) {
            if (sentence.length() <= getServer().getPluginManager().getPlugin("HyacinthHello").getConfig().getInt("maximum-message-length")) {
                try {
                    playerData.createSection("leave");
                    playerData.set("leave.msg", sentence);
                    playerData.save(f);
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            } else if (sentence.length() > getServer().getPluginManager().getPlugin("HyacinthHello").getConfig().getInt("maximum-message-length")) {
                p.sendMessage(pfx + " That's too long! Your message has to be at most " + getServer().getPluginManager().getPlugin("HyacinthHello").getConfig().getInt("maximum-message-length") + " characters!");
            }
        }

        return true;
    }
}
