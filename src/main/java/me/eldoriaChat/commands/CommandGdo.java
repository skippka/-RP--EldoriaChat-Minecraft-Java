/* Decompiler 19ms, total 181ms, lines 32 */
package me.eldoriaChat.commands;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandGdo implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_GDO);
      } else if (!sender.hasPermission("eldoriachat.gdo")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.GDO_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", text);
         Iterator var7 = Bukkit.getOnlinePlayers().iterator();

         while(var7.hasNext()) {
            Player onlinePlayer = (Player)var7.next();
            onlinePlayer.sendMessage(message);
         }
      }

   }
}
