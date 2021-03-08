package at.toastiii.bettercreative.mixin;

import at.toastiii.bettercreative.accessor.ScreenAccessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenAccessor {

	@Shadow protected ItemRenderer itemRenderer;

	@Override
	public ItemRenderer getItemRenderer() {
		return itemRenderer;
	}
}
