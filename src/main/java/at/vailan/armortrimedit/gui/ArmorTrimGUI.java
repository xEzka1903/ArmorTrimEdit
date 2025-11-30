package at.vailan.armortrimedit.gui;

import at.vailan.armortrimedit.data.MaterialItem;
import at.vailan.armortrimedit.data.PatternItem;
import at.vailan.armortrimedit.manager.ArmorTrimController;
import at.vailan.armortrimedit.data.ArmorTrimData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;

import static at.vailan.armortrimedit.ArmorTrimEdit.getInstance;

public class ArmorTrimGUI {

    public static final int APPLY_SLOT = 4;
    public static final int DISPLAY_SLOT = 22;
    public static final int REMOVE_SLOT = 40;
    public static final int CLOSE_SLOT = 49;
    public static final int[] PATTERN_SLOTS = {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
    public static final int[] MATERIAL_SLOTS = {6, 7, 15, 16, 24, 25, 33, 34, 42, 43, 51};
    public static final int[] EMPTY_SLOTS = {3, 5, 8, 12, 13, 14, 17, 21, 23, 26, 30, 31, 32, 35, 39, 41, 44, 48, 50, 52, 53};

    private final Player player;
    private final Inventory inventory;

    public ArmorTrimGUI(Player p) {
        this.player = p;
        this.inventory = Bukkit.createInventory(player, 54, "ArmorTrimEdit");
        buildInventory(player);
    }

    private void buildInventory(Player player) {

        inventory.setItem(APPLY_SLOT, createApplyButton());
        inventory.setItem(DISPLAY_SLOT, createDisplayItem(player));
        inventory.setItem(REMOVE_SLOT, createRemoveButton());
        inventory.setItem(CLOSE_SLOT, createCloseButton());

        fillPatternSlots(inventory);
        fillMaterialSlots(inventory);
        fillEmptySlots(inventory);

    }

    public Inventory getInventory() { return inventory; }

    private ItemStack createApplyButton() {
        return newItem("§aApply Armor Trim", Material.LIME_CONCRETE);
    }

    private ItemStack createRemoveButton() { return newItem("§cRemove Armor Trim", Material.BARRIER); }

    private ItemStack createCloseButton() { return newItem("§4Close Menu", Material.RED_CONCRETE); }

    private ItemStack createDisplayItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack displayItem = item.clone();

        if (!(item.getItemMeta() instanceof ArmorMeta meta)) {
            player.sendMessage(getInstance().getMessage("not-holding-armor"));

            return newItem("§cInvalid Item", Material.BARRIER);
        }

        if (meta.hasTrim()) {
            ArmorMeta armorMeta = (ArmorMeta) displayItem.getItemMeta();
            armorMeta.setTrim(meta.getTrim());
            displayItem.setItemMeta(armorMeta);
        }

        ItemMeta finalMeta = displayItem.getItemMeta();
        finalMeta.displayName(Component.text(item.getType().toString()).decoration(TextDecoration.ITALIC, false));
        displayItem.setItemMeta(finalMeta);

        return displayItem;
    }

    private void fillPatternSlots(Inventory inv) {
        PatternItem[] items = PatternItem.values();
        ArmorTrimData data = ArmorTrimController.get().get(player);
        for (int i = 0; i < ArmorTrimGUI.PATTERN_SLOTS.length; i++) {
            int slot = ArmorTrimGUI.PATTERN_SLOTS[i];
            PatternItem item = items[i];

            ItemStack stack = newItem(item.display, item.material);

            if (item.trimPattern.equals(data.getPattern())) {
                stack = addGlint(stack);
            }

            inv.setItem(slot, stack);
        }
    }

    public void fillMaterialSlots(Inventory inv) {
        MaterialItem[] items = MaterialItem.values();
        ArmorTrimData data = ArmorTrimController.get().get(player);
        for (int i = 0; i < ArmorTrimGUI.MATERIAL_SLOTS.length; i++) {
            int slot = ArmorTrimGUI.MATERIAL_SLOTS[i];
            MaterialItem item = items[i];

            ItemStack stack = newItem(item.display, item.material);

            if (item.trimMaterial.equals(data.getMaterial())) {
                stack = addGlint(stack);
            }

            inv.setItem(slot, stack);
        }
    }

    private void fillEmptySlots(Inventory inv) {
        for (int slot : ArmorTrimGUI.EMPTY_SLOTS) {
            inv.setItem(slot, newItem("", Material.GRAY_STAINED_GLASS_PANE));
        }
    }

    protected static ItemStack newItem(String name, Material material) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);

        return item;
    }

    protected static ItemStack addGlint(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        if (meta != null) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
            clone.setItemMeta(meta);
        }
        return clone;
    }

}