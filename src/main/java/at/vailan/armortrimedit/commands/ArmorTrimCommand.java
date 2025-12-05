package at.vailan.armortrimedit.commands;

import at.vailan.armortrimedit.Permissions;
import at.vailan.armortrimedit.gui.ArmorTrimGUIOpener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import static at.vailan.armortrimedit.ArmorTrimEdit.getInstance;

public class ArmorTrimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        if (!(p.hasPermission(Permissions.USE)
                || p.hasPermission(Permissions.APPLY)
                || p.hasPermission(Permissions.REMOVE))) {
            p.sendMessage(getInstance().getMessage("no-permission"));
            return true;
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        if (!isArmor(item)) {
            p.sendMessage(getInstance().getMessage("not-holding-armor"));
            return true;
        }

        ArmorTrimGUIOpener.open(p);
        return true;
    }

    public static boolean isArmor(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;

        Material m = item.getType();

        if (m == Material.TURTLE_HELMET) return false;

        if (m.name().endsWith("_HELMET")) return true;
        if (m.name().endsWith("_CHESTPLATE")) return true;
        if (m.name().endsWith("_LEGGINGS")) return true;
        if (m.name().endsWith("_BOOTS")) return true;

        return false;

    }

}