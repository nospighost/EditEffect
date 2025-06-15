package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import de.Main.editEffect.Manager.EffectCompatibility;
import org.bukkit.Location;
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

public class AutoSmeltBlock implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockType = block.getType();
        Location loc = block.getLocation();
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || !hand.hasItemMeta()) return;

        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "AutoSmeltBlock");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return;
        if (EffectCompatibility.isEffectCompatible(hand, "AutoSmeltBlock")) {
            return;
        }
        if (!blockType.toString().endsWith("_ORE") && blockType != Material.ANCIENT_DEBRIS) {
            return;
        }

        if (!blockType.toString().endsWith("_ORE") && blockType != Material.ANCIENT_DEBRIS) {
            return;
        }

        Material smeltedMaterial = null;

        if (blockType == Material.IRON_ORE || blockType == Material.DEEPSLATE_IRON_ORE) {
            smeltedMaterial = Material.IRON_BLOCK;
        } else if (blockType == Material.GOLD_ORE || blockType == Material.DEEPSLATE_GOLD_ORE) {
            smeltedMaterial = Material.GOLD_BLOCK;
        } else if (blockType == Material.COPPER_ORE || blockType == Material.DEEPSLATE_COPPER_ORE) {
            smeltedMaterial = Material.COPPER_BLOCK;
        } else if (blockType == Material.ANCIENT_DEBRIS) {
            smeltedMaterial = Material.NETHERITE_SCRAP;
        } else if(blockType == Material.COAL_ORE || blockType == Material.DEEPSLATE_COAL_ORE){
            smeltedMaterial = Material.COAL_BLOCK;
        } else if(blockType == Material.LAPIS_ORE || blockType == Material.DEEPSLATE_LAPIS_ORE){
            smeltedMaterial = Material.LAPIS_BLOCK;
        }else if(blockType == Material.EMERALD_ORE || blockType == Material.DEEPSLATE_EMERALD_ORE){
            smeltedMaterial = Material.EMERALD_BLOCK;
        }else if(blockType == Material.REDSTONE_ORE || blockType == Material.DEEPSLATE_REDSTONE_ORE){
            smeltedMaterial = Material.REDSTONE_BLOCK;
        }else if(blockType == Material.DIAMOND_ORE || blockType == Material.DEEPSLATE_DIAMOND_ORE){
            smeltedMaterial = Material.DIAMOND_BLOCK;
        }
        event.setDropItems(false);
        loc.getWorld().dropItemNaturally(loc, new ItemStack(smeltedMaterial));
    }
}

