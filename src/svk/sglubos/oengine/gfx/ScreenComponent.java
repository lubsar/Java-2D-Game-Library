package svk.sglubos.oengine.gfx;

import java.awt.Graphics;

import svk.sglubos.oengine.utils.debug.DebugStringBuilder;

public abstract class ScreenComponent {
	protected Screen screen;
	
	protected Graphics g;
	protected int[] pixels;
	
	protected boolean bound;
	
	public void bind(Screen screen, Graphics g, int[] pixels) {
		this.bound = true;
		this.g = g;
		this.pixels = pixels;
		this.screen = screen;
	}

	public void unbind() {
		bound = false;
		g = null;
		pixels = null;
		screen = null;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("bound = " + bound);
		ret.append(g, "g");
		ret.append(screen, "screen");
		ret.append(pixels, "pixels");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
