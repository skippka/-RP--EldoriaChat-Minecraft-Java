/* Decompiler 28ms, total 178ms, lines 73 */
package me.eldoriaChat.commands;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class CommandRpage implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      if (args.length == 0) {
         sender.sendMessage(Values.INFO_RPAGE);
      } else if (!sender.hasPermission("eldoriachat.rpage")) {
         sender.sendMessage(Values.PERMISSION);
      } else {
         Player target;
         if (args.length >= 2 && sender.hasPermission("eldoriachat.rpage.other")) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
               sender.sendMessage(Values.OFFLINE);
            } else {
               Date date;
               try {
                  date = Profile.DATE_FORMAT.parse(args[0]);
               } catch (ParseException var12) {
                  sender.sendMessage(Values.RPAGE_INCORRECT);
                  return;
               }

               Profile profile = Main.getProfileContainer().get(target);
               profile.setBirthday(date);
               sender.sendMessage("§aУспешно!");
            }

            return;
         }

         target = (Player)sender;
         Profile profile = Main.getProfileContainer().get(target);
         if (profile.hasBirthday()) {
            sender.sendMessage(Values.RPAGE_ALREADY);
         } else {
            Date birthday;
            try {
               birthday = Profile.DATE_FORMAT.parse(args[0]);
            } catch (ParseException var13) {
               sender.sendMessage(Values.RPAGE_INCORRECT);
               return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);
            if (calendar.get(1) > Values.BIRTHDAY_YEAR_MAX || calendar.get(1) < Values.BIRTHDAY_YEAR_MIN) {
               sender.sendMessage(Values.BIRTHDAY_TIME_ERROR);
               return;
            }

            profile.setBirthday(birthday);
            Date now = Calendar.getInstance().getTime();
            long millis = now.getTime() - birthday.getTime();
            long years = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 365L;
            sender.sendMessage(Values.RPAGE_SUCCESS.replace("%birthday", args[0]).replace("%years", String.valueOf(years)));
         }
      }

   }
}
