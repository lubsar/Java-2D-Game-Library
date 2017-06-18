package svk.sglubos.oengine.gfx;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import svk.sglubos.oengine.gfx.utils.ImageOptimizer;
import svk.sglubos.oengine.utils.debug.DebugStringBuilder;

public class RenderBuffer {
	protected int width;
	protected int height;
	protected int[] pixels = null;
	protected boolean optimizedPipeline = true;
	
	protected BufferedImage renderLayer;
	protected Graphics g;
	
	public RenderBuffer(int width, int height, int imageType, GraphicsDevice device) {
		renderLayer = device.getDefaultConfiguration().createCompatibleImage(width, height, imageType);
		g = renderLayer.createGraphics();
		
		this.width = width;
		this.height = height;
	}
	
	public RenderBuffer(int width, int height) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = renderLayer.createGraphics();
		
		this.width = width;
		this.height = height;
	}
	
	//TODO BREAKS renderers (graphics and pixels[] references if set after buffer is bound to a renderer)
	public void setOptimizedPipelineOnly(boolean optimized) {
		if(optimized && !this.optimizedPipeline) {
			renderLayer = ImageOptimizer.optimize(renderLayer);
			g = renderLayer.createGraphics();
		} else {
			pixels = ((DataBufferInt)renderLayer.getRaster().getDataBuffer()).getData();
		}
		this.optimizedPipeline = optimized;
	}
	
	public BufferedImage getImage() {
		return renderLayer;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public Graphics getG() {
		return g;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("width", width);
		ret.append("height", height);

		ret.append(g, "g");
		ret.append(renderLayer, "renderLayer");
		ret.append(pixels, "pixels");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
