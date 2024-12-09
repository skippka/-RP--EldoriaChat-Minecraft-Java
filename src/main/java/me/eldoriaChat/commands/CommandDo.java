/* Decompiler 27ms, total 392ms, lines 31 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandDo implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_DO);
      } else if (!sender.hasPermission("eldoriachat.do")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         Profile profile = Main.getProfileContainer().get(player);
         String doFormat = Values.DO_FORMAT.replace("%name", player.getName()).replace("%displayname", profile.getFullName()).replace("%text", text);
         player.sendMessage(doFormat);
         player.getNearbyEntities((double)Values.RADIUS_DO, (double)Values.RADIUS_DO, (double)Values.RADIUS_DO).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(doFormat);
         });
      }

   }
}
