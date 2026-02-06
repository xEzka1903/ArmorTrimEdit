package at.vailan.armortrimedit;

import at.vailan.armortrimedit.commands.CommandRegistration;
import at.vailan.armortrimedit.listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorTrimEdit extends JavaPlugin {

    private static ArmorTrimEdit plugin;

    public static ArmorTrimEdit getInstance() { return plugin; }

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        new bStats(this);

        new CommandRegistration(this).registerCommands();

        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    public String getPrefix() {
        return getConfig().getString("prefix", "§8[§bArmorTrimEdit§8] ");
    }

    public String getMessage(String key) {
        String msg = getConfig().getString("messages." + key);
        if (msg == null) msg = "§cMissing message: " + key;
        return getPrefix() + msg;
    }

}