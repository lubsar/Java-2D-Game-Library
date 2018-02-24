package svk.sglubos.oengine.lib.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import svk.sglubos.oengine.lib.gfx.event.GFXCallback;
import svk.sglubos.oengine.lib.gfx.event.GFXEvent;
import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;

@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	public static final byte BUFFER_CHANGED = 0x0;
	
	protected RenderBuffer buffer;
	protected float scale = 1.0f;
	protected BufferStrategy bs;
	
	private GFXCallback renderBufferCb;
	
	public RenderCanvas(RenderBuffer buffer, float scale) {
		super();
		setPreferredSize(new Dimension((int)((buffer.width + 10) * scale), (int)((buffer.height + 10) * scale)));
		
		this.buffer = buffer;
		this.scale = scale;
	}
	
	public void setRenderBuffer(RenderBuffer buffer, float scale) {
		setPreferredSize(new Dimension((int)((buffer.width + 10) * scale), (int)((buffer.height + 10) * scale)));
		
		this.buffer = buffer;
		this.scale = scale;
		renderBufferCb.callback(GFXEvent.CAN_BUFF_CHANGED);
	}
	
	public void setBufferChangeCallback(GFXCallback cb) {
		this.renderBufferCb = cb;
	}
	
	public void init(int numBuffers){
		try{
			createBufferStrategy(numBuffers);			
		}catch(Exception e){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "Exception while creating BufferStrategy ! printing stack trace\n");
			e.printStackTrace();
		}
		
		bs = getBufferStrategy();
	}
	
	//TODO Exception
	public void showRenderedContent(){
		if(bs == null){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "BufferStrategy is not initialized !");
			return;
		}
		
		Graphics g = null;
		
		do {
		    try{
		    	g = bs.getDrawGraphics();
		    	g.drawImage(buffer.renderLayer, 0, 0, getWidth(),getHeight(), null);
		    } finally {
		    	if(g != null)
		    		g.dispose();
		    }
		    
		    bs.show();
		} while (bs.contentsLost());
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("scale", scale);
		ret.append(buffer, "renderBuffer");
		ret.append(bs, "bs");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
