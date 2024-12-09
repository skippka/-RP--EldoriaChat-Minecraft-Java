package me.eldoriaChat.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.eldoriaChat.Main;
import me.eldoriaChat.commands.ICommand;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.event.PlayerChangeNameEvent;
import me.eldoriaChat.util.Values;

public class CommandChangeName
        implements ICommand {
    private final Map<String, ChangeNameAccepter> accepterMap = new HashMap<String, ChangeNameAccepter>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Values.INFO_CHANGENAME);
        } else if (args[0].equalsIgnoreCase("accept")) {
            this.executeChangeNameAccept(sender, args);
        } else if (args[0].equalsIgnoreCase("decline")) {
            this.executeChangeNameDecline(sender, args);
        } else {
            this.executeChangeName(sender, args);
        }
    }

    private void executeChangeNameAccept(CommandSender sender, String[] args) {
        if (!sender.hasPermission("eldoriarp.changename.accept")) {
            sender.sendMessage(Values.PERMISSION);
            return;
        }
        ChangeNameAccepter accepter = this.accepterMap.remove(args[1]);
        if (accepter != null) {
            if (accepter.accept()) {
                sender.sendMessage(Values.CHANGENAME_ACCEPT_USE);
                accepter.player().sendMessage(Values.CHANGENAME_ACCEPT_TARGET);
            } else {
                sender.sendMessage(Values.OFFLINE);
            }
        } else {
            sender.sendMessage(Values.CHANGENAME_ACCEPT_NO_REQUEST);
        }
    }

    private void executeChangeNameDecline(CommandSender sender, String[] args) {
        if (!sender.hasPermission("eldoriarp.changename.decline")) {
            sender.sendMessage(Values.PERMISSION);
            return;
        }
        ChangeNameAccepter accepter = this.accepterMap.remove(args[1]);
        if (accepter != null) {
            sender.sendMessage(Values.CHANGENAME_DECLINE_USE);
            Player target = accepter.player();
            if (target != null) {
                target.sendMessage(Values.CHANGENAME_DECLINE_TARGET);
            }
        } else {
            sender.sendMessage(Values.CHANGENAME_DECLINE_NO_REQUEST);
        }
    }

    private void executeChangeName(CommandSender sender, String[] args) {
        if (!sender.hasPermission("eldoriarp.changename")) {
            sender.sendMessage(Values.PERMISSION);
            return;
        }
        boolean self = args.length == 2;
        Player target = self ? (Player)((Object)sender) : Bukkit.getPlayer(args[0]);
        String name = args[self ? 0 : 1];
        String surname = args[self ? 1 : 2];
        if (target == null) {
            sender.sendMessage(Values.OFFLINE);
            return;
        }
        if (!target.equals(sender) && !sender.hasPermission("eldoriarp.changename.other")) {
            sender.sendMessage(Values.PERMISSION);
            return;
        }
        Profile profile = Main.getProfileContainer().get(target);
        if (target.equals(sender) && !profile.isCooldownExpired()) {
            long cooldown = profile.getChangeNameCooldown();
            long minutes = (cooldown - System.currentTimeMillis()) / 60000L;
            sender.sendMessage(Values.CHANGENAME_COOLDOWN_MESSSAGE.replace("%time", minutes + " мин."));
            return;
        }
        int size = (name + surname).trim().length();
        if (size > Values.CHANGENAME_SIZE_MAX || size < Values.CHANGENAME_SIZE_MIN) {
            sender.sendMessage(Values.CHANGENAME_SIZE_MESSAGE);
            return;
        }
        for (String word : Values.FORBIDDEN_WORDS) {
            if (!name.contains(word) && !surname.contains(word)) continue;
            sender.sendMessage(Values.NAME_CONTAINS_FORBIDDEN_WORD);
            return;
        }
        if (Values.CHANGENAME_REQUEST_ENABLE) {
            List<Player> staffList = this.getStaffList();
            if (staffList.isEmpty()) {
                sender.sendMessage(Values.CHANGENAME_REQUEST_NO_STAFF);
                return;
            }
            profile.setChangeNameCooldown(System.currentTimeMillis() + (long)Values.CHANGENAME_COOLDOWN * 60000L);
            target.sendMessage(Values.CHANGENAME_REQUEST_MESSAGE);
            this.accepterMap.put(target.getName(), new ChangeNameAccepter(target, profile, name, surname));
            BaseComponent[] header = TextComponent.fromLegacyText(Values.CHANGENAME_REQUEST_FOR_STAFF.replace("%nick", target.getName()).replace("%firstname", name).replace("%lastname", surname));
            TextComponent footer = new TextComponent();
            footer.addExtra(this.createButton(Values.CHANGENAME_REQUEST_ACCEPT_FORMAT, null, "/changename accept " + target.getName()));
            footer.addExtra(" ");
            footer.addExtra(this.createButton(Values.CHANGENAME_REQUEST_DECLINE_FORMAT, null, "/changename decline " + target.getName()));
            for (Player staff : staffList) {
                staff.spigot().sendMessage(header);
                staff.spigot().sendMessage(footer);
            }
        } else {
            profile.setChangeNameCooldown(System.currentTimeMillis() + (long)Values.CHANGENAME_COOLDOWN * 60000L);
            new ChangeNameAccepter(target, profile, name, surname).accept();
            target.sendMessage(Values.CHANGENAME);
        }
    }

    private TextComponent createButton(String text, String hover, String command) {
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        if (hover != null) {
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));
        }
        if (command != null) {
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        }
        return textComponent;
    }

    private List<Player> getStaffList() {
        // Преобразуем Collection в List
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("eldoriachat.changename.notify"))
                .collect(Collectors.toList());
    }

    private record ChangeNameAccepter(Player player, Profile profile, String name, String surname) {
        public boolean accept() {
            if (this.player == null || !this.player.isOnline()) {
                return false;
            }
            this.profile.setName(this.name);
            this.profile.setSurname(this.surname);
            if (Values.FRIENDS_ENABLE) {
                PlayerChangeNameEvent event = new PlayerChangeNameEvent(this.player, this.profile);
                Bukkit.getPluginManager().callEvent(event);
            } else {
                this.player.setDisplayName(this.profile.getFullName());
            }
            return true;
        }
    }
}
