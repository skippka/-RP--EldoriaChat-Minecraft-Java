package me.eldoriaChat.listener;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;
import me.eldoriaChat.data.registration.RegistrationProcessor;
import me.eldoriaChat.util.Values;

public class PlayerCommandListener implements Listener {

   @EventHandler
   public void onCommand(PlayerCommandPreprocessEvent event) {
      ProfileContainer container = Main.getProfileContainer();
      Profile profile = container.get(event.getPlayer());

      // Блокировка команд для игроков в тюрьме
      if (profile.getJail() != null) {
         Iterator<String> iterator = Values.BLOCKED_COMMANDS.iterator();

         while (iterator.hasNext()) {
            String blockedCommand = iterator.next();
            if (event.getMessage().startsWith(blockedCommand)) {
               event.getPlayer().sendMessage(Values.COMMAND_IS_BLOCK);
               event.setCancelled(true);
               break;
            }
         }
      }

      // Ограничение команд для пользователей в процессе регистрации
      RegistrationProcessor processor = Main.getRegistrationProcessor();
      if (processor.contains(profile.getNickname())) {
         List<String> whitelist = Values.REGISTRATION_COMMANDS_WHITELIST;
         String eventMessage = event.getMessage();

         // Проверка команд с использованием whitelist
         if (whitelist.stream().noneMatch(eventMessage::startsWith) && !eventMessage.startsWith("/rp")) {
            event.setCancelled(true);
         }
      }
   }
}
