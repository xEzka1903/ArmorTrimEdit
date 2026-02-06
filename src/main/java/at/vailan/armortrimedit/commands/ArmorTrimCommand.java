package at.vailan.armortrimedit.commands;

import at.vailan.armortrimedit.Permissions;
import at.vailan.armortrimedit.gui.ArmorTrimGUIOpener;
import at.vailan.minecraftutils.commands.ArgsParser;
import at.vailan.minecraftutils.commands.SubCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.util.*;

import static at.vailan.armortrimedit.ArmorTrimEdit.getInstance;

public class ArmorTrimCommand extends SubCommand {

    @Override
    public boolean requiresPlayer() { return true; }

    @Override
    public boolean isVisible(CommandSender sender) {
        return hasRequiredPermission(sender);
    }

    @Override
    public boolean isAvailable(CommandSender sender) {
        return hasRequiredPermission(sender);
    }

    @Override
    public Collection<String> getRequiredPermissions() {
        return List.of(Permissions.APPLY, Permissions.REMOVE);
    }

    @Override
    public String getUsage() {
        return "/ae";
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String commandString, ArgsParser args) {

        if (!(sender instanceof Player p)) { return true; }

        ItemStack item = p.getInventory().getItemInMainHand();

        if (getInstance().getConfig().getBoolean("gui-enabled")) {

            if (!isArmor(item)) {
                p.sendMessage(getInstance().getMessage("not-holding-armor"));
                return true;
            }

            ArmorTrimGUIOpener.open(p);
        }

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