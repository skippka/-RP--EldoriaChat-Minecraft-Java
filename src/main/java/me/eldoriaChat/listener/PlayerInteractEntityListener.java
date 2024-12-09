/* Decompiler 40ms, total 198ms, lines 82 */
package me.eldoriaChat.listener;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.gui.GuiContainer;
import me.eldoriaChat.data.gui.impl.GuiFriend.Builder;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;
import me.eldoriaChat.util.Values;

public class PlayerInteractEntityListener implements Listener {
   private final Map<Player, PlayerInteractEntityListener.CooldownPair> cooldowns = new HashMap();
   private final GuiContainer guiContainer = Main.getGuiContainer();

   @EventHandler
   public void onInteract(PlayerInteractAtEntityEvent event) {
      Entity clicked = event.getRightClicked();
      if (event.getHand() == EquipmentSlot.HAND) {
         if (clicked.getType() == EntityType.PLAYER) {
            if (event.getPlayer().isSneaking()) {
               Player sender = event.getPlayer();
               Player target = (Player)clicked;
               if (!this.guiContainer.contains(target)) {
                  ProfileContainer container = Main.getProfileContainer();
                  Profile senderProfile = container.get(sender);
                  Profile targetProfile = container.get(target);
                  if (senderProfile.getFriendList().contains(target.getName())) {
                     (new Builder()).setPlayer(sender, senderProfile).setTarget(target, targetProfile).setContainer(this.guiContainer).register().open();
                  } else {
                     PlayerInteractEntityListener.CooldownPair pair = (PlayerInteractEntityListener.CooldownPair)this.cooldowns.get(sender);
                     if (pair != null && pair.getTarget().equals(target) && pair.getDifference() < Values.FRIENDS_COOLDOWN) {
                        sender.sendMessage(Values.FRIENDS_COOLDOWN_MESSAGE);
                        event.setCancelled(true);
                     } else {
                        (new me.eldoriaChat.data.gui.impl.GuiRequest.Builder()).setSender(sender, senderProfile).setTarget(target, targetProfile).setContainer(this.guiContainer).register().open();
                        this.cooldowns.put(sender, new PlayerInteractEntityListener.CooldownPair(sender, target));
                        sender.sendMessage(Values.FRIENDS_REQUEST_SEND.replace("%stranger", targetProfile.getStrangerName()));
                     }
                  }
               }
            }
         }
      }
   }

   private static class CooldownPair {
      private final Player sender;
      private final Player target;
      private final long cooldown;

      private CooldownPair(Player sender, Player target) {
         this.sender = sender;
         this.target = target;
         this.cooldown = System.currentTimeMillis();
      }

      public long getCooldown() {
         return this.cooldown;
      }

      public long getDifference() {
         return System.currentTimeMillis() - this.cooldown;
      }

      public Player getSender() {
         return this.sender;
      }

      public Player getTarget() {
         return this.target;
      }
   }
}
