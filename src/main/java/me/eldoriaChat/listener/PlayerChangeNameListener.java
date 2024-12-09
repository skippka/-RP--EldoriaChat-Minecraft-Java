/* Decompiler 2ms, total 161ms, lines 14 */
package me.eldoriaChat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.eldoriaChat.depend.ProtocolLib;
import me.eldoriaChat.event.PlayerChangeNameEvent;

public class PlayerChangeNameListener implements Listener {
   @EventHandler
   public void onChange(PlayerChangeNameEvent event) {
      ProtocolLib.updateSelf(event.getPlayer());
   }
}
