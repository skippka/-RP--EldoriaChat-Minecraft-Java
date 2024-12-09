/* Decompiler 19ms, total 183ms, lines 46 */
package me.eldoriaChat.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import me.eldoriaChat.data.profile.Profile;

public class PlayerChangeNameEvent extends Event {
   private static final HandlerList handlerList = new HandlerList();
   private final Player player;
   private final Profile profile;

   public static HandlerList getHandlerList() {
      return handlerList;
   }

   @NotNull
   public HandlerList getHandlers() {
      HandlerList var10000 = handlerList;
      if (var10000 == null) {
         $$$reportNull$$$0(0);
      }

      return var10000;
   }

   public PlayerChangeNameEvent(Player player, Profile profile) {
      this.player = player;
      this.profile = profile;
   }

   public Player getPlayer() {
      return this.player;
   }

   public Profile getProfile() {
      return this.profile;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/uninstal/eldoriarp/event/PlayerChangeNameEvent", "getHandlers"));
   }
}
