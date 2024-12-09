/* Decompiler 11ms, total 179ms, lines 45 */
package me.eldoriaChat.depend;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;

public class ProtocolPlayerInfoListener extends PacketAdapter {
   private final boolean is1_19_3;

   public ProtocolPlayerInfoListener(Main plugin) {
      super(plugin, new PacketType[]{Server.PLAYER_INFO});
      this.is1_19_3 = MinecraftVersion.getCurrentVersion().isAtLeast(MinecraftVersion.FEATURE_PREVIEW_UPDATE);
      this.plugin = plugin;
   }

   public void onPacketSending(PacketEvent event) {
      PacketContainer packet = event.getPacket();
      Set<PlayerInfoAction> actions = this.is1_19_3 ? (Set)packet.getPlayerInfoActions().read(0) : Sets.newHashSet(new PlayerInfoAction[]{(PlayerInfoAction)packet.getPlayerInfoAction().read(0)});
      if (((Set)actions).contains(PlayerInfoAction.ADD_PLAYER)) {
         PlayerInfoData infoData = (PlayerInfoData)((List)packet.getPlayerInfoDataLists().read(this.is1_19_3 ? 1 : 0)).get(0);
         Player player = Bukkit.getPlayer(infoData.getProfileId());
         if (player != null) {
            Profile profile = Main.getProfileContainer().get(player);
            String displayName = profile.getName(event.getPlayer().getName());
            PlayerInfoData newData = new PlayerInfoData(infoData.getProfile(), infoData.getLatency(), infoData.getGameMode(), WrappedChatComponent.fromLegacyText(displayName));
            packet.getPlayerInfoDataLists().write(this.is1_19_3 ? 1 : 0, Collections.singletonList(newData));
         }
      }
   }
}
