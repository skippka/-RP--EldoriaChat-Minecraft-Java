package me.eldoriaChat;

import java.text.SimpleDateFormat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;
import me.eldoriaChat.util.Values;

public class Placeholder extends PlaceholderExpansion {
   private final SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");

   @NotNull
   public String getIdentifier() {
      return "eldoriachat";
   }

   @NotNull
   public String getAuthor() {
      return "Uninstal";
   }

   @NotNull
   public String getVersion() {
      return "1.0";
   }

   public String onRequest(OfflinePlayer p, @NotNull String params) {
      if (params == null) {
         $$$reportNull$$$0(0);
      }

      return this.onPlaceholderRequest((Player)p, params);
   }

   public String onPlaceholderRequest(Player player, String params) {
      ProfileContainer profileContainer = Main.getProfileContainer();
      Profile profile;
      String fullName;

      // Определение профиля по полному имени или по игроку
      if (params.contains("_")) {
         fullName = params.substring(params.lastIndexOf("_") + 1);
         if (!profileContainer.contains(fullName)) {
            return "NULL";
         }
         profile = profileContainer.get(fullName);
      } else {
         profile = profileContainer.get(player);
      }

      // Обработка различных плейсхолдеров
      if (params.startsWith("birthday")) {
         return profile.hasBirthday() ? Values.BIRTHDAY_PLACEHOLDER.replace("%date", this.dateParser.format(profile.getBirthday())) : "нет";
      } else if (params.startsWith("displayname")) {
         return profile.getFullName();
      } else if (params.startsWith("firstname")) {
         return profile.hasSurname() ? profile.getName() : "Не выбрано";
      } else if (params.startsWith("lastname")) {
         return profile.hasSurname() ? profile.getSurname() : "Не выбрано";
      } else if (params.startsWith("initials")) {
         if (!profile.hasSurname()) {
            return player.getName();
         } else {
            fullName = profile.getFullName();
            fullName = fullName.substring(0, fullName.indexOf(" ") + 2);
            fullName = fullName + ".";
            return fullName;
         }
      } else if (params.startsWith("years")) {
         // Добавляем обработку плейсхолдера years, который возвращает возраст
         return String.valueOf(profile.getYears()); // предполагается, что метод getYears() существует в Profile
      } else {
         return "unknown";
      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "params", "org/uninstal/minerp/Placeholder", "onRequest"));
   }
}
