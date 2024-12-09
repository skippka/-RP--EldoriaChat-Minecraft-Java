/* Decompiler 25ms, total 207ms, lines 105 */
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

public class GuiRequest extends Gui {
   private final Player sender;
   private final Profile senderProfile;
   private final Profile targetProfile;
   private boolean accepted = false;

   GuiRequest(Player target, Profile targetProfile, Player sender, Profile senderProfile) {
      super(target, Values.FRIENDS_GUI_REQUEST_TITLE, 9);
      this.sender = sender;
      this.senderProfile = senderProfile;
      this.targetProfile = targetProfile;
   }

   public void open() {
      this.inventory.setItem(0, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(1, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(2, this.createItemWithName(Material.LIME_STAINED_GLASS_PANE, Values.FRIENDS_GUI_REQUEST_ACCEPT));
      this.inventory.setItem(6, this.createItemWithName(Material.RED_STAINED_GLASS_PANE, Values.FRIENDS_GUI_REQUEST_DECLINE));
      this.inventory.setItem(7, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.inventory.setItem(8, this.createItemWithName(Material.GRAY_STAINED_GLASS_PANE, " "));
      this.player.openInventory(this.inventory);
   }

   public void onClick(InventoryClickEvent event) {
      event.setCancelled(true);
      int slot = event.getSlot();
      if (slot == 2) {
         this.accepted = true;
         this.senderProfile.getFriendList().add(this.player.getName());
         this.targetProfile.getFriendList().add(this.sender.getName());
         this.sender.sendMessage(Values.FRIENDS_REQUEST_ACCEPT_TARGET.replace("%stranger", this.targetProfile.getStrangerName()).replace("%name", this.targetProfile.getName()).replace("%displayname", this.targetProfile.getFullName()));
         this.player.sendMessage(Values.FRIENDS_REQUEST_ACCEPT_ACCEPTER.replace("%stranger", this.senderProfile.getStrangerName()).replace("%name", this.senderProfile.getName()).replace("%displayname", this.senderProfile.getFullName()));
         ProtocolLib.updateSelf(this.player, this.sender);
         ProtocolLib.updateSelf(this.sender, this.player);
         this.player.closeInventory();
      } else {
         if (slot == 6) {
            this.player.closeInventory();
         }

      }
   }

   protected boolean onClose() {
      if (!this.accepted) {
         this.sender.sendMessage(Values.FRIENDS_REQUEST_DECLINE_TARGET.replace("%stranger", this.targetProfile.getStrangerName()));
         this.player.sendMessage(Values.FRIENDS_REQUEST_DECLINE_DECLINER.replace("%stranger", this.senderProfile.getStrangerName()));
      }

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
      private Player target;
      private Profile targetProfile;
      private Player sender;
      private Profile senderProfile;

      public GuiRequest.Builder setContainer(GuiContainer container) {
         this.container = container;
         return this;
      }

      public GuiRequest.Builder setTarget(Player target, Profile profile) {
         this.target = target;
         this.targetProfile = profile;
         return this;
      }

      public GuiRequest.Builder setSender(Player sender, Profile profile) {
         this.sender = sender;
         this.senderProfile = profile;
         return this;
      }

      public GuiRequest register() {
         GuiRequest gui = new GuiRequest(this.target, this.targetProfile, this.sender, this.senderProfile);
         this.container.add(this.target, gui);
         return gui;
      }
   }
}
