package svk.sglubos.oengine.lib.gfx.sprite;

import svk.sglubos.oengine.lib.gfx.AbstractRenderer;

public interface AbstractSpriteRenderer extends AbstractRenderer {
	public void render(Sprite sprite, int xCoord, int yCoord);
	public void render(Sprite sprite, int xCoord, int yCoord, int scale);
}
