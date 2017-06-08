package svk.sglubos.oengine.gfx.animation;

import java.awt.image.BufferedImage;

import svk.sglubos.oengine.gfx.Screen;
import svk.sglubos.oengine.utils.debug.DebugStringBuilder;

//TODO Documentation
public class BufferedImageAnimation extends Animation {
	protected BufferedImage[] images;
	
	public BufferedImageAnimation(BufferedImage[] images, long frameDelay, byte timeFormat, int frames) {
		super(frameDelay, timeFormat, frames);
		this.images = images;
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderImage(images[currentFrame], x, y);
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append(images, "images");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
