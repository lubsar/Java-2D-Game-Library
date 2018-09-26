package svk.lubsar.j2dgl.gfx.sprite;

import svk.lubsar.j2dgl.gfx.AbstractRenderer;

public interface AbstractSpriteRenderer extends AbstractRenderer {
	public void render(Sprite sprite, int xCoord, int yCoord);
	public void render(Sprite sprite, int xCoord, int yCoord, int scale);
}
