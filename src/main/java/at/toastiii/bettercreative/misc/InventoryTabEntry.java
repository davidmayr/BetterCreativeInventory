package at.toastiii.bettercreative.misc;

import net.minecraft.item.ItemStack;

public class InventoryTabEntry {

    private ItemStack itemStack;
    /**
     * Gets added to the y value. Can be used to sort the elements on the side
     */
    private int yModifier;

    public InventoryTabEntry(ItemStack itemStack, int yModifier) {
        this.itemStack = itemStack;
        this.yModifier = yModifier;
    }

    public InventoryTabEntry(ItemStack itemStack) {
        this(itemStack, 0);
    }

    public int getyModifier() {
        return yModifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
