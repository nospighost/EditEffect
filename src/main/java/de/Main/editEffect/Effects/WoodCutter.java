package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
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

import java.util.HashSet;
import java.util.Set;

public class WoodCutter implements Listener {

    private JavaPlugin plugin;

    public WoodCutter(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand == null || !hand.hasItemMeta()) return;

        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "woodcutter");

        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;

        int value = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

        Block block = event.getBlock();
        if (!block.getType().toString().contains("LOG")) return;

        if (value == 1) {

            for (int i = block.getY() + 1; i < block.getY() + 30; i++) {
                Location targetLoc = new Location(block.getWorld(), block.getX(), i, block.getZ());
                Block targetBlock = targetLoc.getBlock();
                if (!targetBlock.getType().isAir() && targetBlock.getType().toString().contains("LOG")) {
                    targetBlock.breakNaturally(hand);
                    player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2f, 1f);
                    targetBlock.getWorld().spawnParticle(Particle.FLAME, targetBlock.getLocation().add(0.5, 0.5, 0.5), 5, 0.3, 0.3, 0.3, 0.01);
                } else {
                    break;
                }
            }
        } else if (value == 2) {

            Set<Block> visited = new HashSet<>();
            breakConnectedBlocks(block, hand, player, visited);
        }
    }

    private void breakConnectedBlocks(Block block, ItemStack hand, Player player, Set<Block> visited) {
        if (visited.contains(block) || !block.getType().toString().contains("LOG")) return;

        visited.add(block);
        block.breakNaturally(hand);
        player.playSound(player.getLocation(), Sound.BLOCK_SPONGE_BREAK, 1f, 1f);
        block.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.3, 0.3, 0.3, 0.01);

        for (Block neighbor : getAdjacentBlocks(block)) {
            breakConnectedBlocks(neighbor, hand, player, visited);
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


}
