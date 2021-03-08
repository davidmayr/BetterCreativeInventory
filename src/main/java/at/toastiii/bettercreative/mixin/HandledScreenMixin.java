package at.toastiii.bettercreative.mixin;

import at.toastiii.bettercreative.accessor.HandledScreenAccessor;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin implements HandledScreenAccessor {

	@Shadow protected int x;

	@Shadow protected int y;

	@Shadow protected int backgroundHeight;

	@Shadow protected int backgroundWidth;
	private DrawableHelper helper;

	@Inject(at = @At("RETURN"), method = "<init>")
	public <T extends ScreenHandler> void onInit(T handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
		this.helper = DrawableHelper.class.cast(this);
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getBackgroundHeight() {
		return this.backgroundHeight;
	}

	@Override
	public int getBackgroundWidth() {
		return this.backgroundWidth;
	}

	@Override
	public DrawableHelper getHelper() {
		return helper;
	}

}
