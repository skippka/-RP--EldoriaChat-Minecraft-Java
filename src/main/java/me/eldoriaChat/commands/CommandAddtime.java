/* Decompiler 22ms, total 186ms, lines 45 */
package me.eldoriaChat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandAddtime implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length < 2) {
         sender.sendMessage(Values.INFO_ADDTIME);
      } else if (!sender.hasPermission("eldoriachat.overseer") && !sender.hasPermission("eldoriachat.addtime")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player target = Bukkit.getPlayer(args[0]);
         if (target == null) {
            sender.sendMessage(Values.OFFLINE);
         } else {
            Profile profile = Main.getProfileContainer().get(target);
            Jail jail = profile.getJail();
            if (jail == null) {
               sender.sendMessage(Values.NOT_IN_JAIL);
            } else {
               int time;
               try {
                  time = Integer.parseInt(args[2]);
               } catch (NumberFormatException var10) {
                  sender.sendMessage(Values.NUMBER_EXCEPTION);
                  return;
               }

               String targetName = target.getName();
               long current = jail.getTime(targetName);
               jail.setTime(targetName, current + (long)time * 60L);
               sender.sendMessage(Values.SEND_ADDTIME_POLICEMAN.replace("%name", targetName).replace("%displayname", profile.getFullName()).replace("%time", time + " мин."));
               target.sendMessage(Values.SEND_ADDTIME_CRIMINAL.replace("%name", targetName).replace("%displayname", profile.getFullName()).replace("%time", time + " мин."));
            }
         }
      }
   }
}
