/* Decompiler 44ms, total 232ms, lines 92 */
package me.eldoriaChat.data.gui.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.eldoriaChat.data.gui.Gui;
import me.eldoriaChat.data.gui.GuiContainer;
import me.eldoriaChat.data.profile.Profile;
import me.eldoriaChat.depend.ProtocolLib;
import me.eldoriaChat.util.Values;

public class GuiFriend extends Gui {
   private final Player target;
   private final Profile targetProfile;
   private final Profile playerProfile;

   public GuiFriend(Player player, Profile playerProfile, Player target, Profile targetProfile) {
      super(player, Values.FRIENDS_GUI_FRIEND_TITLE.replace("%displayname", targetProfile.getFullName()).replace("%name", target.getName()), 9);
      this.target = target;
      this.targetProfile = targetProfile;
      this.playerProfile = playerProfile;
   }

   public void open() {
      this.inventory.setItem(0, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(1, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(4, this.createItemWithName(Material.RED_STAINED_GLASS_PANE, Values.FRIENDS_GUI_FRIEND_REMOVE));
      this.inventory.setItem(7, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(8, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.player.openInventory(this.inventory);
   }

   public void onClick(InventoryClickEvent event) {
      event.setCancelled(true);
      if (event.getSlot() == 4) {
         this.playerProfile.getFriendList().remove(this.target.getName());
         this.targetProfile.getFriendList().remove(this.player.getName());
         this.player.sendMessage(Values.FRIENDS_REMOVE_REMOVER.replace("%stranger", this.targetProfile.getStrangerName()).replace("%name", this.targetProfile.getName()).replace("%displayname", this.targetProfile.getFullName()));
         this.target.sendMessage(Values.FRIENDS_REMOVE_TARGET.replace("%stranger", this.playerProfile.getStrangerName()).replace("%name", this.playerProfile.getName()).replace("%displayname", this.playerProfile.getFullName()));
         ProtocolLib.updateSelf(this.player, this.target);
         ProtocolLib.updateSelf(this.target, this.player);
         this.player.closeInventory();
      }

   }

   protected boolean onClose() {
      return true;
   }

   private ItemStack createItemWithName(Material material, String name) {
      ItemStack itemStack = new ItemStack(material);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.setDisplayName(name);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static class Builder {
      private GuiContainer container;
      private Player player;
      private Profile playerProfile;
      private Player target;
      private Profile targetProfile;

      public GuiFriend.Builder setContainer(GuiContainer container) {
         this.container = container;
         return this;
      }

      public GuiFriend.Builder setPlayer(Player player, Profile profile) {
         this.player = player;
         this.playerProfile = profile;
         return this;
      }

      public GuiFriend.Builder setTarget(Player target, Profile profile) {
         this.target = target;
         this.targetProfile = profile;
         return this;
      }

      public GuiFriend register() {
         GuiFriend gui = new GuiFriend(this.player, this.playerProfile, this.target, this.targetProfile);
         this.container.add(this.player, gui);
         return gui;
      }
   }
}
