package at.vailan.armortrimedit.commands;

import at.vailan.armortrimedit.Permissions;
import at.vailan.armortrimedit.manager.ArmorTrimController;
import at.vailan.minecraftutils.commands.ArgsParser;
import at.vailan.minecraftutils.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

import static at.vailan.armortrimedit.ArmorTrimEdit.getInstance;
import static at.vailan.armortrimedit.commands.ArmorTrimCommand.isArmor;

public class RemoveCommand extends SubCommand {

    @Override
    public boolean requiresPlayer() {
        return true;
    }

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
        return List.of(Permissions.REMOVE);
    }

    @Override
    public String getUsage() {
        return "/ae remove";
    }

    public boolean onCommand(CommandSender commandSender, Command command, String alias, String commandString, ArgsParser args) {
        Player player = (Player) commandSender;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!isArmor(item)) {
            player.sendMessage(getInstance().getMessage("not-holding-armor"));
            return true;
        }

        ArmorTrimController controller = ArmorTrimController.get();

        controller.removeTrim(player);

        return true;
    }

}