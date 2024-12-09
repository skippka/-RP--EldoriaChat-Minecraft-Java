/* Decompiler 57ms, total 432ms, lines 204 */
package me.eldoriaChat;

import com.comphenix.protocol.ProtocolLibrary;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.eldoriaChat.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.eldoriaChat.data.gui.GuiContainer;
import me.eldoriaChat.data.jail.JailContainer;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.profile.ProfileContainer;
import me.eldoriaChat.data.registration.RegistrationChain;
import me.eldoriaChat.data.registration.RegistrationElement;
import me.eldoriaChat.data.registration.RegistrationProcess;
import me.eldoriaChat.data.registration.RegistrationProcessor;
import me.eldoriaChat.data.registration.impl.AgeElement;
import me.eldoriaChat.data.registration.impl.NameElement;
import me.eldoriaChat.depend.ProtocolPlayerInfoListener;
import me.eldoriaChat.util.*;
import me.eldoriaChat.listener.*;
import me.eldoriaChat.data.jail.JailContainer;
import me.eldoriaChat.util.Values;

public class Main extends JavaPlugin implements CommandExecutor, Listener {
    private static Main instance;
    private static final Map<String, ICommand> commands = new HashMap();
    private static final JailContainer jailContainer = new JailContainer();
    private static final GuiContainer guiContainer = new GuiContainer();
    private static final ProfileContainer profileContainer = new ProfileContainer();
    private static final RegistrationProcessor registrationProcessor = new RegistrationProcessor();
    private static final Map<String, RegistrationProcess> registrations = new HashMap();
    private static final RegistrationChain chain = new RegistrationChain(new RegistrationElement[]{new NameElement(), new AgeElement()});

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        Values.load(this.getConfig());
        this.loadManagersData();
        this.registerListeners();
        this.registerCommands();
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player onlinePlayer = (Player)var1.next();
            if (!profileContainer.contains(onlinePlayer)) {
                profileContainer.add(new Profile(onlinePlayer));
            }
        }

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("recipe"), Values.KEY.clone());
        recipe.shape(new String[]{"abc", "def", "ghi"});
        char symbol = 'a';

        for(int i = 0; i < 9; ++i) {
            recipe.setIngredient(symbol++, Values.CRAFT[i]);
        }

        Bukkit.addRecipe(recipe);
        Bukkit.getScheduler().runTaskTimer(this, this::save, 18000L, 18000L);
        (new Placeholder()).register();
    }

    private void loadManagersData() {
        profileContainer.getLoader().loadFromFile(FileLoader.byFileName(this, "profiles.yml"));
        jailContainer.getLoader().loadFromFile(FileLoader.byFileName(this, "jails.yml"));
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerCommandListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerGuiListener(), this);
        if (Values.FRIENDS_ENABLE) {
            pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
            pluginManager.registerEvents(new PlayerChangeNameListener(), this);
            ProtocolLibrary.getProtocolManager().addPacketListener(new ProtocolPlayerInfoListener(this));
        }

        if (Values.DOOR_INTEGRATION) {
            pluginManager.registerEvents(new PlayerDoorInteractListener(), this);
        }

    }

    private void registerCommands() {
        commands.put("changename", new CommandChangeName());
        commands.put("arrest", new CommandArrest());
        commands.put("letout", new CommandLetout());
        commands.put("addtime", new CommandAddtime());
        commands.put("scprison", new CommandScprison());
        commands.put("slprison", new CommandSlprison());
        commands.put("nrp", new CommandNrp());
        commands.put("me", new CommandMe());
        commands.put("do", new CommandDo());
        commands.put("try", new CommandTry());
        commands.put("coin", new CommandCoin());
        commands.put("todo", new CommandTodo());
        commands.put("cube", new CommandCube());
        commands.put("wh", new CommandWh());
        commands.put("sh", new CommandSh());
        commands.put("gme", new CommandGme());
        commands.put("gdo", new CommandGdo());
        commands.put("gtry", new CommandGtry());
        commands.put("rpage", new CommandRpage());
        commands.put("rp", new CommandRp());
        commands.put("birthday", new CommandBirthday());
        commands.put("namereset", new CommandNamereset());
        commands.put("roll", new CommandRoll());
        commands.put("prison", new CommandPrison());
    }

    private void save() {
        try {
            profileContainer.getLoader().saveToFile(new File(this.getDataFolder(), "profiles.yml"));
            jailContainer.getLoader().saveToFile(new File(this.getDataFolder(), "jails.yml"));
        } catch (IOException var2) {
            this.getLogger().warning("An error occurred while saving data to a file");
        }

    }

    public void onDisable() {
        this.save();
    }

    public static RegistrationProcessor getRegistrationProcessor() {
        return registrationProcessor;
    }

    public static RegistrationChain getRegistrationChain() {
        return chain;
    }

    public static JailContainer getJailContainer() {
        return jailContainer;
    }

    public static ProfileContainer getProfileContainer() {
        return profileContainer;
    }

    public static GuiContainer getGuiContainer() {
        return guiContainer;
    }

    public static Main getInstance() {
        return instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (commands.containsKey(command.getName())) {
            ((ICommand)commands.get(command.getName())).execute(sender, args);
        }

        return false;
    }
}