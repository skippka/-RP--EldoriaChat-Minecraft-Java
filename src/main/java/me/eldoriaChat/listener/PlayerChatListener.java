/* Decompiler 19ms, total 221ms, lines 40 */
package me.eldoriaChat.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.registration.RegistrationProcess;
import me.eldoriaChat.data.registration.RegistrationProcessor;
import me.eldoriaChat.util.Values;

public class PlayerChatListener implements Listener {
   @EventHandler
   public void onChat(AsyncPlayerChatEvent event) {
      Player player = event.getPlayer();
      String message = event.getMessage();
      RegistrationProcessor processor = Main.getRegistrationProcessor();
      RegistrationProcess process = (RegistrationProcess)processor.get(player.getName());
      if (process != null) {
         event.setCancelled(true);
         if (processor.process(process, message)) {
            Profile profile = process.getProfile();
            BaseComponent[] component = TextComponent.fromLegacyText(Values.REGISTRATION_CONFIRMATION_HEADER.replace("%displayname", profile.getFullName()).replace("%birthday", Profile.DATE_FORMAT.format(profile.getBirthday())));
            TextComponent accept = new TextComponent(TextComponent.fromLegacyText(Values.REGISTRATION_CONFIRMATION_ACCEPT));
            accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/rp accept"));
            TextComponent reset = new TextComponent(TextComponent.fromLegacyText(Values.REGISTRATION_CONFIRMATION_RESET));
            reset.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/rp reset"));
            player.spigot().sendMessage(component);
            player.spigot().sendMessage(new TextComponent(new BaseComponent[]{accept, new TextComponent(" "), reset}));
         }
      }

   }
}
