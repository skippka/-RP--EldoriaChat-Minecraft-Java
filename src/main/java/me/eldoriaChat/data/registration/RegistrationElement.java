/* Decompiler 2ms, total 382ms, lines 11 */
package me.eldoriaChat.data.registration;

import org.bukkit.entity.Player;
import me.eldoriaChat.data.profile.Profile;

public interface RegistrationElement {
   void sendInformation(Player var1);

   boolean recieveMessage(Profile var1, String var2);
}
