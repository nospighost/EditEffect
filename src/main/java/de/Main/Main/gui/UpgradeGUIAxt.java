package de.Main.Main.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeGUIAxt {

    public static Inventory open(Player player) {
        Inventory upgradeguiaxt = Bukkit.createInventory(null, 27, "§cAxt Upgrades");

        ItemStack effezienz = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta effezienzMeta = effezienz.getItemMeta();
        effezienzMeta.setDisplayName("§cEffizienz Upgrade");
        effezienz.setItemMeta(effezienzMeta);
        upgradeguiaxt.setItem(0, effezienz);

        ItemStack haltbarkeit = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta haltbarkeitMeta = haltbarkeit.getItemMeta();
        haltbarkeitMeta.setDisplayName("§cHaltbarkeit Upgrade");
        haltbarkeit.setItemMeta(haltbarkeitMeta);
        upgradeguiaxt.setItem(1, haltbarkeit);

        ItemStack Sharpness = new ItemStack(Material.IRON_SWORD);
        ItemMeta SharpnessMeta = Sharpness.getItemMeta();
        SharpnessMeta.setDisplayName("§cSharpness Upgrade");
        Sharpness.setItemMeta(SharpnessMeta);
        upgradeguiaxt.setItem(2, Sharpness);




        return upgradeguiaxt;
    }
}
