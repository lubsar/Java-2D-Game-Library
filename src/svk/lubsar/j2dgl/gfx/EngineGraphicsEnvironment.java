package svk.lubsar.j2dgl.gfx;

import java.awt.GraphicsDevice;
import java.util.HashMap;
import java.util.Map;

public class EngineGraphicsEnvironment {
	private GraphicsDevice device;
	
	public Map<String, GameWindow> windows = new HashMap<String, GameWindow>(1);
	public Map<String, AbstractRenderer> renderers = new HashMap<String, AbstractRenderer>(3);
	public Map<String, RenderBuffer> buffers = new HashMap<String, RenderBuffer>(1);
	
	public EngineGraphicsEnvironment(GraphicsDevice device) {
		this.device = device;
	}
	
	public void bindRenderer(String windowID, AbstractRenderer renderer) {
		renderer.setBuffer(buffers.get(windowID));
	}
	
	public GameWindow createWindow(String id, int screenWidth, int screenHeight, String title, float screenScale){
		GameWindow window = new GameWindow(screenHeight, screenHeight, title, screenScale, device);
		buffers.put(id, window.getRenderBuffer());
		
		return windows.put(id, window);
	}
	
	public RenderBuffer createBuffer(String id, int width, int colorType, int height) {
		return buffers.put(id, new RenderBuffer(width, height, colorType, device));
	}
	
	public GraphicsDevice getGraphicsDevice() {
		return device;
	}
}