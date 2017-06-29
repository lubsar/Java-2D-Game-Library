package svk.sglubos.oengine.lib.gfx;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;


@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	protected float widthScale;
	protected float heightScale;
	
	protected boolean isFullscreen = false;
	
	protected RenderBuffer renderBuffer;
	protected RenderCanvas canvas;
	protected GraphicsDevice device;
	
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(new RenderBuffer(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()), title, 1.0f, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(int screenWidth, int screenHeight, String title, float canvasScale) {
		this(new RenderBuffer(screenWidth, screenHeight,BufferedImage.TYPE_INT_RGB, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()), title, canvasScale, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(RenderBuffer buffer, String title) {
		this(buffer, title, 1.0f, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(RenderBuffer buffer, String title, float screenScale, GraphicsDevice graphicsDevice) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		renderBuffer = buffer;
		canvas = new RenderCanvas(renderBuffer, screenScale);
		canvas.setBufferChangeCallback(() -> {
			dispose();
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
			setFullScreen(isFullscreen);
			canvas.requestFocusInWindow();
		});
		
		add(canvas, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		getContentPane().addComponentListener(new ComponentListener(){
			@Override
			public void componentResized(ComponentEvent e) {
				 widthScale = (float)(e.getComponent().getWidth() / renderBuffer.width);
				 heightScale = (float)(e.getComponent().getHeight() / renderBuffer.height);
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		setVisible(true);
		
		canvas.init(2);
		this.device = graphicsDevice;
	}
	
	public GameWindow(int screenWidth, int screenHeight, String title, float screenScale, GraphicsDevice graphicsDevice) {
		this(new RenderBuffer(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB, graphicsDevice), title, screenScale, graphicsDevice);
	}
	
	public void showRenderedContent() {
		canvas.showRenderedContent();
	}
	
	public void setFullScreen(boolean fullScreen) {
		this.isFullscreen = fullScreen;
		if(device == null || !device.isFullScreenSupported()) {
			simulateFullScreen(fullScreen);
		} else {
			setWindowDecoration(!fullScreen);
			if(fullScreen){
				device.setFullScreenWindow(this);
			} else {
				device.setFullScreenWindow(null);
			}
		}
	}
	
	public void setDisplayMode(DisplayMode mode) {
		DisplayMode[] modes = device.getDisplayModes();
		for(DisplayMode md : modes) {
			if(md.equals(mode)) {
				device.setDisplayMode(mode);
				break;
			}
		}
	}
	
	protected void setWindowDecoration(boolean decorated) {
		dispose();
		setUndecorated(!decorated);
		pack();
		setVisible(true);
		canvas.init(2);
	}
	
	protected void simulateFullScreen(boolean fullScreen) {
		if(getExtendedState() == JFrame.MAXIMIZED_BOTH && !fullScreen) {
			setWindowDecoration(true);
			setExtendedState(JFrame.NORMAL);
		} else if (fullScreen){
			setWindowDecoration(false);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	
	public RenderBuffer getRenderBuffer() {
		return renderBuffer;
	}
	
	public RenderCanvas getRenderCanvas() {
		return canvas;
	}
	
	public float getWidthScale() {
		return widthScale;
	}
	
	public float getHeightScale() {
		return heightScale;
	}
	
	public int translateXToWindowCoords(int x) {
		return (int) (x * widthScale);
	}
	
	public int translateYToWindowCoords(int y) {
		return (int) (y * heightScale);
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(this.getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("widthScale", widthScale);
		ret.append("heightScale", heightScale);
		ret.append(renderBuffer, "screen");
		ret.append(canvas, "canvas");
		ret.append(device, "device");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
