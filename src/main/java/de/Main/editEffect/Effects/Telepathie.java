package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class Telepathie implements Listener {
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        World world = item.getWorld();

        MetadataValue metadataValue = new FixedMetadataValue(Main.getInstance(), true);
        item.setMetadata("droppedByPlayer", metadataValue);

        world.getNearbyEntities(item.getLocation(), 7, 7, 7).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(player -> {
                    ItemStack handItem = player.getInventory().getItemInMainHand();
                    if (handItem == null || !handItem.hasItemMeta()) return;

                    ItemMeta meta = handItem.getItemMeta();
                    NamespacedKey key = new NamespacedKey(Main.getInstance(), "Telepathie");

                    if (meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                        player.getInventory().addItem(item.getItemStack());
                        item.remove();
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.0f);
                    }
                });
    }

}
