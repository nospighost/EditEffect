package de.Main.Main;

import co.aikar.commands.PaperCommandManager;
import de.Main.Main.Effects.*;
import de.Main.Main.commands.EditEffectCommand;
import de.Main.Main.commands.OpenGUICommand;
import de.Main.Main.listener.ClickListenerAxt;
import de.Main.Main.listener.ClickListenerSpitzhacken;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    public static Economy eco;
    public static String prefix = "§b§lEditEffect §8» §7";

    @Override
    public void onEnable() {
        instance = this;
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new EditEffectCommand(this));
        manager.registerCommand(new OpenGUICommand());
        if (!setupEconomy()) {
            getLogger().severe("Vault nicht gefunden oder keine Economy verfügbar!.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new Bohrer(this), this);
        Bukkit.getPluginManager().registerEvents(new PlotPicke(this), this);
        Bukkit.getPluginManager().registerEvents(new WoodCutter(this), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmeltIngot(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmeltBlock(), this);
        Bukkit.getPluginManager().registerEvents(new VeinMiner(), this);
        Bukkit.getPluginManager().registerEvents(new InfinitRocket(), this);
        Bukkit.getPluginManager().registerEvents(new InfinitEnderPearl(), this);
        Bukkit.getPluginManager().registerEvents(new Telepathie(), this);
        Bukkit.getPluginManager().registerEvents(new InfinitCoal(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListenerAxt(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListenerSpitzhacken(), this);

        getLogger().info("EditEfect erfolgreich gestartet.");
    }

    @Override
    public void onDisable() {
        getLogger().info("EditEffect Plugin deaktiviert");
    }

    public static Main getInstance() {
        return instance;
    }


    public static FileConfiguration getPluginConfig() {
        return instance.getConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault ist nicht installiert!");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer()
                .getServicesManager()
                .getRegistration(Economy.class);

        if (rsp == null) {
            getLogger().severe("Keine Economy-Implementierung gefunden! Bitte stelle sicher, dass ein Economy-Plugin wie EssentialsX Economy installiert ist.");
            return false;
        }

        eco = rsp.getProvider();
        getLogger().info("Vault Economy erfolgreich eingerichtet!");
        return eco != null;
    }

    public static Economy getEconomy() {
        return eco;
    }
}
