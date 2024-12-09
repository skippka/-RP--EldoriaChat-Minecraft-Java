/* Decompiler 22ms, total 250ms, lines 21 */
package me.eldoriaChat.commands;

import org.bukkit.command.CommandSender;

public interface ICommand {
   void execute(CommandSender var1, String[] var2);

   default String joinArgs(String[] args, int start) {
      StringBuilder builder = new StringBuilder();

      for(int i = start; i < args.length; ++i) {
         builder.append(args[i]);
         if (i + 1 < args.length) {
            builder.append(" ");
         }
      }

      return builder.toString();
   }
}
