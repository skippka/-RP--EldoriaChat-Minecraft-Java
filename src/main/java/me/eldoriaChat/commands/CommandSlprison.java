/* Decompiler 16ms, total 192ms, lines 30 */
package me.eldoriaChat.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.util.Values;

public class CommandSlprison implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.LOCATIONS_HELP);
      } else if (!sender.hasPermission("eldoriachat.overseer") && !sender.hasPermission("eldoriachat.slprison")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         String jailId = args[0];
         Jail jail = (Jail)Main.getJailContainer().get(jailId);
         if (jail == null) {
            sender.sendMessage(Values.NO_JAIL);
         } else {
            Player player = (Player)sender;
            Location location = player.getLocation();
            jail.setOut(location);
            sender.sendMessage(Values.SLPRISON);
         }
      }
   }
}
