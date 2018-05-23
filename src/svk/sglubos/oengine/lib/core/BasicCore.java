package svk.sglubos.oengine.lib.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;

public abstract class BasicCore extends Core {
	public static final int FPS_UNLIMITED = -1;
	
	protected Timer timer;
	
	private boolean debug;
	private int updatesPerSecond;
	private int fps;
	private int ticks;
	
	public BasicCore(int updatesPerSecond, boolean debug) {
		this.updatesPerSecond = updatesPerSecond;
		this.debug = debug;
	}

	protected int getUpdatesPerSecond() {
		return updatesPerSecond;
	}
	
	protected boolean isDebug() {
		return debug;
	}
	
	protected void setDebug(boolean debug) {
		this.debug = debug;
	}
		
	@Override
	protected void start() {
		running = true;
		init();
		
		timer = new Timer(50, new ActionListener() {
			long lastTimeDebugOutput = System.currentTimeMillis();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
				ticks++;
				
				if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
					if(debug) {
						MessageHandler.printMessage(MessageHandler.INFO, "ticks: " + ticks + " fps: " + fps);
					}
					
					lastTimeDebugOutput += 1000;
					fps = 0;
					ticks = 0;
				}
				
				render();
				fps++;
			}
	
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	
	@Override
	protected void stop() {
	 timer.stop();
	 stopped();
	 running = false;
	}
	
	@Override
	protected int getFPS() {
		return fps;
	}
	
	@Override
	protected int getTPS() {
		return ticks;
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("debug", debug);
		ret.append("updatesPerSecond", updatesPerSecond);
		ret.append("tps", ticks);
		ret.append("fps", fps);
		ret.append(timer, "Timer");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
