/* Decompiler 14ms, total 178ms, lines 31 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandNamereset implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("Only for players");
      } else if (!sender.hasPermission("eldoriachat.namereset")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         Profile profile = Main.getProfileContainer().get(player);
         if (!profile.hasSurname()) {
            sender.sendMessage(Values.NAMERESET_NO_CHANGE);
         } else {
            profile.setSurname((String)null);
            profile.setName(player.getName());
            player.setPlayerListName(player.getName());
            player.setDisplayName(player.getName());
            sender.sendMessage(Values.NAMERESET_USE);
         }

      }
   }
}
