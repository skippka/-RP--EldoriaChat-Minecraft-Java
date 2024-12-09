/* Decompiler 24ms, total 194ms, lines 89 */
package me.eldoriaChat.data.jail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;

public class Jail {
   private final String id;
   private final Map<String, Long> prisoners;
   private Location enter;
   private Location out;

   public Jail(String id, Location enter, Location out) {
      this.prisoners = new HashMap();
      this.id = id;
      this.enter = enter;
      this.out = out;
      Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::tick, 0L, 20L);
   }

   public Jail(String id) {
      this(id, Bukkit.getWorld("world").getSpawnLocation(), Bukkit.getWorld("world").getSpawnLocation());
   }

   public String getId() {
      return this.id;
   }

   public Location getEnter() {
      return this.enter;
   }

   public Location getOut() {
      return this.out;
   }

   public void setEnter(Location enter) {
      this.enter = enter;
   }

   public void setOut(Location out) {
      this.out = out;
   }

   public void addPrisoner(String name, long time) {
      this.prisoners.put(name, time);
   }

   public void removePrisoner(String name) {
      this.prisoners.remove(name);
   }

   public void setTime(String name, long time) {
      this.prisoners.replace(name, time);
   }

   public long getTime(String name) {
      return (Long)this.prisoners.get(name);
   }

   public Set<String> getPlayers() {
      return this.prisoners.keySet();
   }

   private void tick() {
      Iterator var1 = this.prisoners.keySet().iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         long seconds = (Long)this.prisoners.get(key);
         if (seconds == 0L) {
            Player player = Bukkit.getPlayer(key);
            if (player != null) {
               this.removePrisoner(key);
               player.teleport(this.out);
            }
         } else {
            this.prisoners.put(key, seconds - 1L);
         }
      }

   }
}
