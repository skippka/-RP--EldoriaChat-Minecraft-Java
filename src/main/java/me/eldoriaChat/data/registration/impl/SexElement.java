package me.eldoriaChat.data.registration.impl;

import org.bukkit.entity.Player;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.data.registration.RegistrationElement;
import me.eldoriaChat.util.Values;

public class SexElement implements RegistrationElement {

    @Override
    public void sendInformation(Player player) {
        player.sendTitle(Values.REGISTRATION_ENTER_SEX_TITLE, Values.REGISTRATION_ENTER_SEX_SUBTITLE, 0, 100000, 0);
    }

    @Override
    public boolean recieveMessage(Profile profile, String message) {
        String trimmedMessage = message.trim().toLowerCase();

        // Check if the input is valid
        if (trimmedMessage.equals("мужской") || trimmedMessage.equals("женский")) {
            profile.setSex(trimmedMessage); // Assuming there is a setGender method
            return true;
        } else {
            profile.getPlayer().sendMessage(Values.GENDER_INVALID_MESSAGE);
            return false;
        }
    }
}
