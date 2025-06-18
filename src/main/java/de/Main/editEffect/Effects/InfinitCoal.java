package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InfinitCoal implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        ItemStack fuel = event.getFuel();
        if (fuel == null || !fuel.hasItemMeta()) return;
        ItemMeta meta = fuel.getItemMeta();
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "InfinitCoal");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;
        event.setBurning(true);
        event.setBurnTime(1);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (event.getBlock().getState() instanceof Furnace furnace) {
                ItemStack currentFuel = furnace.getInventory().getFuel();
                if (currentFuel == null || currentFuel.getType().isAir() ||
                    !currentFuel.hasItemMeta() ||
                    !currentFuel.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                    ItemStack newFuel = new ItemStack(Material.COAL, 1);
                    newFuel.setItemMeta(meta);
                    furnace.getInventory().setFuel(newFuel);
                }
            }
        }, 1L);
    }
}
