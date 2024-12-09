/* Decompiler 8ms, total 239ms, lines 38 */
package me.eldoriaChat.data.registration.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.entity.Player;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.registration.RegistrationElement;
import me.eldoriaChat.util.Values;

public class AgeElement implements RegistrationElement {
   public void sendInformation(Player player) {
      player.sendTitle(Values.REGISTRATION_ENTER_AGE_TITLE, Values.REGISTRATION_ENTER_AGE_SUBTITLE, 0, 100000, 0);
   }

   public boolean recieveMessage(Profile profile, String message) {
      Player player = profile.getPlayer();

      Date birthday;
      try {
         birthday = Profile.DATE_FORMAT.parse(message);
      } catch (ParseException var6) {
         player.sendMessage(Values.RPAGE_INCORRECT);
         return false;
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(birthday);
      if (calendar.get(1) <= Values.BIRTHDAY_YEAR_MAX && calendar.get(1) >= Values.BIRTHDAY_YEAR_MIN) {
         profile.setBirthday(birthday);
         return true;
      } else {
         player.sendMessage(Values.BIRTHDAY_TIME_ERROR);
         return false;
      }
   }
}
