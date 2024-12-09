/* Decompiler 37ms, total 410ms, lines 64 */
package me.eldoriaChat.data.registration;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import me.eldoriaChat.Main;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.util.Values;

public class RegistrationProcess implements Runnable {
   private final Profile profile;
   private final RegistrationElement[] chain;
   private final BukkitTask task;
   private int stageIndex;
   private boolean finished = false;

   public RegistrationProcess(Profile profile, RegistrationElement[] chain) {
      this.profile = profile;
      this.chain = chain;
      this.task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this, Values.REGISTRATION_TITLE_UPDATE, Values.REGISTRATION_TITLE_UPDATE);
   }

   public Profile getProfile() {
      return this.profile;
   }

   public void reset() {
      this.stageIndex = 0;
      this.finished = false;
      this.nextStage();
   }

   public boolean recieve(String message) {
      return this.chain[this.stageIndex - 1].recieveMessage(this.profile, message);
   }

   public boolean isFinished() {
      return this.finished;
   }

   public boolean nextStage() {
      if (this.stageIndex != this.chain.length) {
         Player player = Bukkit.getPlayer(this.profile.getNickname());
         this.chain[this.stageIndex++].sendInformation(player);
         return true;
      } else {
         this.finished = true;
         return false;
      }
   }

   public void run() {
      if (this.stageIndex > 0) {
         Player player = Bukkit.getPlayer(this.profile.getNickname());
         this.chain[this.stageIndex - 1].sendInformation(player);
      }

   }

   public void endProcess() {
      this.task.cancel();
   }
}
