/* Decompiler 6ms, total 363ms, lines 28 */
package me.eldoriaChat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.gui.Gui;
import me.eldoriaChat.data.gui.GuiContainer;

public class PlayerGuiListener implements Listener {
   @EventHandler
   public void onClick(InventoryClickEvent event) {
      GuiContainer container = Main.getGuiContainer();
      Gui gui = (Gui)container.get((Player)event.getWhoClicked());
      if (gui != null) {
         gui.onClick(event);
      }

   }

   @EventHandler
   public void onClose(InventoryCloseEvent event) {
      Main.getGuiContainer().onClose((Player)event.getPlayer());
   }
}
