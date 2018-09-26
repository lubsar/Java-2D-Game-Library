package svk.lubsar.j2dgl.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public interface AbstractPrimitiveRenderer extends AbstractRenderer {
	public void renderFilledRectangle(int x, int y, int width, int height, Color color);
	public void renderRectangle(int x, int y, int width, int height, Color color);
	public void renderImage(BufferedImage img, int x, int y, int width, int height);
	public void renderImage(BufferedImage img, int x, int y);
	public void renderString(String text, int x, int y, Font font, Color color);
	public void renderFilledOval(int x, int y, int width, int height, Color color);
	public void renderOval(int x, int y, int width, int height, Color color);
	public void renderLine(int x, int y, int xa, int ya, Color color);
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color);
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color);
}
