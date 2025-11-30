package at.vailan.armortrimedit.data;

import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ArmorTrimData {
    private TrimPattern pattern;
    private TrimMaterial material;

    public void setPattern(TrimPattern p) { this.pattern = p; }
    public void setMaterial(TrimMaterial m) { this.material = m; }

    public TrimPattern getPattern() { return pattern; }
    public TrimMaterial getMaterial() { return material; }
}