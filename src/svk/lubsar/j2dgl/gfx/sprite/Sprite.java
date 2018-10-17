package svk.lubsar.j2dgl.gfx.sprite;

import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

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
		
		ret.appendInstanceInfo(getClass(), hashCode());
		ret.increaseOffset();
		ret.appendPrimitive("width", width);
		ret.appendPrimitive("height", height);
		ret.appendPrimitive("pixels", pixels);
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
