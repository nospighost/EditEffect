package de.Main.Main.Effects;

import de.Main.Main.Main;
import de.Main.Main.Manager.EffectCompatibility;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class VeinMiner implements Listener {
    private int MAX_BLOCKS = 50;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block startBlock = event.getBlock();
        Material startMaterial = startBlock.getType();
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemMeta meta = hand.getItemMeta();
        NamespacedKey VeinMiner = new NamespacedKey(Main.getInstance(), "VeinMiner");

        if (!meta.getPersistentDataContainer().has(VeinMiner, PersistentDataType.INTEGER)) return;
        if (!EffectCompatibility.isEffectCompatible(hand, "VeinMiner")) {
            return;
        }

        if (!isOre(startMaterial)) {
            return;
        }
        event.setCancelled(true);
        veinMine(startBlock, player);
    }

    private void veinMine(Block startBlock, Player player) {
        Material targetMaterial = startBlock.getType();

        Set<Block> toBreak = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();
        queue.add(startBlock);
        toBreak.add(startBlock);

        while (!queue.isEmpty() && toBreak.size() < MAX_BLOCKS) {
            Block current = queue.poll();

            for (Block relative : getAdjacentBlocks(current)) {
                if (relative.getType() == targetMaterial && !toBreak.contains(relative)) {
                    toBreak.add(relative);
                    queue.add(relative);
                    if (toBreak.size() >= MAX_BLOCKS) break;
                }
            }
        }

        for (Block block : toBreak) {
            block.breakNaturally(player.getInventory().getItemInMainHand());
        }
    }

    private boolean isOre(Material material) {
        switch (material) {
            case DIAMOND_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case EMERALD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case NETHER_GOLD_ORE:
            case NETHER_QUARTZ_ORE:
            case DEEPSLATE_COAL_ORE:
            case DEEPSLATE_IRON_ORE:
            case DEEPSLATE_GOLD_ORE:
            case DEEPSLATE_COPPER_ORE:
            case DEEPSLATE_LAPIS_ORE:
            case DEEPSLATE_REDSTONE_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case ANCIENT_DEBRIS:
                return true;
            default:
                return false;
        }
    }

    private Block[] getAdjacentBlocks(Block block) {
        return new Block[]{
                block.getRelative(1, 0, 0),
                block.getRelative(-1, 0, 0),
                block.getRelative(0, 1, 0),
                block.getRelative(0, -1, 0),
                block.getRelative(0, 0, 1),
                block.getRelative(0, 0, -1)
        };
    }

}
