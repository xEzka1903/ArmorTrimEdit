package at.vailan.armortrimedit.commands;

import at.vailan.armortrimedit.ArmorTrimEdit;
import at.vailan.minecraftutils.commands.CommandRouter;

public record CommandRegistration(ArmorTrimEdit plugin) {

    public void registerCommands() {
        CommandRouter armorTrimRouter = new CommandRouter(new ArmorTrimCommand());
        armorTrimRouter.addSubCommand("set", new SetCommand());
        armorTrimRouter.addSubCommand("remove", new RemoveCommand());
        armorTrimRouter.addToPlugin(plugin.getCommand("armortrimedit"));
    }

}