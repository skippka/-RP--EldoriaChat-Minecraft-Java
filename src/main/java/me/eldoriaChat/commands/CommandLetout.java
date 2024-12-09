/* Decompiler 19ms, total 383ms, lines 40 */
package me.eldoriaChat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandLetout implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_LETOUT);
      } else if (!sender.hasPermission("eldoriachat.overseer") && !sender.hasPermission("eldoriachat.letout")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = Bukkit.getPlayer(args[0]);
         if (player == null) {
            sender.sendMessage(Values.OFFLINE);
         } else {
            Profile profile = Main.getProfileContainer().get(player);
            Jail jail = profile.getJail();
            if (jail == null) {
               sender.sendMessage(Values.NOT_IN_JAIL);
            } else {
               jail.removePrisoner(player.getName());
               profile.setJail((Jail)null);
               sender.sendMessage(Values.SEND_LETOUT_POLICEMAN);
               player.sendMessage(Values.SEND_LETOUT_CRIMINAL);
               if (jail.getOut() != null) {
                  player.teleport(jail.getOut());
               }

            }
         }
      }
   }
}
