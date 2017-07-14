package svk.sglubos.oengine.lib.gfx.animation;

import java.awt.image.BufferedImage;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;

//TODO Documentation
public class BufferedImageAnimation extends Animation {
	protected BufferedImage[] images;
	
	public BufferedImageAnimation(BufferedImage[] images, int frameDelayTicks, int frames) {
		super(frameDelayTicks, frames);
		this.images = images;
	}

	@Override
	public void render(AnimationRenderer renderer, int x, int y) {
		renderer.renderImage(images[currentFrame], x, y);
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
