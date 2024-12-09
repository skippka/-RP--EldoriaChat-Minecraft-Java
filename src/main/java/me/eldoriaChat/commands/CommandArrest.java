/* Decompiler 38ms, total 204ms, lines 58 */
package me.eldoriaChat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandArrest implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length < 4) {
         sender.sendMessage(Values.INFO_ARREST);
      } else if (!sender.hasPermission("eldoriachat.overseer") && !sender.hasPermission("eldoriachat.arrest")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         Player target = Bukkit.getPlayer(args[0]);
         if (target == null) {
            sender.sendMessage(Values.OFFLINE);
         } else {
            Profile profile = Main.getProfileContainer().get(target);
            if (profile.getJail() != null) {
               sender.sendMessage(Values.ALREADY_ARREST);
            } else if (player.getWorld() == target.getWorld() && player.getLocation().distanceSquared(target.getLocation()) <= 25.0D) {
               Jail jail = (Jail)Main.getJailContainer().get(args[1]);
               if (jail == null) {
                  sender.sendMessage(Values.NO_JAIL);
               } else {
                  String reason = this.joinArgs(args, 3);

                  int time;
                  try {
                     time = Integer.parseInt(args[2]);
                  } catch (NumberFormatException var10) {
                     sender.sendMessage(Values.NUMBER_EXCEPTION);
                     return;
                  }

                  if (jail.getEnter() != null) {
                     target.teleport(jail.getEnter());
                  }

                  String targetName = target.getName();
                  jail.addPrisoner(targetName, (long)time * 60L);
                  profile.setJail(jail);
                  sender.sendMessage(Values.SEND_ARREST_POLICEMAN.replace("%name", targetName).replace("%displayname", profile.getFullName()).replace("%reason", reason).replace("%time", time + " мин."));
                  target.sendMessage(Values.SEND_ARREST_CRIMINAL.replace("%name", targetName).replace("%displayname", profile.getFullName()).replace("%reason", reason).replace("%time", time + " мин."));
               }
            } else {
               sender.sendMessage(Values.HIGH_DISTANCE);
            }
         }
      }
   }
}
