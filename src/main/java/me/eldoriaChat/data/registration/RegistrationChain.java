/* Decompiler 4ms, total 161ms, lines 16 */
package me.eldoriaChat.data.registration;

import java.util.Arrays;

public class RegistrationChain {
   private final RegistrationElement[] elements;

   public RegistrationChain(RegistrationElement... elements) {
      this.elements = elements;
   }

   public RegistrationElement[] getElements() {
      return (RegistrationElement[])Arrays.copyOf(this.elements, this.elements.length);
   }
}
