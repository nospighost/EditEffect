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

    public EditEffectCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Default
    public void oncommand(Player player, String[] args) {

    }

    @Subcommand("Bohrer 1")
    @CommandPermission("be.ce.Bohrer.1")
    public void onBohrer1(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "bohrer");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);

            player.sendMessage("§aBohrer Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl (1 oder 2) sein!");
        }

    }

    @Subcommand("Bohrer 2")
    @CommandPermission("be.ce.Bohrer.2")
    public void onBohrer2(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "bohrer");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
            hand.setItemMeta(meta);
            player.sendMessage("§aBohrer Modus " + 2 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl (1 oder 2) sein!");
        }
    }

    @Subcommand("WoodCutter 1")
    @CommandPermission("be.ce.WoodCutter.1")
    public void onWoodCutter1(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "woodcutter");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aWoodCutter Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl (1 oder 2) sein!");
        }
    }

    @Subcommand("WoodCutter 2")
    @CommandPermission("be.ce.WoodCutter.2")
    public void onWoodCutter2(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "woodcutter");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
            hand.setItemMeta(meta);
            player.sendMessage("§aWoodCutter Modus " + 2 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl (1 oder 2) sein!");
        }
    }

    @Subcommand("AutoSmeltIngot")
    @CommandPermission("be.ce.AutoSmeltIngot")
    public void onAutoSmeltIngot(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "AutoSmeltIngot");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aAutoSmeltIngot Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl 1 sein!");
        }
    }

    @Subcommand("AutoSmeltBlock")
    @CommandPermission("be.ce.AutoSmeltBlock")
    public void onAutoSmeltBlock(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "AutoSmeltBlock");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aAutoSmeltBlock Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl 1 sein!");
        }
    }

    @Subcommand("VeinMiner")
    @CommandPermission("be.ce.VeinMiner")
    public void onVeinMiner(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "VeinMiner");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aVeinMiner Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl 1 sein!");
        }
    }
    @Subcommand("UnendlicheRakete")
    @CommandPermission("be.ce.UnendlicheRakete")
    public void onUnendlicheRakete(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "InfinitRocket");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aUnendlicheRakete Modus " + 1 + " erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl 1 sein!");
        }
    }

    @Subcommand("UnendlicheEnderPerle")
    @CommandPermission("be.ce.InfinitEnderPeal")
    public void onUnendlicheEnderPerle(Player player, String[] args) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) {
            player.sendMessage("Du musst ein gültiges Item in der Hand halten, um diesen Befehl zu nutzen!");
        }
        try {
            ItemMeta meta = hand.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "InfinitEnderPeal");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            hand.setItemMeta(meta);
            player.sendMessage("§aUnendlicheEnderPerle Modus erfolgreich gesetzt!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cDer Modus muss eine Zahl 1 sein!");
        }
    }

}
