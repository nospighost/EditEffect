package de.Main.Main.listener;

import de.Main.Main.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListenerAxt implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§cAxt Upgrades")) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        ItemStack Axe = player.getInventory().getItemInMainHand();

        if (Axe == null || !isAxe(Axe.getType())) {
            player.sendMessage("§cHalte eine Axt in der Hand!");
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.ENCHANTED_BOOK) {
            int currentLevel = Axe.getEnchantmentLevel(Enchantment.EFFICIENCY);
            int minLevel = 5;
            int maxLevel = 10;
            int basePrice = 75000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Effizienz " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Axt hat bereits Effizienz " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, Axe, Enchantment.EFFICIENCY, currentLevel, cost, maxLevel, "Effizienz");
            }
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.NETHERITE_INGOT) {
            int currentLevel = Axe.getEnchantmentLevel(Enchantment.UNBREAKING);
            int minLevel = 3;
            int maxLevel = 10;
            int basePrice = 50000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Haltbarkeit " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Axt hat bereits Haltbarkeit " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, Axe, Enchantment.UNBREAKING, currentLevel, cost, maxLevel, "Haltbarkeit");
            }
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.IRON_SWORD) {
            int currentLevel = Axe.getEnchantmentLevel(Enchantment.SHARPNESS);
            int minLevel = 3;
            int maxLevel = 5;
            int basePrice = 50000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Schärfe " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Axt hat bereits Schärfe " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, Axe, Enchantment.SHARPNESS, currentLevel, cost, maxLevel, "Schärfe");
            }
            player.closeInventory();
        }
    }

    private boolean isAxe(Material type) {
        return type == Material.WOODEN_AXE ||
                type == Material.STONE_AXE ||
                type == Material.IRON_AXE ||
                type == Material.DIAMOND_AXE ||
                type == Material.NETHERITE_AXE;
    }

    private void handleUpgrade(Player player, ItemStack pickaxe, Enchantment enchant, int currentLevel, int cost, int maxLevel, String name) {
        if (Main.getEconomy().getBalance(player) >= cost) {
            Main.getEconomy().withdrawPlayer(player, cost);
            pickaxe.addUnsafeEnchantment(enchant, currentLevel + 1);
            player.sendMessage("§a" + name + " wurde auf Level " + (currentLevel + 1) + " erhöht! §7(-" + cost + "$)");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            player.sendMessage("§cDu hast nicht genug Geld! §7Benötigt: " + cost + "$");
        }
    }
}
