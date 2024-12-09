/* Decompiler 10ms, total 177ms, lines 25 */
package me.eldoriaChat.util;

import java.util.HashMap;
import java.util.Map;

public class Container<K, V> {
   protected final Map<K, V> map = new HashMap();

   public V get(K key) {
      return this.map.get(key);
   }

   public void add(K key, V value) {
      this.map.put(key, value);
   }

   public boolean contains(K key) {
      return this.map.containsKey(key);
   }

   public V remove(K key) {
      return this.map.remove(key);
   }
}
