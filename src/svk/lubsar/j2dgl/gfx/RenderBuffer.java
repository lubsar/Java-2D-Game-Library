package svk.lubsar.j2dgl.gfx;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import svk.lubsar.j2dgl.gfx.event.GFXCallback;
import svk.lubsar.j2dgl.gfx.event.GFXEvent;
import svk.lubsar.j2dgl.gfx.utils.ImageOptimizer;
import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

public class RenderBuffer {
	public static final byte PIPELINE_LOCKED_TAG = 0x0;
	public static final byte PIPELINE_UNLOCKED_TAG = 0x1;
	
	protected int width;
	protected int height;
	protected int[] pixels = null;
	protected boolean optimizedPipeline = false;
	
	protected BufferedImage renderLayer;
	protected Graphics2D g;
	
	protected List<GFXCallback> callbacks = new ArrayList<GFXCallback>();
	
	public RenderBuffer(int width, int height, int imageType, GraphicsDevice device) {
		renderLayer = device.getDefaultConfiguration().createCompatibleImage(width, height, imageType);
		g = renderLayer.createGraphics();
		
		this.width = width;
		this.height = height;
		this.pixels = ((DataBufferInt)renderLayer.getRaster().getDataBuffer()).getData();
	}
	
	public RenderBuffer(int width, int height) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = renderLayer.createGraphics();
		
		this.width = width;
		this.height = height;
	}
	
	public void setOptimizedPipelineOnly(boolean optimized) {
		if(optimized && !this.optimizedPipeline) {
			renderLayer = ImageOptimizer.optimize(renderLayer);
			g = renderLayer.createGraphics();
			dispatchEvent(GFXEvent.BUFF_PIPE_LOCKED);
		} else {
			pixels = ((DataBufferInt)renderLayer.getRaster().getDataBuffer()).getData();
			dispatchEvent(GFXEvent.BUFF_PIPE_UNLOCKED);
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
	
	public void addEventCallback(GFXCallback cb) {
		this.callbacks.add(cb);
	}
	
	public void removeEventCallback(GFXCallback cb) {
		this.callbacks.remove(cb);
	}
	
	public void clearEventCallbacks() {
		this.callbacks.clear();
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
	
	protected void dispatchEvent(GFXEvent e) {
		for(GFXCallback c : callbacks) {
			c.callback(e);
		}
	}
}
