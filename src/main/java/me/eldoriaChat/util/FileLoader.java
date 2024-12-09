/* Decompiler 4ms, total 351ms, lines 13 */
package me.eldoriaChat.util;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileLoader {
   public static YamlConfiguration byFileName(JavaPlugin javaPlugin, String fileName) {
      File file = new File(javaPlugin.getDataFolder(), fileName);
      return file.exists() ? YamlConfiguration.loadConfiguration(file) : new YamlConfiguration();
   }
}
