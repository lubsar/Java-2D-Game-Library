package svk.sglubos.oengine.gfx;

import java.awt.GraphicsDevice;
import java.util.HashMap;
import java.util.Map;

public class EngineGraphicsEnvironment {
	public Map<String, GameWindow> windows = new HashMap<String, GameWindow>(1);
	public Map<String, Renderer> renderers = new HashMap<String, Renderer>(3);
	public Map<String, RenderBuffer> buffers = new HashMap<String, RenderBuffer>(1);
	
	public GameWindow createWindow(String id, int screenWidth, int screenHeight, String title, float screenScale, GraphicsDevice graphicsDevice){
		return windows.put(id, new GameWindow(screenHeight, screenHeight, title, screenScale, graphicsDevice));
	}
	
	public RenderBuffer createBuffer(String id, int width, int height) {
		return buffers.put(id, new RenderBuffer(width, height));
	}
}