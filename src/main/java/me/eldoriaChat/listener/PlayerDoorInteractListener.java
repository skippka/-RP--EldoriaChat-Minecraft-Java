/* Decompiler 46ms, total 229ms, lines 67 */
package me.eldoriaChat.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;
import me.eldoriaChat.Main;
import me.eldoriaChat.util.Values;
import wtf.choco.locksecurity.LockSecurity;


public class PlayerDoorInteractListener implements Listener {
   private final LockSecurity security = LockSecurity.getInstance();
   private final float chance = 0.5F;

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      final Block block = event.getClickedBlock();
      final Player player = event.getPlayer();
      ItemStack hand = event.getItem();
      if (hand != null && hand.isSimilar(Values.KEY) && block != null && block.getType().name().endsWith("DOOR") && this.security.isLockable(block.getType()) && this.security.getLockedBlockManager().isLocked(block) && block.getLocation().distance(player.getLocation()) <= 2.0D) {
         event.setCancelled(true);
         player.sendMessage(Values.BREAKING_START);
         hand.setAmount(hand.getAmount() - 1);
         (new BukkitRunnable() {
            private int ticks = 0;

            public void run() {
               ++this.ticks;
               if (block.getWorld().equals(player.getWorld()) && player.getLocation().distance(block.getLocation()) <= 2.0D) {
                  if (this.ticks == 100) {
                     this.cancel();
                     if (Math.random() > 0.5D) {
                        BlockState state = block.getState();
                        Block down = block.getRelative(0, -1, 0);
                        if (down.getType().name().endsWith("DOOR")) {
                           state = down.getState();
                        }

                        Openable openable = (Openable)state.getData();
                        openable.setOpen(!openable.isOpen());
                        state.setData((MaterialData)openable);
                        state.update(false, true);
                        player.sendMessage(Values.BREAKING_SUCCESS);
                     } else {
                        player.sendMessage(Values.BREAKING_FAIL);
                     }
                  }

                  player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(this.ticks + "%"));
               } else {
                  player.sendMessage(Values.BREAKING_DISTANCE);
                  this.cancel();
               }
            }
         }).runTaskTimer(Main.getInstance(), 1L, 1L);
      }
   }
}
