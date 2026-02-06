package at.vailan.armortrimedit.commands;

import at.vailan.armortrimedit.Permissions;
import at.vailan.armortrimedit.data.MaterialItem;
import at.vailan.armortrimedit.data.PatternItem;
import at.vailan.armortrimedit.manager.ArmorTrimController;
import at.vailan.minecraftutils.commands.ArgsParser;
import at.vailan.minecraftutils.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static at.vailan.armortrimedit.ArmorTrimEdit.getInstance;
import static at.vailan.armortrimedit.commands.ArmorTrimCommand.isArmor;

public class SetCommand extends SubCommand {

    @Override
    public boolean requiresPlayer() { return true; }

    @Override
    public boolean isVisible(CommandSender sender) {
        return getInstance().getConfig().getBoolean("command-enabled") && hasRequiredPermission(sender);
    }

    @Override
    public boolean isAvailable(CommandSender sender) {
        return getInstance().getConfig().getBoolean("command-enabled") && hasRequiredPermission(sender);
    }

    @Override
    public Collection<String> getRequiredPermissions() {
        return List.of(Permissions.APPLY);
    }

    @Override
    public String getUsage() {
        return "/ae set [pattern] [material]";
    }

    public boolean onCommand(CommandSender commandSender, Command command, String alias, String commandString, ArgsParser args) {

        if (!(commandSender instanceof Player player)) return true;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!isArmor(item)) {
            player.sendMessage(getInstance().getMessage("not-holding-armor"));
            return true;
        }

        String patternName = args.getNext(null);
        String materialName = args.getNext(null);

        if (patternName == null || materialName == null) {
            player.sendMessage(getInstance().getMessage("not-enough-arguments"));
            return true;
        }

        if (!PatternItem.isPattern(patternName) || !MaterialItem.isMaterial(materialName)) {
            player.sendMessage(getInstance().getMessage("invalid-pattern-or-material"));
            return true;
        }

        ArmorTrimController controller = ArmorTrimController.get();

        controller.select(player, patternName);
        controller.select(player, materialName);

        controller.applyTrim(player);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, ArgsParser args) {

        if (args.getRemaining().length == 1) {
            return PatternItem.getAllPatternNames();
        }

        if (args.getRemaining().length == 2) {
            return MaterialItem.getAllMaterialNames();
        }

        return Collections.emptyList();
    }


}