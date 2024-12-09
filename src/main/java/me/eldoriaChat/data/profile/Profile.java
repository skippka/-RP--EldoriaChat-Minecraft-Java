package me.eldoriaChat.data.profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.eldoriaChat.data.jail.Jail;
import me.eldoriaChat.util.Values;

public class Profile {
   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
   private static int lastIndex = 1;
   private final int index;
   private final String nickname;
   private String name;
   private String sex;
   private String surname;
   private long cooldown;
   private boolean requestEnabled;
   private Date birthday;
   private Jail jail;
   private final Set<String> friendList = new HashSet<>();

   public Profile(Player player) {
      this.index = lastIndex++;
      this.nickname = player.getName();
      this.name = player.getName();
   }

   public Profile(String nickname, String name, String surname) {
      this.index = lastIndex++;
      this.nickname = nickname;
      this.name = name;
      this.surname = surname;
   }

   public boolean isRegistered() {
      return this.surname != null && this.name != null && this.birthday != null;
   }

   public Set<String> getFriendList() {
      return this.friendList;
   }

   public int getIndex() {
      return this.index;
   }

   public Jail getJail() {
      return this.jail;
   }

   public void setJail(Jail jail) {
      this.jail = jail;
   }

   public boolean hasBirthday() {
      return this.birthday != null;
   }

   public void setBirthday(Date birthday) {
      this.birthday = birthday;
   }

   public Date getBirthday() {
      return this.birthday;
   }

   public String getNickname() {
      return this.nickname;
   }

   public String getName(String whoSee) {
      return !whoSee.equals(this.nickname) && !this.friendList.contains(whoSee) ? this.getStrangerName() : this.getFullName();
   }

   public String getName(Player whoSee) {
      return this.getName(whoSee.getName());
   }

   public String getName() {
      return this.name;
   }

   public String getSurname() {
      return this.surname;
   }

   public String getFullName() {
      return this.hasSurname() ? this.name + " " + this.surname : this.nickname;
   }

   public boolean hasSurname() {
      return this.surname != null;
   }

   public void setName(String name) {
      this.name = name;
   }
   public void setSex(String name) {
      this.sex = sex;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public String getStrangerName() {
      return Values.FRIENDS_STRANGER_NAME.replace("%index", String.valueOf(this.index));
   }

   public void setChangeNameCooldown(long cooldown) {
      this.cooldown = cooldown;
   }

   public long getChangeNameCooldown() {
      return this.cooldown;
   }

   public boolean isCooldownExpired() {
      return this.cooldown <= System.currentTimeMillis();
   }

   public Player getPlayer() {
      return Bukkit.getPlayer(this.nickname);
   }

   // Метод для вычисления возраста
   public int getYears() {
      if (this.birthday == null) {
         return -1; // Возвращаем -1, если день рождения не установлен
      }

      Calendar birthDate = Calendar.getInstance();
      birthDate.setTime(this.birthday);

      Calendar today = Calendar.getInstance();

      int years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

      // Проверяем, прошел ли день рождения в этом году
      if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
         years--;
      }

      return years;
   }
}
