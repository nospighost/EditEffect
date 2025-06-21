package de.Main.Main.Effects;

import de.Main.Main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WoodSharper implements Listener {

    private JavaPlugin plugin;

    public WoodSharper(Main plugin) {
        this.plugin = plugin;
    }


    private void stripVerticalLogs(Block block, Player player) {
        for (int i = block.getY(); i < block.getY() + 30; i++) {
            Location targetLoc = new Location(block.getWorld(), block.getX(), i, block.getZ());
            Block targetBlock = targetLoc.getBlock();
            if (!targetBlock.getType().isAir() && targetBlock.getType().toString().contains("LOG")) {
                stripLog(targetBlock, player);
            } else {
                break;
            }
        }
    }

    private void stripConnectedLogs(Block block, Player player, Set<Block> visited) {
        if (visited.contains(block) || !block.getType().toString().contains("LOG")) return;

        visited.add(block);
        stripLog(block, player);

        for (Block neighbor : getAdjacentBlocks(block)) {
            stripConnectedLogs(neighbor, player, visited);
        }
    }

    private void stripLog(Block block, Player player) {
        try {
            String blockType = block.getType().toString();
            if (!blockType.contains("STRIPPED")) {
                Material strippedType = Material.valueOf("STRIPPED_" + blockType);
                block.setType(strippedType);
                player.getWorld().playSound(block.getLocation(), Sound.BLOCK_HANGING_SIGN_WAXED_INTERACT_FAIL, 1.0f, 1.0f);
                block.getWorld().spawnParticle(Particle.GLOW, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.3, 0.3, 0.3, 0.01);
            }
        } catch (IllegalArgumentException e) {
            // Ignoriere Fehler bei ungültigen Materialtypen
        }
    }

    private Set<Block> getAdjacentBlocks(Block block) {
        Set<Block> neighbors = new HashSet<>();
        Location loc = block.getLocation();
        neighbors.add(loc.clone().add(1, 0, 0).getBlock());
        neighbors.add(loc.clone().add(-1, 0, 0).getBlock());
        neighbors.add(loc.clone().add(0, 1, 0).getBlock());
        neighbors.add(loc.clone().add(0, -1, 0).getBlock());
        neighbors.add(loc.clone().add(0, 0, 1).getBlock());
        neighbors.add(loc.clone().add(0, 0, -1).getBlock());
        return neighbors;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack hand = player.getInventory().getItemInOffHand();

        if (hand == null || !hand.hasItemMeta()) return;

        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "woodsharper");

        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;

        int value = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

        Block block = event.getClickedBlock();
        if (!block.getType().toString().contains("LOG")) return;

        event.setCancelled(true); // Verhindert das Abbauen des Blocks

        if (value == 1) {
            stripVerticalLogs(block, player);
        } else if (value == 2) {
            Set<Block> visited = new HashSet<>();
            stripConnectedLogs(block, player, visited);
        }
    }

}