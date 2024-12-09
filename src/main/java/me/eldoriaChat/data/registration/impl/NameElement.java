/* Decompiler 26ms, total 387ms, lines 45 */
package me.eldoriaChat.data.registration.impl;

import java.util.Iterator;
import org.bukkit.entity.Player;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.registration.RegistrationElement;
import me.eldoriaChat.util.Values;

public class NameElement implements RegistrationElement {
   public void sendInformation(Player player) {
      player.sendTitle(Values.REGISTRATION_ENTER_NAME_TITLE, Values.REGISTRATION_ENTER_NAME_SUBTITLE, 0, 100000, 0);
   }

   public boolean recieveMessage(Profile profile, String message) {
      if (!message.contains(" ")) {
         return false;
      } else {
         int separatorIndex = message.indexOf(" ");
         String firstName = message.substring(0, separatorIndex);
         String lastName = message.substring(separatorIndex + 1);
         int size = (firstName + lastName).trim().length();
         if (size <= Values.CHANGENAME_SIZE_MAX && size >= Values.CHANGENAME_SIZE_MIN) {
            Iterator var7 = Values.FORBIDDEN_WORDS.iterator();

            String word;
            do {
               if (!var7.hasNext()) {
                  profile.setName(firstName);
                  profile.setSurname(lastName);
                  return true;
               }

               word = (String)var7.next();
            } while(!firstName.contains(word) && !lastName.contains(word));

            profile.getPlayer().sendMessage(Values.NAME_CONTAINS_FORBIDDEN_WORD);
            return false;
         } else {
            profile.getPlayer().sendMessage(Values.CHANGENAME_SIZE_MESSAGE);
            return false;
         }
      }
   }
}
