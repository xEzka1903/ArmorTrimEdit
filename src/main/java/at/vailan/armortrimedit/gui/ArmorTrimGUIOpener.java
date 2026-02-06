package at.vailan.armortrimedit.gui;

import org.bukkit.entity.Player;

public class ArmorTrimGUIOpener {

    public static void open(Player player) {
        ArmorTrimGUI gui = new ArmorTrimGUI(player);
        player.openInventory(gui.getInventory());
    }

    private ArmorTrimGUIOpener() {}

}