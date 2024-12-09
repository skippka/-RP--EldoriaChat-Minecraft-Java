/* Decompiler 19ms, total 174ms, lines 33 */
package me.eldoriaChat.commands;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandGtry implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_GTRY);
      } else if (!sender.hasPermission("eldoriachat.gtry")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         String action = Math.round(Math.random()) == 0L ? "§aУспешно§r" : "§cНеудачно§r";
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.GTRY_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", text).replace("%action", action);
         Iterator var8 = Bukkit.getOnlinePlayers().iterator();

         while(var8.hasNext()) {
            Player onlinePlayer = (Player)var8.next();
            onlinePlayer.sendMessage(message);
         }
      }

   }
}
