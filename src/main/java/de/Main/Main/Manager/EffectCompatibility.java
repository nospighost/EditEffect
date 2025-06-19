package de.Main.Main.Manager;

import de.Main.Main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class EffectCompatibility {
    private static final Map<String, String[]> incompatibilities = new HashMap<>();

    static {
        incompatibilities.put("VeinMiner", new String[]{"AutoSmeltIngot", "AutoSmeltBlock"});
        incompatibilities.put("AutoSmeltIngot", new String[]{"VeinMiner", "AutoSmeltBlock"});
        incompatibilities.put("AutoSmeltBlock",  new String[]{"VeinMiner", "AutoSmeltIngot", "AutoSmeltBlock", "bohrer"});
        incompatibilities.put("bohrer", new String[]{"AutoSmeltBlock"});
    }

    public static boolean isEffectCompatible(ItemStack hand, String effectName) {
        if (hand == null || !hand.hasItemMeta()) return false;
        ItemMeta meta = hand.getItemMeta();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), effectName), PersistentDataType.INTEGER)) {
            return false;
        }
        String[] incompatibleEffects = incompatibilities.get(effectName);
        if (incompatibleEffects == null) return true;

        for (String incompatible : incompatibleEffects) {
            NamespacedKey key = new NamespacedKey(Main.getInstance(), incompatible);
            if (meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                return false;
            }
        }

        return true;
    }
}
