package de.Main.Main.Effects;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import de.Main.Main.Main;
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

import com.plotsquared.core.location.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlotPicke implements Listener {
    private final JavaPlugin plugin;
    private final @NonNull PlotSquared plotAPI = PlotSquared.get();

    public PlotPicke(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        Block centerBlock = event.getBlock();

        if (hand == null || hand.getType() == Material.AIR || !hand.hasItemMeta()) return;

        ItemMeta meta = hand.getItemMeta();
        if (!(player.getWorld().getName().contains("CityBuild"))) {
            return;
        } //Wenn es nicht in der CityBuild Welt ist, dann abbrechen
        NamespacedKey key = new NamespacedKey(plugin, "plotPicke"); // Key auf "bohrer" geändert
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;
        int bohrerLevel = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        if (bohrerLevel < 1) return;
        boolean effectApplied = applyBohrerEffect(player, hand, centerBlock, bohrerLevel);

        if (effectApplied) {
            event.setCancelled(true);
        }
    }

    private boolean applyBohrerEffect(Player player, ItemStack tool, Block centerBlock, int bohrerLevel) {
        if (bohrerLevel < 1) return false;

        List<String> whitelist = plugin.getConfig().getStringList("BohrerWhiteList");
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
                        if (isBlockInPlayerPlot(player, block) && whitelist.contains(block.getType().toString())) {
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
                            if (isBlockInPlayerPlot(player, block) && whitelist.contains(block.getType().toString())) {
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
                            if (isBlockInPlayerPlot(player, block) && whitelist.contains(block.getType().toString())) {
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
        List<String> whitelist = plugin.getConfig().getStringList("BohrerWhiteList");
        if (!whitelist.contains(block.getType().toString())) {
            return false;
        }
        block.breakNaturally(tool);
        return true;
    }

    private boolean isBlockInPlayerPlot(Player player, Block block) {
        Location plotLocation = Location.at(
                block.getWorld().getName(),
                block.getX(),
                block.getY(),
                block.getZ()
        );

        var plotArea = plotAPI.getPlotAreaManager().getPlotArea(plotLocation);

        if (plotArea == null) {
            return false;
        }

        Plot plot = plotArea.getPlot(plotLocation);

        if (plot == null) {
            return false;
        }

        boolean isOwner = plot.getOwners().contains(player.getUniqueId());
        boolean isMember = plot.getMembers().contains(player.getUniqueId());

        if (!isOwner && !isMember) {
        }

        return isOwner || isMember;
    }

}

