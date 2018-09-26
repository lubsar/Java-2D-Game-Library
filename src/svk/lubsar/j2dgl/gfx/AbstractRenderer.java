package svk.lubsar.j2dgl.gfx;

public interface AbstractRenderer {
	public void setBuffer(RenderBuffer buffer);
	public RenderBuffer getBuffer();
	public int getBufferWidth();
	public int getBufferHeight();
	
	public void setTransform(float[] matrix, boolean affine);
	public float[] getTransform();
}