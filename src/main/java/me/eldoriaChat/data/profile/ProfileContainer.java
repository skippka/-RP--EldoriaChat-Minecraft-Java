/* Decompiler 65ms, total 440ms, lines 106 */
package me.eldoriaChat.data.profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.util.Container;

public class ProfileContainer extends Container<String, Profile> {
   private final ProfileContainer.Loader loader = new ProfileContainer.Loader();

   public ProfileContainer.Loader getLoader() {
      return this.loader;
   }

   public void add(Profile profile) {
      this.add(profile.getNickname(), profile);
   }

   public Profile get(Player player) {
      return (Profile)this.get(player.getName());
   }

   public boolean contains(Player player) {
      return this.contains(player.getName());
   }

   public Collection<Profile> getProfiles() {
      return this.map.values();
   }

   public class Loader {
      public void loadFromFile(YamlConfiguration yaml) {
         Iterator var2 = yaml.getKeys(false).iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            ConfigurationSection section = yaml.getConfigurationSection(key);
            String nickname = section.contains("realname") ? Bukkit.getOfflinePlayer(UUID.fromString(key)).getName() : key;
            String name = section.getString("name", (String)null);
            String surname = section.getString("surname", (String)null);
            String jail = section.getString("jail", (String)null);
            long birthday = section.getLong("birthday", 0L);
            Profile profile = new Profile(nickname, name, surname);
            if (birthday != 0L) {
               profile.setBirthday(new Date(birthday));
            }

            if (jail != null) {
               profile.setJail((Jail)Main.getJailContainer().get(jail));
            }

            ProfileContainer.this.map.put(nickname, profile);
            List<String> friends = section.getStringList("friends");
            Iterator var13 = friends.iterator();

            while(var13.hasNext()) {
               String friendName = (String)var13.next();
               profile.getFriendList().add(friendName);
            }
         }

      }

      public void saveToFile(File file) throws IOException {
         YamlConfiguration yaml = new YamlConfiguration();
         Iterator var3 = ProfileContainer.this.map.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<String, Profile> entry = (Entry)var3.next();
            Profile profile = (Profile)entry.getValue();
            String nickname = (String)entry.getKey();
            String name = profile.getName();
            String surname = profile.getSurname();
            List<String> friends = new ArrayList(profile.getFriendList());
            Jail jail = profile.getJail();
            if (jail != null) {
               jail.getId();
            } else {
               Object var10000 = null;
            }

            long birthday = profile.hasBirthday() ? profile.getBirthday().getTime() : 0L;
            yaml.set(nickname + ".name", name);
            yaml.set(nickname + ".jail", jail);
            yaml.set(nickname + ".surname", surname);
            yaml.set(nickname + ".birthday", birthday);
            yaml.set(nickname + ".friends", friends);
         }

         yaml.save(file);
      }
   }
}
