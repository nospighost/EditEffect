package de.Main.editEffect.listener;

import de.Main.editEffect.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListenerSpitzhacken implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§cSpitzhacken Upgrades")) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        ItemStack pickaxe = player.getInventory().getItemInMainHand();

        if (pickaxe == null || !isPickaxe(pickaxe.getType())) {
            player.sendMessage("§cHalte eine Spitzhacke in der Hand!");
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.ENCHANTED_BOOK) {
            int currentLevel = pickaxe.getEnchantmentLevel(Enchantment.EFFICIENCY);
            int minLevel = 5;
            int maxLevel = 12;
            int basePrice = 75000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Effizienz " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Spitzhacke hat bereits Effizienz " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, pickaxe, Enchantment.EFFICIENCY, currentLevel, cost, maxLevel, "Effizienz");
            }
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.NETHERITE_INGOT) {
            int currentLevel = pickaxe.getEnchantmentLevel(Enchantment.UNBREAKING);
            int minLevel = 3;
            int maxLevel = 10;
            int basePrice = 50000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Haltbarkeit " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Spitzhacke hat bereits Haltbarkeit " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, pickaxe, Enchantment.UNBREAKING, currentLevel, cost, maxLevel, "Haltbarkeit");
            }
            player.closeInventory();
            return;
        }

        if (clicked.getType() == Material.TOTEM_OF_UNDYING) {
            int currentLevel = pickaxe.getEnchantmentLevel(Enchantment.FORTUNE);
            int minLevel = 3;
            int maxLevel = 5;
            int basePrice = 50000;

            if (currentLevel < minLevel) {
                player.sendMessage("§cDu benötigst mindestens Glück " + minLevel + " zum Upgraden!");
            } else if (currentLevel >= maxLevel) {
                player.sendMessage("§cDeine Spitzhacke hat bereits Glück " + maxLevel + "!");
            } else {
                int cost = basePrice * (int) Math.pow(2, currentLevel - minLevel);
                handleUpgrade(player, pickaxe, Enchantment.FORTUNE, currentLevel, cost, maxLevel, "Glück");
            }
            player.closeInventory();
        }
    }

    private boolean isPickaxe(Material type) {
        return type == Material.WOODEN_PICKAXE ||
                type == Material.STONE_PICKAXE ||
                type == Material.IRON_PICKAXE ||
                type == Material.DIAMOND_PICKAXE ||
                type == Material.NETHERITE_PICKAXE;
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
