/* Decompiler 9ms, total 194ms, lines 29 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandCoin implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission("eldoriachat.coin")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         boolean type = Math.random() * 100.0D >= 50.0D;
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.COIN_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%result", type ? "орёл" : "решка");
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_COIN, (double)Values.RADIUS_COIN, (double)Values.RADIUS_COIN).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }
}
