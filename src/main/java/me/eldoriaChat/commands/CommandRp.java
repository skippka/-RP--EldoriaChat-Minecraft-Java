/* Decompiler 19ms, total 361ms, lines 45 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.registration.RegistrationProcess;
import me.eldoriaChat.data.registration.RegistrationProcessor;
import me.eldoriaChat.depend.ProtocolLib;
import me.eldoriaChat.util.Values;

public class CommandRp implements ICommand {
   private final Main plugin = Main.getInstance();
   private final RegistrationProcessor container = Main.getRegistrationProcessor();

   public void execute(CommandSender sender, String[] args) {
      if (args.length >= 1 && args[0].equalsIgnoreCase("reset") && this.container.contains(sender.getName())) {
         ((RegistrationProcess)this.container.get(sender.getName())).reset();
      } else if (args.length >= 1 && args[0].equalsIgnoreCase("accept") && this.container.contains(sender.getName())) {
         this.container.killProcess(sender.getName());
         Profile profile = (Profile)Main.getProfileContainer().get(sender.getName());
         Player player = (Player)sender;
         player.resetTitle();
         player.removePotionEffect(PotionEffectType.BLINDNESS);
         sender.sendMessage(Values.REGISTRATION_END.replace("%displayname", profile.getFullName()).replace("%birthday", Profile.DATE_FORMAT.format(profile.getBirthday())));
         if (Values.FRIENDS_ENABLE) {
            ProtocolLib.updateSelf(player);
         }

      } else {
         if (!sender.hasPermission("eldoriachat.reload")) {
            sender.sendMessage(Values.PERMISSION);
         } else if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            this.plugin.reloadConfig();
            Values.load(this.plugin.getConfig());
            sender.sendMessage("§eКонфигурации плагина были перезагружены.");
         } else {
            sender.sendMessage("/rp reload");
         }

      }
   }
}
