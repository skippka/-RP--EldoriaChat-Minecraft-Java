/* Decompiler 9ms, total 348ms, lines 31 */
package me.eldoriaChat.commands;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandGme implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_GME);
      } else if (!sender.hasPermission("eldoriachat.gme")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.GME_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", this.joinArgs(args, 0));
         Iterator var6 = Bukkit.getOnlinePlayers().iterator();

         while(var6.hasNext()) {
            Player onlinePlayer = (Player)var6.next();
            onlinePlayer.sendMessage(message);
         }
      }

   }
}
