package de.Main.Main.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;

import de.Main.Main.gui.UpgradeGUIAxt;
import de.Main.Main.gui.UpgradeGUIPickaxe;
import org.bukkit.entity.Player;

@CommandAlias("upgrades")
@CommandPermission("be.allow.upgrades")
public class OpenGUICommand extends BaseCommand {

    @Default
    @Subcommand("Spitzhacke")
    public void onCommandPickaxe(Player player) {
        player.openInventory(UpgradeGUIPickaxe.open(player));
    }
    @Default
    @Subcommand("Axt")
    public void onCommandAxt(Player player) {
        player.openInventory(UpgradeGUIAxt.open(player));
    }
}
