package svk.sglubos.oengine.lib.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

import svk.sglubos.oengine.lib.gfx.sprite.Sprite;
import svk.sglubos.oengine.lib.gfx.sprite.SpriteRenderer;
import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;

public class BasicRenderer extends Renderer implements SpriteRenderer {
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected boolean ignoreOffset;

	protected Color clearColor;
	protected int fontSize;
	
	public BasicRenderer() {
		clearColor = Color.BLACK;
		fontSize = renderGraphics.getFont().getSize();
	}
	
	public void renderFilledRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillRect(x, y, width, height);
	}

	public void renderFilledRectangle(int x, int y, int width, int height) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillRect(x, y, width, height);
	}

	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawRect(x, y, width, height);
	}

	public void renderRectangle(int x, int y, int width, int height) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawRect(x, y, width, height);
	}

	public void renderImage(BufferedImage img, int x, int y, int width, int height) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawImage(img, x, y, width, height, null);
	}

	public void renderImage(BufferedImage img, int x, int y) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawImage(img, x, y, null);
	}

	public void renderString(String text, int x, int y, Font font, Color color) {
		setFont(font);
		setColor(color);

		y += fontSize;

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawString(text, x, y);
	}

	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		y += fontSize;

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawString(text, x, y);
	}

	public void renderString(String text, int x, int y) {
		y += fontSize;

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawString(text, x, y);
	}

	public void renderFilledOval(int x, int y, int width, int height, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillOval(x, y, width, height);
	}

	public void renderFilledOval(int x, int y, int width, int height) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillOval(x, y, width, height);
	}

	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawOval(x, y, width, height);
	}

	public void renderOval(int x, int y, int width, int height) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawOval(x, y, width, height);
	}

	public void renderLine(int x, int y, int xa, int ya, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
			xa -= xOffset;
			ya -= yOffset;
		}

		renderGraphics.drawLine(x, y, xa, ya);
	}

	public void renderLine(int x, int y, int xa, int ya) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
			xa -= xOffset;
			ya -= yOffset;
		}

		renderGraphics.drawLine(x, y, xa, ya);
	}

	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color) {
		setColor(color);

		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		if (!ignoreOffset) {
			x -= xOffset;
			y -= yOffset;
		}

		renderGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	public void render(Sprite sprite, int xCoord, int yCoord) {
		int[] spritePixels = sprite.getPixels();

		if (!ignoreOffset) {
			xCoord -= xOffset;
			yCoord -= yOffset;
		}

		xCoord--;
		yCoord--;

		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();

		int pixelX = 0;
		int pixelY = 0;

		for (int y = 0; y < spriteHeight; y++) {
			pixelY = y + yCoord;
			for (int x = 0; x < spriteWidth; x++) {
				pixelX = x + xCoord;

				if (spritePixels[x + y * spriteWidth] >> 24 == 0) {
					continue;
				}

				if (pixelY >= 0 && pixelY < this.bufferHeight && pixelX >= 0 && pixelX < this.bufferWidth) {
					this.bufferPixels[pixelX + pixelY * this.bufferWidth] = spritePixels[x + y * spriteWidth];
				}
			}
		}
	}

	public void render(Sprite sprite, int xCoord, int yCoord, int scale) {
		int[] spritePixels = sprite.getPixels();

		if (!ignoreOffset) {
			xCoord -= xOffset;
			yCoord -= yOffset;
		}

		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();

		int pixelX = 0;
		int pixelY = 0;

		int scaledPixelX;
		int scaledPixelY;

		for (int y = 0; y < spriteHeight; y++) {
			pixelY = y * scale + yCoord;
			for (int x = 0; x < spriteWidth; x++) {
				pixelX = x * scale + xCoord;

				if (spritePixels[x + y * spriteWidth] >> 24 == 0) {
					continue;
				}

				if (scale > 1) {
					for (int yScaler = 0; yScaler < scale; yScaler++) {
						for (int xScaler = 0; xScaler < scale; xScaler++) {
							scaledPixelY = pixelY + yScaler;
							scaledPixelX = pixelX + xScaler;

							if (scaledPixelY >= 0 && scaledPixelY < this.bufferHeight && scaledPixelX >= 0
									&& scaledPixelX < this.bufferWidth) {
								this.bufferPixels[scaledPixelX + scaledPixelY * this.bufferWidth] = spritePixels[x
										+ y * spriteWidth];
							}
						}
					}
				} else {
					if (pixelY > 0 && pixelY < this.bufferHeight && pixelX > 0 && pixelX < this.bufferWidth) {
						this.bufferPixels[pixelX + pixelY * this.bufferWidth] = spritePixels[x + y * spriteWidth];
					}
				}

			}
		}
	}

	public void clear() {
		if (!optimizedPipeline) {
			int colorValue = clearColor.getRGB();
			for (int i = 0; i < bufferPixels.length; i++) {
				bufferPixels[i] = colorValue;
			}
		} else {
			Color temp = renderGraphics.getColor();
			renderGraphics.setColor(clearColor);
			renderGraphics.fillRect(0, 0, bufferWidth, bufferHeight);
			renderGraphics.setColor(temp);
		}
	}

	public void clear(Color color) {
		if (!optimizedPipeline) {
			int colorValue = color.getRGB();
			for (int i = 0; i < bufferPixels.length; i++) {
				bufferPixels[i] = colorValue;
			}
		} else {
			Color temp = renderGraphics.getColor();
			renderGraphics.setColor(color);
			renderGraphics.fillRect(0, 0, bufferWidth, bufferHeight);
			renderGraphics.setColor(temp);
		}
	}

	public void setColor(Color color) {
		if (color == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen color cannot be set to null");
			return;
		}

		renderGraphics.setColor(color);
	}

	public void setFont(Font font) {
		if (font == null) {
			MessageHandler.printMessage(MessageHandler.ERROR,
					"Screen font cannot be set to null, font stays set to current font");
			return;
		}

		renderGraphics.setFont(font);
		fontSize = font.getSize();
	}

	public void setFontSize(float size) {
		Font old = renderGraphics.getFont();
		Font newFont = old.deriveFont(size);
		this.fontSize = newFont.getSize();
		renderGraphics.setFont(newFont);
	}

	public void setFontStyle(int style) {
		Font old = renderGraphics.getFont();
		renderGraphics.setFont(old.deriveFont(style));
	}

	public FontMetrics getFontMetrics() {
		return renderGraphics.getFontMetrics();
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void setIngoreOffset(boolean ignore) {
		this.ignoreOffset = ignore;
	}

	public void disposeGraphics() {
		renderGraphics.dispose();
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();

		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("bufferWidth", bufferWidth);
		ret.append("bufferHeight", bufferHeight);
		ret.append("ignoreOffset", ignoreOffset);
		ret.append("xOffset", xOffset);
		ret.append("yOffset", yOffset);

		ret.append(clearColor, "clearColor");
		ret.append(renderGraphics, "renderGraphics");
		ret.append(buffer, "renderBuffer");
		ret.append(bufferPixels, "bufferPixels");
		ret.decreaseLayer();
		ret.appendCloseBracket();

		return ret.getString();
	}
}
