package svk.sglubos.oengine.lib.gfx.sprite;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;

public class Sprite {
	protected int width;
	protected int height;
	
	protected int[] pixels;

	public Sprite(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("width", width);
		ret.append("height", height);
		ret.append("pixels", pixels);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
