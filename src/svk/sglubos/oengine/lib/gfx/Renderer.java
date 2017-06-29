package svk.sglubos.oengine.lib.gfx;

import java.awt.Graphics;

public abstract class Renderer {
	protected RenderBuffer buffer;
	protected int[] bufferPixels;
	
	protected int bufferWidth;
	protected int bufferHeight;
	
	protected Graphics renderGraphics;

	public RenderBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(RenderBuffer buffer) {
		this.buffer = buffer;
		this.bufferHeight = buffer.height;
		this.bufferWidth = buffer.width;
		this.bufferPixels = buffer.pixels;
		this.renderGraphics = buffer.g;
	}

	public int getBufferWidth() {
		return bufferWidth;
	}

	public int getBufferHeight() {
		return bufferHeight;
	}
}
