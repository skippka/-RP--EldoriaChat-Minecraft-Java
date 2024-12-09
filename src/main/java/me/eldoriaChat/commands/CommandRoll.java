/* Decompiler 7ms, total 346ms, lines 29 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandRoll implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission("eldoriachat.roll")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         int result = (int)(Math.random() * 100.0D);
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.ROLL_FORMAT.replace("%result", String.valueOf(result)).replace("%displayname", profile.getFullName()).replace("%name", sender.getName());
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_ROLL, (double)Values.RADIUS_ROLL, (double)Values.RADIUS_ROLL).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }
}
