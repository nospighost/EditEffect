package de.Main.Main.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeGUIPickaxe {

    public static Inventory open(Player player) {
        Inventory upgradeguipickaxe = Bukkit.createInventory(null, 27, "§cSpitzhacken Upgrades");

        ItemStack effezienz = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta effezienzMeta = effezienz.getItemMeta();
        effezienzMeta.setDisplayName("§cEffizienz Upgrade");
        effezienz.setItemMeta(effezienzMeta);
        upgradeguipickaxe.setItem(0, effezienz);

        ItemStack haltbarkeit = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta haltbarkeitMeta = haltbarkeit.getItemMeta();
        haltbarkeitMeta.setDisplayName("§cHaltbarkeit Upgrade");
        haltbarkeit.setItemMeta(haltbarkeitMeta);
        upgradeguipickaxe.setItem(1, haltbarkeit);

        ItemStack Glück = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta GlückMeta = Glück.getItemMeta();
        GlückMeta.setDisplayName("§cGlück Upgrade");
        Glück.setItemMeta(GlückMeta);
        upgradeguipickaxe.setItem(2, Glück);
        return upgradeguipickaxe;
    }
}
