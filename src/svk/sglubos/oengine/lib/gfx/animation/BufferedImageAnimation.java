package svk.sglubos.oengine.lib.gfx.animation;

import java.awt.image.BufferedImage;

import svk.sglubos.oengine.lib.gfx.AbstractPrimitiveRenderer;
import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;

//TODO Documentation
public class BufferedImageAnimation extends Animation<AbstractPrimitiveRenderer> {
	protected BufferedImage[] images;
	
	public BufferedImageAnimation(BufferedImage[] images, int frameDelayTicks, int frames) {
		super(frameDelayTicks, frames);
		this.images = images;
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

	@Override
	public void render(AbstractPrimitiveRenderer renderer, int x, int y) {
		renderer.renderImage(images[currentFrame], x, y);
	}
}
