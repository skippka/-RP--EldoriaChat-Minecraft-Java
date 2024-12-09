/* Decompiler 14ms, total 176ms, lines 23 */
package me.eldoriaChat.data.registration;

import me.eldoriaChat.util.Container;

public class RegistrationProcessor extends Container<String, RegistrationProcess> {
   public void add(RegistrationProcess process) {
      this.add(process.getProfile().getNickname(), process);
   }

   public boolean killProcess(String key) {
      if (this.contains(key)) {
         ((RegistrationProcess)this.remove(key)).endProcess();
         return true;
      } else {
         return false;
      }
   }

   public boolean process(RegistrationProcess process, String message) {
      return process.isFinished() || process.recieve(message) && !process.nextStage();
   }
}
