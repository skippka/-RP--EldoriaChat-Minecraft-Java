/* Decompiler 54ms, total 214ms, lines 93 */
package me.eldoriaChat.depend;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;

public class ProtocolLib {
   private static final Main PLUGIN = Main.getInstance();
   private static final ProfileContainer CONTAINER = Main.getProfileContainer();
   private static final ProtocolManager MANAGER = ProtocolLibrary.getProtocolManager();
   private static final boolean is1_19_3;

   public static boolean isProvided() {
      Plugin protocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib");
      return protocolLib != null && protocolLib.isEnabled();
   }

   public static void updateFull(Player player) {
      Profile profile = CONTAINER.get(player);
      List<Player> otherPlayers = (List)Bukkit.getOnlinePlayers().stream().filter((target) -> {
         return !target.equals(player);
      }).collect(Collectors.toList());
      setSelfDisplayName(player, profile.getFullName());
      Iterator var3 = otherPlayers.iterator();

      while(var3.hasNext()) {
         Player other = (Player)var3.next();
         other.hidePlayer(PLUGIN, player);
         other.showPlayer(PLUGIN, player);
         player.hidePlayer(PLUGIN, other);
         player.showPlayer(PLUGIN, other);
      }

   }

   public static void updateSelf(Player player) {
      Profile profile = CONTAINER.get(player);
      List<Player> otherPlayers = (List)Bukkit.getOnlinePlayers().stream().filter((target) -> {
         return !target.equals(player);
      }).collect(Collectors.toList());
      setSelfDisplayName(player, profile.getFullName());
      Iterator var3 = otherPlayers.iterator();

      while(var3.hasNext()) {
         Player other = (Player)var3.next();
         other.hidePlayer(PLUGIN, player);
         other.showPlayer(PLUGIN, player);
      }

   }

   public static void updateSelf(Player player, Player target) {
      player.hidePlayer(PLUGIN, target);
      player.showPlayer(PLUGIN, target);
   }

   private static void setSelfDisplayName(Player player, String displayName) {
      PlayerInfoData infoData = new PlayerInfoData(new WrappedGameProfile(player.getUniqueId(), player.getName()), 0, NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromLegacyText(displayName));
      PacketContainer container = new PacketContainer(Server.PLAYER_INFO);
      if (is1_19_3) {
         container.getPlayerInfoActions().write(0, EnumSet.of(PlayerInfoAction.UPDATE_DISPLAY_NAME));
         container.getPlayerInfoDataLists().write(1, Collections.singletonList(infoData));
      } else {
         container.getPlayerInfoAction().write(0, PlayerInfoAction.UPDATE_DISPLAY_NAME);
         container.getPlayerInfoDataLists().write(0, Collections.singletonList(infoData));
      }

      MANAGER.sendServerPacket(player, container);
   }

   static {
      is1_19_3 = MinecraftVersion.getCurrentVersion().isAtLeast(MinecraftVersion.FEATURE_PREVIEW_UPDATE);
   }
}
