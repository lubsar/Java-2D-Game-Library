package svk.sglubos.oengine.lib.gfx;

import java.awt.Graphics;

public abstract class Renderer {
	protected RenderBuffer buffer = null;
	protected int[] bufferPixels = null;
	protected boolean optimizedPipeline = false;
	
	protected int bufferWidth;
	protected int bufferHeight;
	
	protected Graphics renderGraphics;
	
	private GFXCallback bufferCallback = (event) -> {
		if(event == GFXEvent.BUFF_PIPE_LOCKED) {
			this.bufferPixels = null;
			this.optimizedPipeline = true;
		} else {
			this.bufferPixels = buffer.pixels;
			this.optimizedPipeline = false;
		}
	};
	
	public RenderBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(RenderBuffer buffer) {
		if(this.buffer != null) {
			buffer.removeEventCallback(bufferCallback);
		}
		
		this.buffer = buffer;
		this.bufferHeight = buffer.height;
		this.bufferWidth = buffer.width;
		this.bufferPixels = buffer.pixels;
		this.renderGraphics = buffer.g;
		
		this.buffer.addEventCallback(bufferCallback);
		this.optimizedPipeline = buffer.optimizedPipeline;
	}

	public int getBufferWidth() {
		return bufferWidth;
	}

	public int getBufferHeight() {
		return bufferHeight;
	}
}
