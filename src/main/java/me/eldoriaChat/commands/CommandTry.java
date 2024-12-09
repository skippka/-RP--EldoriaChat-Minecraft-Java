/* Decompiler 13ms, total 414ms, lines 32 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandTry implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_TRY);
      } else if (!sender.hasPermission("eldoriachat.try")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         String text = this.joinArgs(args, 0);
         String action = Math.round(Math.random()) == 0L ? "§aУспешно§r" : "§cНеудачно§r";
         Profile profile = Main.getProfileContainer().get(player);
         String tryFormat = Values.TRY_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", text).replace("%action", action);
         player.sendMessage(tryFormat);
         player.getNearbyEntities((double)Values.RADIUS_TRY, (double)Values.RADIUS_TRY, (double)Values.RADIUS_TRY).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(tryFormat);
         });
      }

   }
}
