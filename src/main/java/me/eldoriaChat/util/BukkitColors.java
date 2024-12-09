/* Decompiler 17ms, total 372ms, lines 25 */
package me.eldoriaChat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BukkitColors {
   private static final Pattern RGB_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");
   private static final String COLOR_CHAR = "&";

   public static String format(String original) {
      if (original != null && !original.isEmpty()) {
         for(Matcher matcher = RGB_PATTERN.matcher(original); matcher.find(); matcher = RGB_PATTERN.matcher(original)) {
            String color = original.substring(matcher.start() + 1, matcher.end());
            String var10001 = "&" + color;
            char var10002 = color.charAt(1);
            original = original.replace(var10001, "&x&" + var10002 + "&" + color.charAt(2) + "&" + color.charAt(3) + "&" + color.charAt(4) + "&" + color.charAt(5) + "&" + color.charAt(6));
         }

         return original.replace("&", "§");
      } else {
         return original;
      }
   }
}
