/* Decompiler 4ms, total 168ms, lines 32 */
package me.eldoriaChat.data.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Gui {
   protected final Player player;
   protected final Inventory inventory;

   public Gui(Player player, String title, int size) {
      this(player, Bukkit.createInventory((InventoryHolder)null, size, title));
   }

   public Gui(Player player, Inventory inventory) {
      this.player = player;
      this.inventory = inventory;
   }

   public Player getPlayer() {
      return this.player;
   }

   public abstract void open();

   public abstract void onClick(InventoryClickEvent var1);

   protected abstract boolean onClose();
}
