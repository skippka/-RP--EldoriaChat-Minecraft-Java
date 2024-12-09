/* Decompiler 27ms, total 434ms, lines 32 */
package me.eldoriaChat.commands;

import java.util.Random;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandCube implements ICommand {
   private static final Random rand = new Random();

   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission("eldoriachat.cube")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         int result = (int)(Math.random() * 6.0D + 1.0D);
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.CUBE_FORMAT.replace("%result", String.valueOf(result)).replace("%displayname", profile.getFullName()).replace("%name", sender.getName());
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_CUBE, (double)Values.RADIUS_CUBE, (double)Values.RADIUS_CUBE).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }
}
