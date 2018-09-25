package svk.sglubos.oengine.lib.gfx;

public interface AbstractRenderer {
	public void setBuffer(RenderBuffer buffer);
	public RenderBuffer getBuffer();
	public int getBufferWidth();
	public int getBufferHeight();
	
	public void transform(float[] matrix, boolean affine);
}