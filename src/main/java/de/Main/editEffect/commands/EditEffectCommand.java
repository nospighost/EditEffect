package de.Main.editEffect.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

@CommandAlias("ce")
@CommandPermission("be.ce")
public class EditEffectCommand extends BaseCommand {

    private JavaPlugin plugin;
    public static String prefix = "§b§lBlockEngine §8» §7";
    public EditEffectCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Default
    public void oncommand(Player player, String[] args) {
        player.sendMessage(prefix + "§7/ce WoodCutter <1/2> - Baut Stämme/Bäume mit einem Schlag ab und lässt Setzlinge mit rechtsklick Wachsen!");
        player.sendMessage(prefix + "§7/ce Bohrer <1/2> - Baut 3x3 oder 5x5 Blöcke mit einem Schlag ab!");
        player.sendMessage(prefix + "§7/ce AutoSmeltIngot - Schmilzt Erze zu Barren beim abbauen!");
        player.sendMessage(prefix + "§7/ce AutoSmeltBlock - Schmilzt Erze zu Blöcken beim abbauen!");
        player.sendMessage(prefix + "§7/ce VeinMiner - Baut alle Blöcke der selben Art in einem Umkreis von 3x3 ab!");
        player.sendMessage(prefix + "§7/ce UnendlicheRakete - Erlaubt es dir Raketen unendlich zu nutzen!");
        player.sendMessage(prefix + "§7/ce UnendlicheEnderPerle - Erlaubt es dir Enderperlen unendlich zu nutzen!");
        player.sendMessage(prefix + "§7/ce UnendlicheKohle - Eine Unendliche Kohle für den Ofen!");
        player.sendMessage(prefix + "§7/ce Telepathie - Lässt Items die du abbaust direkt in dein Inventar wandern!");
    }

