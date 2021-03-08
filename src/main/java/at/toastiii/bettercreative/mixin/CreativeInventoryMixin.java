package at.toastiii.bettercreative.mixin;

import at.toastiii.bettercreative.accessor.HandledScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import at.toastiii.bettercreative.accessor.ScreenAccessor;
import at.toastiii.bettercreative.misc.InventoryTabEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryMixin {
	@Shadow private static int selectedTab;

	private final static Identifier CUSTOM_INVENTORY_TABS_TEXTURE = new Identifier("bettercreative", "tabs.png");

	private static int selectedInventoryTab;

	private static final int TABS_PER_SIDE = 4;

	private static final InventoryTabEntry[] ENTRIES = new InventoryTabEntry[TABS_PER_SIDE * 2];

	static {
		ENTRIES[0] = new InventoryTabEntry(new ItemStack(Items.ANVIL));
		ENTRIES[1] = new InventoryTabEntry(new ItemStack(Items.ENCHANTING_TABLE));
		ENTRIES[2] = new InventoryTabEntry(new ItemStack(Items.LOOM));
		ENTRIES[3] = new InventoryTabEntry(new ItemStack(Items.CARTOGRAPHY_TABLE));

		ENTRIES[4] = new InventoryTabEntry(new ItemStack(Items.CRAFTING_TABLE));
		ENTRIES[7] = new InventoryTabEntry(new ItemStack(Items.ENDER_CHEST), 20);
	}

	@Inject(at = @At("RETURN"), method = "setSelectedTab")
	public void setSelectedTab(ItemGroup group, CallbackInfo ci) {
		if(group.equals(ItemGroup.INVENTORY)) {
			selectedInventoryTab = 0;
		}
	}

	@Inject(at = @At("HEAD"), method = "drawBackground")
	private void drawBackground(MatrixStack stack, float delta, int mouseX, int mouseY, CallbackInfo info) {
		ItemGroup group = ItemGroup.GROUPS[selectedTab];
		if(group == null)
			return;

		if(group.equals(ItemGroup.INVENTORY)) {
			for (int i = 0; i < ENTRIES.length; i++) {
				InventoryTabEntry entry = ENTRIES[i];

				if(entry != null) {
					renderCustomInventoryIcon(i, entry, stack, delta, mouseX, mouseY);
				}
			}
		}
	}

	private void renderCustomInventoryIcon(int i, InventoryTabEntry entry, MatrixStack stack, float delta, int mouseX, int mouseY) {
		HandledScreenAccessor accessor = (HandledScreenAccessor) this;
		MinecraftClient.getInstance().getTextureManager().bindTexture(CUSTOM_INVENTORY_TABS_TEXTURE);

		int rowEntry = (i >= TABS_PER_SIDE ? i - TABS_PER_SIDE : i);
		int u = 0;
		int v = rowEntry * 28;
		int x = accessor.getX();
		int y = (accessor.getY() + 2 + (rowEntry * 28+2)) + entry.getyModifier();
		if (/*Selected*/ !false) {
			u += 35;
		}

		boolean right = i+1 > TABS_PER_SIDE;
		if(right) {
			x += accessor.getBackgroundWidth() - 4;
			u += 65;
		} else {
			x -= 28;
		}

		accessor.getHelper().drawTexture(stack, x, y, u, v, 28, 28);
		x += 9;
		y += 6;
		RenderSystem.enableRescaleNormal();
		ItemStack itemStack = entry.getItemStack();
		ItemRenderer renderer = ((ScreenAccessor) this).getItemRenderer();
		renderer.renderInGuiWithOverrides(itemStack, x, y);
		renderer.renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, itemStack, x, y);
		renderer.zOffset = 0.0F;
	}
}
