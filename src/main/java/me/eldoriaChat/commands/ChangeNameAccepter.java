package me.eldoriaChat.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.event.PlayerChangeNameEvent;
import me.eldoriaChat.util.Values;

record ChangeNameAccepter(Player player, Profile profile, String name, String surname) {
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
