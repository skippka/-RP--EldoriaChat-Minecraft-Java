/* Decompiler 45ms, total 207ms, lines 103 */
package me.eldoriaChat.data.jail;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.eldoriaChat.util.Container;

public class JailContainer extends Container<String, Jail> {
   private final JailContainer.Loader loader = new JailContainer.Loader();

   public JailContainer.Loader getLoader() {
      return this.loader;
   }

   public Collection<Jail> getJails() {
      return this.map.values();
   }

   public class Loader {
      public void loadFromFile(YamlConfiguration yaml) {
         Iterator var2 = yaml.getKeys(false).iterator();

         while(true) {
            ConfigurationSection section;
            Jail jail;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               String id = (String)var2.next();
               section = yaml.getConfigurationSection(id);
               Location enter = this.fromString(section.getString("enter"));
               Location out = this.fromString(section.getString("out"));
               jail = new Jail(id, enter, out);
               JailContainer.this.map.put(id, jail);
            } while(!section.contains("prisoners"));

            ConfigurationSection prisonersSection = section.getConfigurationSection("prisoners");
            Iterator var9 = prisonersSection.getKeys(false).iterator();

            while(var9.hasNext()) {
               String key = (String)var9.next();

               String nickname;
               try {
                  nickname = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
               } catch (IllegalArgumentException var14) {
                  nickname = key;
               }

               long time = prisonersSection.getLong(key);
               jail.addPrisoner(nickname, time);
            }
         }
      }

      public void saveToFile(File file) throws IOException {
         YamlConfiguration yaml = new YamlConfiguration();
         Iterator var3 = JailContainer.this.map.values().iterator();

         while(var3.hasNext()) {
            Jail jail = (Jail)var3.next();
            yaml.set(jail.getId() + ".enter", this.fromLocation(jail.getEnter()));
            yaml.set(jail.getId() + ".out", this.fromLocation(jail.getOut()));
            Iterator var5 = jail.getPlayers().iterator();

            while(var5.hasNext()) {
               String player = (String)var5.next();
               long time = jail.getTime(player);
               yaml.set(jail.getId() + ".prisoners." + player, time);
            }
         }

         yaml.save(file);
      }

      private String fromLocation(Location loc) {
         if (loc == null) {
            return null;
         } else {
            String var10000 = loc.getWorld().getName();
            return var10000 + "=" + loc.getBlockX() + "=" + loc.getBlockY() + "=" + loc.getBlockZ();
         }
      }

      private Location fromString(String locationString) {
         if (locationString != null && !locationString.isEmpty()) {
            String[] split = locationString.split("=");
            return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
         } else {
            return null;
         }
      }
   }
}
