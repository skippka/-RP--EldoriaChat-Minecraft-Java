/* Decompiler 4ms, total 380ms, lines 15 */
package me.eldoriaChat.data.gui;

import org.bukkit.entity.Player;
import me.eldoriaChat.util.Container;

public class GuiContainer extends Container<Player, Gui> {
   public void onClose(Player player) {
      Gui gui = (Gui)this.get(player);
      if (gui != null && gui.onClose()) {
         this.remove(player);
      }

   }
}
