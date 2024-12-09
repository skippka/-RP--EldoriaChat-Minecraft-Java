/* Decompiler 55ms, total 216ms, lines 43 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandWh implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_WH);
      } else if (!sender.hasPermission("eldoriachat.wh")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player player = (Player)sender;
         Profile profile = Main.getProfileContainer().get(player);
         String message = Values.WH_FORMAT.replace("%name", sender.getName()).replace("%displayname", profile.getFullName()).replace("%text", this.getText(args));
         player.sendMessage(message);
         player.getNearbyEntities((double)Values.RADIUS_WH, (double)Values.RADIUS_WH, (double)Values.RADIUS_WH).stream().filter((ent) -> {
            return ent.getType() == EntityType.PLAYER;
         }).forEach((p) -> {
            p.sendMessage(message);
         });
      }

   }

   private String getText(String[] args) {
      StringBuilder sb = new StringBuilder();
      String[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String word = var3[var5];
         sb.append(" ").append(word);
      }

      return sb.substring(1);
   }
}
