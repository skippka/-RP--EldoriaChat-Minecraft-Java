/* Decompiler 29ms, total 180ms, lines 30 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandSh implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_SH);
      } else if (!sender.hasPermission("eldoriachat.sh")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.SH_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", this.joinArgs(args, 0));
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_SH, (double)Values.RADIUS_SH, (double)Values.RADIUS_SH).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }
}
