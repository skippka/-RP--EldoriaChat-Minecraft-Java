/* Decompiler 11ms, total 360ms, lines 21 */
package me.eldoriaChat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.registration.RegistrationProcessor;

public class PlayerMoveListener implements Listener {
   @EventHandler
   public void onMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      RegistrationProcessor processor = Main.getRegistrationProcessor();
      if (processor.contains(player.getName())) {
         event.setCancelled(true);
      }

   }
}
