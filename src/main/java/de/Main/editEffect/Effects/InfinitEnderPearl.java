package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InfinitEnderPearl implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack handitem = player.getInventory().getItemInMainHand();

        if (handitem == null || handitem.getType() != Material.ENDER_PEARL) return;

        ItemMeta meta = handitem.getItemMeta();
        if (meta == null) return;

        NamespacedKey key = new NamespacedKey(Main.getInstance(), "InfinitEnderPeal");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (handitem.getAmount() == 0) {
                ItemStack newRocket = new ItemStack(Material.ENDER_PEARL, 1);
                newRocket.setItemMeta(meta);
                player.getInventory().setItemInMainHand(newRocket);
            } else {
                handitem.setAmount(handitem.getAmount() + 1);
                handitem.setAmount(handitem.getAmount() - 1);
            }
        }, 1L);
    }
}
