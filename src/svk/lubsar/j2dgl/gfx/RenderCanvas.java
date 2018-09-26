package svk.lubsar.j2dgl.gfx;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import svk.lubsar.j2dgl.gfx.event.GFXCallback;
import svk.lubsar.j2dgl.gfx.event.GFXEvent;
import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

@SuppressWarnings("serial")
public class RenderCanvas extends JPanel {
	public static final byte BUFFER_CHANGED = 0x0;
	
	protected RenderBuffer buffer;
	protected float scale = 1.0f;
	
	private GFXCallback renderBufferCb;
	
	public RenderCanvas(RenderBuffer buffer, float scale) {
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
	
	public void showRenderedContent(){
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(buffer.renderLayer, 0, 0, getWidth(), getHeight(), null);
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("scale", scale);
		ret.append(buffer, "renderBuffer");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
