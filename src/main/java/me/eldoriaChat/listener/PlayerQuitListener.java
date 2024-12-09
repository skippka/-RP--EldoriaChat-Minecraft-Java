/* Decompiler 5ms, total 184ms, lines 22 */
package me.eldoriaChat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.registration.RegistrationProcessor;

public class PlayerQuitListener implements Listener {
   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      String name = player.getName();
      RegistrationProcessor processor = Main.getRegistrationProcessor();
      if (processor.killProcess(name)) {
         Main.getProfileContainer().remove(name);
      }

   }
}
