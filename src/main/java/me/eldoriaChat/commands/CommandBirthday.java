/* Decompiler 14ms, total 350ms, lines 52 */
package me.eldoriaChat.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandBirthday implements ICommand {
   private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

   public void execute(CommandSender sender, String[] args) {
      if (!sender.hasPermission("eldoriachat.birthday")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player target;
         if (args.length == 0) {
            target = (Player)sender;
         } else {
            if (!sender.hasPermission("eldoriachat.birthday.other")) {
               sender.sendMessage(Values.PERMISSION);
               return;
            }

            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
               sender.sendMessage(Values.OFFLINE);
               return;
            }
         }

         Profile profile = Main.getProfileContainer().get(target);
         Date birthday = profile.getBirthday();
         if (birthday == null) {
            sender.sendMessage(Values.BIRTHDAY_NO_DATE);
            return;
         }

         Date now = Calendar.getInstance().getTime();
         long millis = now.getTime() - birthday.getTime();
         long years = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 365L;
         sender.sendMessage(!profile.hasBirthday() ? Values.BIRTHDAY_NO_DATE : Values.BIRTHDAY_USE.replace("%birthday", this.format.format(profile.getBirthday())).replace("%years", String.valueOf(years)));
      }

   }
}
