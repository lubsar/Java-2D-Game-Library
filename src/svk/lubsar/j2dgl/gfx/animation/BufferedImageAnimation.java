package svk.lubsar.j2dgl.gfx.animation;

import java.awt.image.BufferedImage;

import svk.lubsar.j2dgl.gfx.AbstractPrimitiveRenderer;
import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

//TODO Documentation
public class BufferedImageAnimation extends Animation<AbstractPrimitiveRenderer> {
	protected BufferedImage[] images;
	
	public BufferedImageAnimation(BufferedImage[] images, int frameDelayTicks, int frames) {
		super(frameDelayTicks, frames);
		this.images = images;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendInstanceInfo(getClass(), hashCode());
		ret.increaseOffset();
		ret.appendln(super.toString());
		ret.append(images, "images");
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.getString();
	}

	@Override
	public void render(AbstractPrimitiveRenderer renderer, int x, int y) {
		renderer.renderImage(images[currentFrame], x, y);
	}
}
