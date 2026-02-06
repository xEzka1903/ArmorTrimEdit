package at.vailan.armortrimedit;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public class bStats {

    private final ArmorTrimEdit plugin;

    public bStats(ArmorTrimEdit plugin) {
        this.plugin = plugin;

        int pluginId = 18797;
        Metrics metrics = new Metrics(plugin, pluginId);

        registerCharts(metrics);
    }

    private void registerCharts(Metrics metrics) {
        metrics.addCustomChart(new SimplePie("survival_mode", () ->
                plugin.getConfig().getBoolean("survival-mode-enabled") ? "Enabled" : "Disabled"
        ));
        metrics.addCustomChart(new SimplePie("gui", () ->
                plugin.getConfig().getBoolean("gui-enabled") ? "Enabled" : "Disabled"
        ));
        metrics.addCustomChart(new SimplePie("command", () ->
                plugin.getConfig().getBoolean("command-enabled") ? "Enabled" : "Disabled"
        ));
    }
}