    private void addEffekt(Player player, String effekt, int stufe) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage(prefix + "Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
            return;
        }
        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, effekt);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, stufe);
        hand.setItemMeta(meta);
        player.sendMessage(prefix + "§aEffekt '" + effekt + "' (Stufe " + stufe + ") hinzugefügt!");
    }

    private void removeEffekt(Player player, String effekt) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage(prefix + "Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
            return;
        }
        ItemMeta meta = hand.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, effekt);
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
            meta.getPersistentDataContainer().remove(key);
            hand.setItemMeta(meta);
            player.sendMessage(prefix + "§cEffekt '" + effekt + "' entfernt!");
        } else {
            player.sendMessage(prefix + "§7Effekt '" + effekt + "' ist nicht auf diesem Item.");
        }
    }

    private void listEffekte(Player player) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage(prefix + "Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
            return;
        }
        ItemMeta meta = hand.getItemMeta();
        if (meta == null) {
            player.sendMessage(prefix + "Keine Effekte auf diesem Item.");
            return;
        }
        var container = meta.getPersistentDataContainer();
        StringBuilder sb = new StringBuilder(prefix + "§7Effekte auf diesem Item: ");
        boolean found = false;
        for (NamespacedKey key : container.getKeys()) {
            Integer stufe = container.get(key, PersistentDataType.INTEGER);
            if (stufe != null) {
                sb.append("§a").append(key.getKey()).append("§7[§e").append(stufe).append("§7], ");
                found = true;
            }
        }
        if (found) {
            player.sendMessage(sb.substring(0, sb.length() - 2));
        } else {
            player.sendMessage(prefix + "Keine Effekte auf diesem Item.");
        }
    }

    // --- Module für alle Effekte ---

    @Subcommand("add bohrer 1")
    @CommandPermission("be.ce.Bohrer.1")
    public void addBohrer1(Player player) {
        addEffekt(player, "bohrer", 1);
    }

    @Subcommand("add bohrer 2")
    @CommandPermission("be.ce.Bohrer.2")
    public void addBohrer2(Player player) {
        addEffekt(player, "bohrer", 2);
    }

    @Subcommand("remove bohrer")
    @CommandPermission("be.ce.Bohrer")
    public void removeBohrer(Player player) {
        removeEffekt(player, "bohrer");
    }

    @Subcommand("add woodcutter 1")
    @CommandPermission("be.ce.WoodCutter.1")
    public void addWoodCutter1(Player player) {
        addEffekt(player, "woodcutter", 1);
    }

    @Subcommand("add woodcutter 2")
    @CommandPermission("be.ce.WoodCutter.2")
    public void addWoodCutter2(Player player) {
        addEffekt(player, "woodcutter", 2);
    }

    @Subcommand("remove woodcutter")
    @CommandPermission("be.ce.WoodCutter")
    public void removeWoodCutter(Player player) {
        removeEffekt(player, "woodcutter");
    }

    @Subcommand("add autosmeltIngot 1")
    @CommandPermission("be.ce.AutoSmeltIngot")
    public void addAutoSmeltIngot(Player player) {
        addEffekt(player, "AutoSmeltIngot", 1);
    }

    @Subcommand("remove autosmeltIngot")
    @CommandPermission("be.ce.AutoSmeltIngot")
    public void removeAutoSmeltIngot(Player player) {
        removeEffekt(player, "AutoSmeltIngot");
    }

    @Subcommand("add autosmeltBlock 1")
    @CommandPermission("be.ce.AutoSmeltBlock")
    public void addAutoSmeltBlock(Player player) {
        addEffekt(player, "AutoSmeltBlock", 1);
    }

    @Subcommand("remove autosmeltBlock")
    @CommandPermission("be.ce.AutoSmeltBlock")
    public void removeAutoSmeltBlock(Player player) {
        removeEffekt(player, "AutoSmeltBlock");
    }

    @Subcommand("add veinMiner 1")
    @CommandPermission("be.ce.VeinMiner")
    public void addVeinMiner(Player player) {
        addEffekt(player, "VeinMiner", 1);
    }

    @Subcommand("remove veinMiner")
    @CommandPermission("be.ce.VeinMiner")
    public void removeVeinMiner(Player player) {
        removeEffekt(player, "VeinMiner");
    }

    @Subcommand("add UnendlicheRakete 1")
    @CommandPermission("be.ce.UnendlicheRakete")
    public void addUnendlicheRakete(Player player) {
        addEffekt(player, "InfinitRocket", 1);
    }

    @Subcommand("remove UnendlicheRakete")
    @CommandPermission("be.ce.UnendlicheRakete")
    public void removeUnendlicheRakete(Player player) {
        removeEffekt(player, "InfinitRocket");
    }

    @Subcommand("add InfinitEnderPeal 1")
    @CommandPermission("be.ce.InfinitEnderPeal")
    public void addUnendlicheEnderPerle(Player player) {
        addEffekt(player, "InfinitEnderPeal", 1);
    }

    @Subcommand("remove InfinitEnderPeal")
    @CommandPermission("be.ce.InfinitEnderPeal")
    public void removeUnendlicheEnderPerle(Player player) {
        removeEffekt(player, "InfinitEnderPeal");
    }

    @Subcommand("add telepathie 1")
    @CommandPermission("be.ce.Telepathie")
    public void addTelepathie(Player player) {
        addEffekt(player, "Telepathie", 1);
    }

    @Subcommand("remove telepathie")
    @CommandPermission("be.ce.Telepathie")
    public void removeTelepathie(Player player) {
        removeEffekt(player, "Telepathie");
    }

    @Subcommand("add UnendlicheKohle 1")
    @CommandPermission("be.ce.UnendlicheKohle")
    public void addUnendlicheKohle(Player player) {
        addEffekt(player, "InfinitCoal", 1);
    }

    @Subcommand("remove UnendlicheKohle")
    @CommandPermission("be.ce.UnendlicheKohle")
    public void removeUnendlicheKohle(Player player) {
        removeEffekt(player, "InfinitCoal");
    }

    @Subcommand("list")
    @CommandPermission("be.ce")
    public void listAll(Player player) {
        listEffekte(player);
    }
}
