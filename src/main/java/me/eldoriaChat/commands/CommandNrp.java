/* Decompiler 26ms, total 180ms, lines 31 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandNrp implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_NRP);
      } else if (!sender.hasPermission("eldoriachat.nrp")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         Profile profile = Main.getProfileContainer().get(player);
         String nrpFormat = Values.NRP_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", text);
         player.sendMessage(nrpFormat);
         player.getNearbyEntities((double)Values.RADIUS_NRP, (double)Values.RADIUS_NRP, (double)Values.RADIUS_NRP).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(nrpFormat);
         });
      }

   }
}
