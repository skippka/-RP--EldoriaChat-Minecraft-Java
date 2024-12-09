/* Decompiler 24ms, total 177ms, lines 63 */
package me.eldoriaChat.listener;

import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;
import me.eldoriaChat.data.registration.RegistrationElement;
import me.eldoriaChat.data.registration.RegistrationProcess;
import me.eldoriaChat.data.registration.RegistrationProcessor;
import me.eldoriaChat.util.Values;

public class PlayerJoinListener implements Listener {
   private final ProfileContainer container = Main.getProfileContainer();
   private final PotionEffect effect;

   public PlayerJoinListener() {
      this.effect = new PotionEffect(PotionEffectType.BLINDNESS, 100000, 0);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onJoin(PlayerJoinEvent e) {
      Player player = e.getPlayer();
      Profile profile = this.container.get(player);
      if (profile == null) {
         this.container.add(profile = new Profile(player));
         if (!profile.isRegistered() && Values.REGISTRATION_ENABLE) {
            for(int i = 0; i < 50; ++i) {
               player.sendMessage(" ");
            }

            player.addPotionEffect(this.effect);
            RegistrationProcessor processor = Main.getRegistrationProcessor();
            RegistrationElement[] elements = Main.getRegistrationChain().getElements();
            RegistrationProcess process = new RegistrationProcess(profile, elements);
            processor.add(process);
            process.nextStage();
         }
      } else if (profile.hasBirthday()) {
         Calendar calendar = Calendar.getInstance();
         Calendar birthday = Calendar.getInstance();
         birthday.setTime(profile.getBirthday());
         if (calendar.get(5) == birthday.get(5) && calendar.get(2) == birthday.get(2)) {
            Bukkit.broadcastMessage(Values.BIRTHDAY_ANNOUNCE.replace("%player", player.getName()));
         }
      }

      if (!Values.FRIENDS_ENABLE) {
         player.setDisplayName(profile.getFullName());
      }

   }
}
