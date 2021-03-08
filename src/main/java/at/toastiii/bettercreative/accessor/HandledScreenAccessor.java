package at.toastiii.bettercreative.accessor;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;

public interface HandledScreenAccessor {

    int getX();
    int getY();
    int getBackgroundHeight();
    int getBackgroundWidth();

    DrawableHelper getHelper();
}
