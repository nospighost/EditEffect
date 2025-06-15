package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import de.Main.editEffect.Manager.EffectCompatibility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class Bohrer implements Listener {
    private final JavaPlugin plugin;
    public Bohrer(Main plugin) {
        this.plugin = plugin;

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        Block centerBlock = event.getBlock();

        if (hand == null || hand.getType() == Material.AIR || !hand.hasItemMeta()) return;

        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "bohrer");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;
        if (!EffectCompatibility.isEffectCompatible(hand, "bohrer")) {
            return;
        }
        int bohrerLevel = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        if (bohrerLevel < 1) return;

        boolean effectApplied = applyBohrerEffect(player, hand, centerBlock, bohrerLevel);

        if (effectApplied) {

            event.setCancelled(true);
        } else {
            // Kein Bohrer-Effekt, normaler Abbau erlauben (Event nicht cancelled)
        }
    }





    private boolean applyBohrerEffect(Player player, ItemStack tool, Block centerBlock, int bohrerLevel) {
        if (bohrerLevel < 1) return false;

        List<String> whitelist = plugin.getConfig().getStringList("BohrerWhiteList");

        // Wenn der zentrale Block nicht auf der Whitelist ist, dann Bohrer-Effekt nicht ausführen
        if (!whitelist.contains(centerBlock.getType().toString())) {
            return false;
        }

        int size;
        switch (bohrerLevel) {
            case 1:
                size = 3;
                break;
            case 2:
                size = 5;
                centerBlock = centerBlock.getRelative(0, 1, 0);
                break;
            case 3:
                size = 5;
                break;
            default:
                size = 3;
        }

        int radius = (size - 1) / 2;
        Vector direction = player.getLocation().getDirection();
        boolean isVertical = Math.abs(direction.getY()) > 0.5;

        boolean effectApplied = false;

        if (isVertical) {
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block block = centerBlock.getRelative(x, y, z);
                        if (whitelist.contains(block.getType().toString())) {
                            if (processBlock(player, tool, block)) {
                                effectApplied = true;
                            }
                        }
                    }
                }
            }
        } else {
            switch (player.getFacing()) {
                case NORTH:
                case SOUTH:
                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            Block block = centerBlock.getRelative(x, y, 0);
                            if (whitelist.contains(block.getType().toString())) {
                                if (processBlock(player, tool, block)) {
                                    effectApplied = true;
                                }
                            }
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    for (int z = -radius; z <= radius; z++) {
                        for (int y = -radius; y <= radius; y++) {
                            Block block = centerBlock.getRelative(0, y, z);
                            if (whitelist.contains(block.getType().toString())) {
                                if (processBlock(player, tool, block)) {
                                    effectApplied = true;
                                }
                            }
                        }
                    }
                    break;
            }
        }

        return effectApplied;
    }




    private boolean processBlock(Player player, ItemStack tool, Block block) {
        if (block.getType().isAir()) return false;

        // Whitelist aus der Config abrufen
       List<String> whitelist = plugin.getConfig().getStringList("BohrerWhiteList");

       // Überprüfen, ob der Blocktyp in der Whitelist enthalten ist
       if (!whitelist.contains(block.getType().toString())) {
           return false;
       }

        block.breakNaturally(tool);

        return true;
    }

}
