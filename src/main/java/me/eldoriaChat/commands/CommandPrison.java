/* Decompiler 33ms, total 195ms, lines 54 */
package me.eldoriaChat.commands;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.data.jail.JailContainer;
import me.eldoriaChat.util.Values;

public class CommandPrison implements ICommand {
   public void execute(CommandSender sender, String[] args) {
      JailContainer container = Main.getJailContainer();
      if (args.length >= 1 && args[0].equalsIgnoreCase("list")) {
         if (!sender.hasPermission("eldoriachat.prison.list")) {
            sender.sendMessage(Values.PERMISSION);
         } else {
            List<String> collect = (List)container.getJails().stream().map(Jail::getId).collect(Collectors.toList());
            String message = Values.PRISON_LIST.replace("<jails>", collect.isEmpty() ? "нет" : String.join(", ", collect));
            sender.sendMessage(message);
         }

      } else if (args.length < 2) {
         sender.sendMessage(Values.PRISON_HELP);
      } else {
         String jailId = args[1];
         if (args[0].equalsIgnoreCase("create")) {
            if (!sender.hasPermission("eldoriachat.prison.create")) {
               sender.sendMessage(Values.PERMISSION);
            } else if (container.contains(jailId)) {
               sender.sendMessage(Values.PRISON_ALREADY_CREATED);
            } else {
               Jail jail = new Jail(jailId);
               container.add(jailId, jail);
               sender.sendMessage(Values.PRISON_CREATE);
            }

         } else {
            if (args[0].equalsIgnoreCase("delete")) {
               if (!sender.hasPermission("eldoriachat.prison.delete")) {
                  sender.sendMessage(Values.PERMISSION);
               } else if (!container.contains(jailId)) {
                  sender.sendMessage(Values.NO_JAIL);
               } else {
                  container.remove(jailId);
                  sender.sendMessage(Values.PRISON_DELETE);
               }
            }

         }
      }
   }
}
