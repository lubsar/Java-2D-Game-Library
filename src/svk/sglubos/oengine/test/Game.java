package svk.sglubos.oengine.test;

import java.awt.Color;

import svk.sglubos.oengine.core.BasicCore;
import svk.sglubos.oengine.gfx.BasicRenderer;
import svk.sglubos.oengine.gfx.GameWindow;
import svk.sglubos.oengine.input.Keyboard;
import svk.sglubos.oengine.input.Mouse;

public class Game extends BasicCore {
	private GameWindow window;
	private BasicRenderer renderer = new BasicRenderer();
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		super(20, BasicCore.FPS_UNLIMITED, true);
		start();
	}
	
	public void init(){
		window = new GameWindow(1600, 900, "game");
		window.setResizable(true);
		window.getRenderBuffer().setOptimizedPipelineOnly(false);
		renderer.setBuffer(window.getRenderBuffer());
		
		Mouse.bind(window.getRenderCanvas());
		Keyboard.bind(window.getRenderCanvas());
	}
	
	public void tick(){
		
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		renderer.clear(Color.BLACK);
		//RENDER
		
		window.showRenderedContent();
	}

	@Override
	protected void stopped() {
		System.exit(0);
	}

}
