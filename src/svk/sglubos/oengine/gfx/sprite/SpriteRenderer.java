package svk.sglubos.oengine.gfx.sprite;

public interface SpriteRenderer {
	public void render(Sprite sprite, int xCoord, int yCoord);
	public void render(Sprite sprite, int xCoord, int yCoord, int scale);
}
