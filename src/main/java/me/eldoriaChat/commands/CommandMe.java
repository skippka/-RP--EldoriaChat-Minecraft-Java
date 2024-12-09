/* Decompiler 20ms, total 363ms, lines 31 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandMe implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_ME);
      } else if (!sender.hasPermission("eldoriachat.me")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.ME_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", text);
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_ME, (double)Values.RADIUS_ME, (double)Values.RADIUS_ME).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }
}