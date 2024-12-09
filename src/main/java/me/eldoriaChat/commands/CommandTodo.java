/* Decompiler 21ms, total 382ms, lines 36 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandTodo implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      String text = this.joinArgs(args, 0);
      if (args.length != 0 && text.contains("*") && !text.endsWith("*")) {
         if (!sender.hasPermission("eldoriachat.todo")) {
            sender.sendMessage(Values.PERMISSION);
         } else {
            Player player = (Player)sender;
            String name = sender.getName();
            String prefix = text.substring(0, text.indexOf("*"));
            String suffix = text.substring(text.indexOf("*") + 1);
            Profile profile = Main.getProfileContainer().get(player);
            String message = Values.TODO_FORMAT.replace("%prefix", prefix.trim()).replace("%suffix", suffix.trim()).replace("%name", name).replace("%displayname", profile.getFullName());
            player.sendMessage(message);
            player.getNearbyEntities((double)Values.RADIUS_TODO, (double)Values.RADIUS_TODO, (double)Values.RADIUS_TODO).stream().filter((ent) -> {
               return ent.getType() == EntityType.PLAYER;
            }).forEach((p) -> {
               p.sendMessage(message);
            });
         }
      } else {
         sender.sendMessage(Values.INFO_TODO);
      }

   }
}
